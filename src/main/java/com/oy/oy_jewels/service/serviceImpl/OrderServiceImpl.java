package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.*;
import com.oy.oy_jewels.dto.response.AllOrderResponseDTO;
import com.oy.oy_jewels.dto.response.OrderResponse;
import com.oy.oy_jewels.entity.*;
import com.oy.oy_jewels.repository.*;
import com.oy.oy_jewels.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ShiprocketService shiprocketService;

    private final ReturnRepository returnRepository;
    private final ExchangeRepository exchangeRepository;


    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                            UserRepository userRepository, ProductRepository productRepository,
                            ShiprocketService shiprocketService,ReturnRepository returnRepository,
                            ExchangeRepository exchangeRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shiprocketService = shiprocketService;
        this.returnRepository = returnRepository;
        this.exchangeRepository = exchangeRepository;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        try {
            logger.info("Starting order creation for user ID: {}", request.getUserId());

            // Validate required fields
            if ((request.getCustomerFirstName() == null && request.getCustomerLastName() == null) && (request.getCustomerFirstName().trim().isEmpty() && request.getCustomerLastName().trim().isEmpty()) ) {
                throw new RuntimeException("Customer name is required");
            }
            if (request.getCustomerPhone() == null || request.getCustomerPhone().trim().isEmpty()) {
                throw new RuntimeException("Customer phone is required");
            }
            if (request.getCustomerEmail() == null || request.getCustomerEmail().trim().isEmpty()) {
                throw new RuntimeException("Customer email is required");
            }
            if (request.getShippingAddress() == null || request.getShippingAddress().trim().isEmpty()) {
                throw new RuntimeException("Shipping address is required");
            }
            if (request.getShippingCity() == null || request.getShippingCity().trim().isEmpty()) {
                throw new RuntimeException("Shipping city is required");
            }
            if (request.getShippingState() == null || request.getShippingState().trim().isEmpty()) {
                throw new RuntimeException("Shipping state is required");
            }
            if (request.getShippingPincode() == null || request.getShippingPincode().trim().isEmpty()) {
                throw new RuntimeException("Shipping pincode is required");
            }

            logger.debug("Order request validation passed for user ID: {}", request.getUserId());

            UserEntity user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

            if (!"active".equalsIgnoreCase(user.getStatus())) {
                throw new RuntimeException("User is not active. Cannot create order.");
            }

            OrderEntity order = new OrderEntity();

            order.setUser(user);
            order.setCustomerFirstName(request.getCustomerFirstName());
            order.setCustomerLastName(request.getCustomerLastName());
            order.setCustomerPhone(request.getCustomerPhone());
            order.setCustomerEmail(request.getCustomerEmail());
            order.setShippingAddress(request.getShippingAddress());
            order.setShippingCity(request.getShippingCity());
            order.setShippingState(request.getShippingState());
            order.setShippingPincode(request.getShippingPincode());
            order.setShippingCountry(request.getShippingCountry());

            if (request.getShippingIsBilling() != null && request.getShippingIsBilling()) {
                order.setBillingCustomerName(request.getCustomerFirstName());
                order.setBillingLastName(request.getCustomerLastName());
                order.setBillingAddress(request.getShippingAddress());
                order.setBillingCity(request.getShippingCity());
                order.setBillingState(request.getShippingState());
                order.setBillingPincode(request.getShippingPincode());
                order.setBillingCountry(request.getShippingCountry());
                order.setBillingEmail(request.getCustomerEmail());
                order.setBillingPhone(request.getCustomerPhone());
                logger.debug("Using shipping address as billing address for order");
            } else {
                order.setBillingCustomerName(request.getBillingCustomerName());
                order.setBillingLastName(request.getBillingLastName());
                order.setBillingAddress(request.getBillingAddress());
                order.setBillingCity(request.getBillingCity());
                order.setBillingState(request.getBillingState());
                order.setBillingPincode(request.getBillingPincode());
                order.setBillingCountry(request.getBillingCountry());
                order.setBillingEmail(request.getBillingEmail());
                order.setBillingPhone(request.getBillingPhone());
                logger.debug("Using separate billing address for order");
            }

            order.setPaymentMethod(request.getPaymentMethod());
            order.setOrderDate(LocalDate.now());
            order.setOrderStatus("placed");
            order.setShippingIsBilling(request.getShippingIsBilling());
            order.setPickupLocation("Home");
            order.setChannelId("");

            BigDecimal totalAmount = BigDecimal.ZERO;
            List<OrderItemEntity> orderItems = new ArrayList<>();

            for (OrderItemRequest itemRequest : request.getItems()) {
                ProductEntity product = productRepository.findById(itemRequest.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.getProductId()));

                if (itemRequest.getProductQuantity() > product.getProductQuantity()) {
                    logger.warn("Insufficient stock for product {}: requested={}, available={}",
                            product.getProductTitle(), itemRequest.getProductQuantity(), product.getProductQuantity());
                    return new OrderResponse("insufficient_stock",
                            "Product '" + product.getProductTitle() + "' only has " + product.getProductQuantity() +
                                    " items in stock (requested: " + itemRequest.getProductQuantity() + ")",
                            product.getProductId());
                }

                OrderItemEntity orderItem = new OrderItemEntity();

                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setQuantity(itemRequest.getProductQuantity());
                orderItem.setProductPrice(product.getProductPrice());
                orderItem.setItemName(product.getProductTitle());
                orderItem.setSku(product.getSkuNo());
                orderItem.setUnits(itemRequest.getProductQuantity());
                orderItem.setSellingPrice(product.getProductPrice());

                // Convert the string discount to BigDecimal safely
                BigDecimal discount;

                try {
                    String discountStr = product.getProductDiscount();
                    discount = (discountStr != null && !discountStr.isEmpty())
                            ? new BigDecimal(discountStr)
                            : BigDecimal.ZERO;
                } catch (NumberFormatException e) {
                    discount = BigDecimal.ZERO; // or handle the error as appropriate
                }
                orderItem.setDiscount(discount);
                orderItem.setTax(BigDecimal.ZERO);

                BigDecimal subtotal = product.getProductPrice().multiply(new BigDecimal(itemRequest.getProductQuantity()));
                orderItem.setSubtotal(subtotal);

                orderItems.add(orderItem);
                totalAmount = totalAmount.add(subtotal);

                int newQuantity = product.getProductQuantity() - itemRequest.getProductQuantity();
                product.setProductQuantity(newQuantity);
                productRepository.save(product);
            }

            order.setTotalAmount(totalAmount);
            order.setOrderItems(orderItems);

            OrderEntity savedOrder = orderRepository.save(order);
            logger.info("Order saved to database with ID: {}", savedOrder.getOrderId());

            String shiprocketOrderId = null;
            try {
                logger.info("Starting Shiprocket order creation for order ID: {}", savedOrder.getOrderId());
                ShiprocketOrderRequest shiprocketRequest = createShiprocketOrderRequest(savedOrder);
                shiprocketService.validateOrderRequest(shiprocketRequest);
                shiprocketOrderId = shiprocketService.createOrder(shiprocketRequest);
                logger.info("Shiprocket order created successfully with ID: {}", shiprocketOrderId);

                savedOrder.setShiprocketOrderId(shiprocketOrderId);
                orderRepository.save(savedOrder);
                logger.info("Updated order ID: {} with Shiprocket order ID: {}", savedOrder.getOrderId(), shiprocketOrderId);
            } catch (Exception e) {
                logger.error("Failed to create Shiprocket order for order ID: {}", savedOrder.getOrderId());
                logger.error("Shiprocket error details: {}", e.getMessage());
                logger.error("Full Shiprocket exception: ", e);
                throw new RuntimeException("Failed to create Shiprocket order: " + e.getMessage());
            }

            logger.info("Order creation completed successfully for order ID: {}", savedOrder.getOrderId());
            return new OrderResponse(savedOrder);

        } catch (Exception e) {
            logger.error("Error creating order for user ID: {}", request.getUserId());
            logger.error("Error details: {}", e.getMessage());
            logger.error("Full exception: ", e);
            throw new RuntimeException("Failed to create order: " + e.getMessage());
        }
    }

    private ShiprocketOrderRequest createShiprocketOrderRequest(OrderEntity order) {
        logger.info("Creating Shiprocket order request for order ID: {}", order.getOrderId());
        ShiprocketOrderRequest shiprocketRequest = new ShiprocketOrderRequest();

        shiprocketRequest.setOrder_id(order.getOrderId().toString());
        shiprocketRequest.setOrder_date(order.getOrderDate().toString());
        shiprocketRequest.setPickup_location(order.getPickupLocation() != null ? order.getPickupLocation() : "Home");
        shiprocketRequest.setChannel_id(order.getChannelId() != null ? order.getChannelId() : "");

        shiprocketRequest.setBilling_customer_name(order.getBillingCustomerName());
        shiprocketRequest.setBilling_last_name(order.getBillingLastName());
        shiprocketRequest.setBilling_address(order.getBillingAddress());
        shiprocketRequest.setBilling_city(order.getBillingCity());
        shiprocketRequest.setBilling_pincode(order.getBillingPincode());
        shiprocketRequest.setBilling_state(order.getBillingState());
        shiprocketRequest.setBilling_country(order.getBillingCountry() != null ? order.getBillingCountry() : "India");
        shiprocketRequest.setBilling_email(order.getBillingEmail());
        shiprocketRequest.setBilling_phone(order.getBillingPhone());

        shiprocketRequest.setShipping_is_billing(order.getShippingIsBilling() != null ? order.getShippingIsBilling() : false);
        shiprocketRequest.setShipping_customer_name(order.getCustomerFirstName());
        shiprocketRequest.setShipping_address(order.getShippingAddress());
        shiprocketRequest.setShipping_city(order.getShippingCity());
        shiprocketRequest.setShipping_pincode(order.getShippingPincode());
        shiprocketRequest.setShipping_state(order.getShippingState());
        shiprocketRequest.setShipping_country(order.getShippingCountry() != null ? order.getShippingCountry() : "India");
        shiprocketRequest.setShipping_email(order.getCustomerEmail());
        shiprocketRequest.setShipping_phone(order.getCustomerPhone());

        List<ShiprocketOrderItem> shiprocketItems = new ArrayList<>();
        for (OrderItemEntity item : order.getOrderItems()) {
            ShiprocketOrderItem shiprocketItem = new ShiprocketOrderItem();
            shiprocketItem.setName(item.getItemName());
            shiprocketItem.setSku(item.getSku());
            shiprocketItem.setUnits(item.getUnits());
            shiprocketItem.setSelling_price(item.getSellingPrice());
            shiprocketItem.setDiscount(item.getDiscount() != null ? item.getDiscount() : BigDecimal.ZERO);
            shiprocketItem.setTax(item.getTax() != null ? item.getTax() : BigDecimal.ZERO);
            shiprocketItems.add(shiprocketItem);
        }
        shiprocketRequest.setOrder_items(shiprocketItems);

        shiprocketRequest.setPayment_method(order.getPaymentMethod() != null ? order.getPaymentMethod() : "Prepaid");
        shiprocketRequest.setShipping_charges(BigDecimal.ZERO);
        shiprocketRequest.setGiftwrap_charges(BigDecimal.ZERO);
        shiprocketRequest.setTransaction_charges(BigDecimal.ZERO);
        shiprocketRequest.setTotal_discount(BigDecimal.ZERO);
        shiprocketRequest.setSub_total(order.getTotalAmount());

        shiprocketRequest.setLength((int) 10.0);
        shiprocketRequest.setBreadth((int) 10.0);
        shiprocketRequest.setHeight((int) 5.0);
        shiprocketRequest.setWeight(0.5);

        logger.info("Shiprocket order request created successfully for order ID: {}", order.getOrderId());
        return shiprocketRequest;
    }

    //get all orders
    @Override
    public List<AllOrderResponseDTO> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private AllOrderResponseDTO convertToDTO(OrderEntity order) {
        AllOrderResponseDTO dto = new AllOrderResponseDTO();
        dto.setSuccess(true);
        dto.setOrderId(order.getOrderId());
        dto.setTotal(order.getTotalAmount());
        dto.setStatus(order.getOrderStatus());
        dto.setShiprocketOrderId(order.getShiprocketOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setDeliveryDate(order.getDeliveryDate());
        dto.setMessage("Order retrieved successfully!");
        dto.setError(null);
        dto.setProductId(null);
        dto.setCustomerFirstName(order.getCustomerFirstName());
        dto.setCustomerLastName(order.getCustomerLastName());
        dto.setCustomerPhone(order.getCustomerPhone());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPickupLocation(order.getPickupLocation());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setState(order.getShippingState());
        dto.setPincode(order.getShippingPincode());
        dto.setUserId(order.getUser().getUserId());

        List<AllOrderResponseDTO.OrderItemDTO> itemDTOs = new ArrayList<>();
        for (OrderItemEntity item : order.getOrderItems()) {
            AllOrderResponseDTO.OrderItemDTO itemDTO = new AllOrderResponseDTO.OrderItemDTO();
            itemDTO.setProductId(item.getProduct().getProductId());
            itemDTO.setName(item.getProduct().getProductTitle());
            itemDTO.setProductQuantity(item.getQuantity());
            itemDTO.setProductPrice(item.getProductPrice());
            itemDTO.setSubtotal(item.getProductPrice().multiply(new BigDecimal(item.getQuantity())));
            itemDTOs.add(itemDTO);
        }
        dto.setItems(itemDTOs);

        return dto;
    }



    @Override
    public OrderResponse getOrderById(Long orderId) {
        try {
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
            return new OrderResponse(order);
        } catch (Exception e) {
            logger.error("Error fetching order with id: {}", orderId, e);
            throw new RuntimeException("Failed to fetch order: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(Long orderId, UpdateOrderRequest request) {
        try {
            OrderEntity existingOrder = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

            // Store original values for rollback if needed
            String originalStatus = existingOrder.getOrderStatus();

            // Update provided fields
            if (request.getShippingAddress() != null) {
                existingOrder.setShippingAddress(request.getShippingAddress());
            }
            if (request.getShippingCity() != null) {
                existingOrder.setShippingCity(request.getShippingCity());
            }
            if (request.getShippingState() != null) {
                existingOrder.setShippingState(request.getShippingState());
            }
            if (request.getShippingPincode() != null) {
                existingOrder.setShippingPincode(request.getShippingPincode());
            }
            if (request.getPaymentMethod() != null) {
                existingOrder.setPaymentMethod(request.getPaymentMethod());
            }
            if (request.getOrderStatus() != null) {
                existingOrder.setOrderStatus(request.getOrderStatus());
            }
            if (request.getDeliveryDate() != null) {
                existingOrder.setDeliveryDate(request.getDeliveryDate());
            }

            OrderEntity updatedOrder = orderRepository.save(existingOrder);

            // If order status changed to cancelled, restore product quantities
            if ("cancelled".equalsIgnoreCase(request.getOrderStatus()) &&
                    !"cancelled".equalsIgnoreCase(originalStatus)) {
                restoreProductQuantities(updatedOrder);
            }

            return new OrderResponse(updatedOrder);
        } catch (Exception e) {
            logger.error("Error updating order with id: {}", orderId, e);
            throw new RuntimeException("Failed to update order: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public OrderResponse patchOrder(Long orderId, Map<String, Object> updates) {
        try {
            OrderEntity existingOrder = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

            String originalStatus = existingOrder.getOrderStatus();

            // Apply partial updates
            updates.forEach((key, value) -> {
                switch (key) {
                    case "shippingAddress":
                        existingOrder.setShippingAddress((String) value);
                        break;
                    case "shippingCity":
                        existingOrder.setShippingCity((String) value);
                        break;
                    case "shippingState":
                        existingOrder.setShippingState((String) value);
                        break;
                    case "shippingPincode":
                        existingOrder.setShippingPincode((String) value);
                        break;
                    case "paymentMethod":
                        existingOrder.setPaymentMethod((String) value);
                        break;
                    case "orderStatus":
                        existingOrder.setOrderStatus((String) value);
                        break;
                    case "deliveryDate":
                        existingOrder.setDeliveryDate(LocalDate.parse(value.toString()));
                        break;
                    case "customerFirstName":
                        existingOrder.setCustomerFirstName((String) value);
                        break;
                    case "customerLastName" :
                        existingOrder.setBillingLastName((String) value);
                        break;
                    case "customerPhone":
                        existingOrder.setCustomerPhone((String) value);
                        break;
                    case "customerEmail":
                        existingOrder.setCustomerEmail((String) value);
                        break;
                    default:
                        logger.warn("Unknown field for patch update: {}", key);
                }
            });

            OrderEntity updatedOrder = orderRepository.save(existingOrder);

            // If order status changed to cancelled, restore product quantities
            if (updates.containsKey("orderStatus") &&
                    "cancelled".equalsIgnoreCase(updates.get("orderStatus").toString()) &&
                    !"cancelled".equalsIgnoreCase(originalStatus)) {
                restoreProductQuantities(updatedOrder);
            }

            return new OrderResponse(updatedOrder);
        } catch (Exception e) {
            logger.error("Error patching order with id: {}", orderId, e);
            throw new RuntimeException("Failed to patch order: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        try {
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

            // Restore product quantities before deletion
            restoreProductQuantities(order);

            // Delete the order (cascade will handle order items)
            orderRepository.delete(order);

            logger.info("Order deleted successfully with id: {}", orderId);
        } catch (Exception e) {
            logger.error("Error deleting order with id: {}", orderId, e);
            throw new RuntimeException("Failed to delete order: " + e.getMessage());
        }
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        try {
            List<OrderEntity> orders = orderRepository.findByUser_UserId(userId);
            return orders.stream()
                    .map(OrderResponse::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching orders for user id: {}", userId, e);
            throw new RuntimeException("Failed to fetch orders for user: " + e.getMessage());
        }
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(String orderStatus) {
        try {
            List<OrderEntity> orders = orderRepository.findByOrderStatus(orderStatus);
            return orders.stream()
                    .map(OrderResponse::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching orders by status: {}", orderStatus, e);
            throw new RuntimeException("Failed to fetch orders by status: " + e.getMessage());
        }
    }

    @Override
    public List<OrderResponse> getOrdersByProductId(Long productId) {
        try {
            List<OrderEntity> orders = findOrdersByProductId(productId);
            return orders.stream()
                    .map(OrderResponse::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching orders by product id: {}", productId, e);
            throw new RuntimeException("Failed to fetch orders by product: " + e.getMessage());
        }
    }

    @Override
    public List<OrderResponse> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            List<OrderEntity> orders = orderRepository.findByOrderDateBetween(startDate, endDate);
            return orders.stream()
                    .map(OrderResponse::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching orders by date range: {} to {}", startDate, endDate, e);
            throw new RuntimeException("Failed to fetch orders by date range: " + e.getMessage());
        }
    }

    @Override
    public List<OrderEntity> findOrdersByProductId(Long productId) {
        try {
            List<OrderItemEntity> items = orderItemRepository.findByProduct_ProductId(productId);
            return items.stream()
                    .map(OrderItemEntity::getOrder)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error finding orders by product id: {}", productId, e);
            throw new RuntimeException("Failed to find orders by product: " + e.getMessage());
        }
    }
    // Helper method to restore product quantities
//    private void restoreProductQuantities(OrderEntity order) {
//        for (OrderItemEntity orderItem : order.getOrderItems()) {
//            ProductEntity product = orderItem.getProduct();
//            product.setProductQuantity(product.getProductQuantity() + orderItem.getQuantity());
//            productRepository.save(product);
//            logger.info("Restored {} units for product id: {}", orderItem.getQuantity(), product.getProductId());
//        }
//    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        try {
            logger.info("Starting cancellation for order ID: {}", orderId);
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

            if ("cancelled".equalsIgnoreCase(order.getOrderStatus())) {
                throw new RuntimeException("Order is already cancelled");
            }

            if (order.getShiprocketOrderId() != null && !order.getShiprocketOrderId().isEmpty()) {
                String shiprocketOrderId = order.getShiprocketOrderId();
                logger.info("Attempting to cancel Shiprocket order with ID: {}", shiprocketOrderId);

                // Check Shiprocket order status to ensure it can be cancelled
                String shiprocketStatus = null;
                try {
                    shiprocketStatus = shiprocketService.getOrderStatus(shiprocketOrderId);
                    if (List.of("OUT FOR PICKUP", "SHIPPED", "DELIVERED").contains(shiprocketStatus.toUpperCase())) {
                        logger.warn("Shiprocket order ID {} is in {} status and cannot be cancelled", shiprocketOrderId, shiprocketStatus);
                        throw new RuntimeException("Order cannot be cancelled: Status is " + shiprocketStatus);
                    }
                } catch (Exception e) {
                    logger.warn("Failed to fetch Shiprocket order status for ID {}: {}. Proceeding with cancellation.", shiprocketOrderId, e.getMessage());
                    // Proceed with cancellation even if status check fails
                }

                // Cancel order in Shiprocket
                try {
                    shiprocketService.cancelOrder(shiprocketOrderId);
                    logger.info("Shiprocket order cancelled successfully for order ID: {}", orderId);
                } catch (Exception e) {
                    logger.error("Failed to cancel Shiprocket order for order ID: {}: {}", orderId, e.getMessage());
                    throw new RuntimeException("Failed to cancel Shiprocket order: " + e.getMessage());
                }
            } else {
                logger.warn("No Shiprocket order ID found for order ID: {}. Skipping Shiprocket cancellation.", orderId);
            }

            // Update local database
            order.setOrderStatus("cancelled");
            try {
                restoreProductQuantities(order);
            } catch (Exception e) {
                logger.error("Failed to restore product quantities for order ID: {}: {}", orderId, e.getMessage());
                throw new RuntimeException("Failed to restore product quantities: " + e.getMessage());
            }
            orderRepository.save(order);
            logger.info("Order status updated to 'cancelled' in local database for order ID: {}", orderId);

            logger.info("Order cancelled successfully for order ID: {}", orderId);
        } catch (Exception e) {
            logger.error("Error cancelling order with id: {}", orderId, e);
            throw new RuntimeException("Failed to cancel order: " + e.getMessage());
        }
    }


    @Override
    @Transactional
    public void cancelOrderByShiprocketId(String shiprocketOrderId) {
        try {
            logger.info("Starting cancellation for Shiprocket order ID: {}", shiprocketOrderId);
            OrderEntity order = orderRepository.findByShiprocketOrderId(shiprocketOrderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with Shiprocket order ID: " + shiprocketOrderId));

            if ("cancelled".equalsIgnoreCase(order.getOrderStatus())) {
                throw new RuntimeException("Order is already cancelled");
            }

            String shiprocketStatus = null;
            try {
                shiprocketStatus = shiprocketService.getOrderStatus(shiprocketOrderId);
                if (List.of("OUT FOR PICKUP", "SHIPPED", "DELIVERED").contains(shiprocketStatus.toUpperCase())) {
                    logger.warn("Shiprocket order ID {} is in {} status and cannot be cancelled", shiprocketOrderId, shiprocketStatus);
                    throw new RuntimeException("Order cannot be cancelled: Status is " + shiprocketStatus);
                }
            } catch (Exception e) {
                logger.warn("Failed to fetch Shiprocket order status for ID {}: {}. Proceeding with cancellation.", shiprocketOrderId, e.getMessage());
                // Optionally, proceed with cancellation or throw the exception based on your business logic
            }

            shiprocketService.cancelOrder(shiprocketOrderId);
            order.setOrderStatus("cancelled");
            restoreProductQuantities(order);
            orderRepository.save(order);

            logger.info("Order cancelled successfully for Shiprocket order ID: {}", shiprocketOrderId);
        } catch (Exception e) {
            logger.error("Error cancelling order with Shiprocket order ID: {}", shiprocketOrderId, e);
            throw new RuntimeException("Failed to cancel order: " + e.getMessage());
        }
    }


    @Override
    @Transactional
    public String createReturnOrder(Long orderId, ReturnRequestDTO returnRequest) {
        try {
            logger.info("Starting return creation for order ID: {}", orderId);
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

            if (!"delivered".equalsIgnoreCase(order.getOrderStatus())) {
                throw new RuntimeException("Order must be delivered to initiate a return");
            }

            List<String> orderItemIds = returnRequest.getOrderItemIds();
            String reason = returnRequest.getReason();

            if (orderItemIds == null || orderItemIds.isEmpty()) {
                throw new RuntimeException("Order item IDs are required for return");
            }
            if (reason == null || reason.trim().isEmpty()) {
                throw new RuntimeException("Return reason is required");
            }

            // Validate order item IDs
            List<Long> orderItemIdList = orderItemIds.stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Long> validOrderItemIds = order.getOrderItems().stream()
                    .map(OrderItemEntity::getOrderItemId)
                    .collect(Collectors.toList());
            if (!validOrderItemIds.containsAll(orderItemIdList)) {
                throw new RuntimeException("Invalid order item IDs provided for return");
            }

            String shiprocketReturnId = null;
            if (order.getShiprocketOrderId() != null && !order.getShiprocketOrderId().isEmpty()) {
                shiprocketReturnId = shiprocketService.createReturnOrder(order.getShiprocketOrderId(), orderItemIds, reason);
            } else {
                logger.warn("No Shiprocket order ID found for order ID: {}. Skipping Shiprocket return creation.", orderId);
            }

            // Create and save ReturnEntity
            ReturnEntity returnEntity = new ReturnEntity();
            returnEntity.setUser(order.getUser());
            returnEntity.setOrder(order);
            returnEntity.setShiprocketReturnId(shiprocketReturnId);
            returnEntity.setOrderItemIds(String.join(",", orderItemIds));
            returnEntity.setReason(reason);
            returnEntity.setReturnStatus("initiated");
            returnEntity.setCreatedAt(LocalDateTime.now());
            returnRepository.save(returnEntity);

            // Update order status
            order.setOrderStatus("return_initiated");
            try {
                restoreProductQuantities(order);
            } catch (Exception e) {
                logger.error("Failed to restore product quantities for order ID: {}: {}", orderId, e.getMessage());
                throw new RuntimeException("Failed to restore product quantities: " + e.getMessage());
            }
            orderRepository.save(order);

            logger.info("Return order created successfully for order ID: {}, Return ID: {}", orderId, shiprocketReturnId);
            return shiprocketReturnId != null ? shiprocketReturnId : returnEntity.getReturnId().toString();
        } catch (Exception e) {
            logger.error("Error creating return for order with id: {}", orderId, e);
            throw new RuntimeException("Failed to create return order: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public String createExchangeOrder(Long orderId, ExchangeRequestDTO exchangeRequest) {
        try {
            logger.info("Starting exchange creation for order ID: {}", orderId);
            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

            if (!"delivered".equalsIgnoreCase(order.getOrderStatus())) {
                throw new RuntimeException("Order must be delivered to initiate an exchange");
            }

            List<String> orderItemIds = exchangeRequest.getOrderItemIds();
            String newProductId = exchangeRequest.getNewProductId();
            String reason = exchangeRequest.getReason();

            if (orderItemIds == null || orderItemIds.isEmpty()) {
                throw new RuntimeException("Order item IDs are required for exchange");
            }
            if (newProductId == null || newProductId.trim().isEmpty()) {
                throw new RuntimeException("New product ID is required for exchange");
            }
            if (reason == null || reason.trim().isEmpty()) {
                throw new RuntimeException("Exchange reason is required");
            }

            // Validate order item IDs
            List<Long> orderItemIdList = orderItemIds.stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Long> validOrderItemIds = order.getOrderItems().stream()
                    .map(OrderItemEntity::getOrderItemId)
                    .collect(Collectors.toList());
            if (!validOrderItemIds.containsAll(orderItemIdList)) {
                throw new RuntimeException("Invalid order item IDs provided for exchange");
            }

            // Validate new product
            ProductEntity newProduct = productRepository.findById(Long.parseLong(newProductId))
                    .orElseThrow(() -> new RuntimeException("New product not found with id: " + newProductId));

            if (newProduct.getProductQuantity() < 1) {
                throw new RuntimeException("New product is out of stock");
            }

            String shiprocketExchangeId = null;
            if (order.getShiprocketOrderId() != null && !order.getShiprocketOrderId().isEmpty()) {
                shiprocketExchangeId = shiprocketService.createExchangeOrder(order.getShiprocketOrderId(), orderItemIds, newProductId, reason);
            } else {
                logger.warn("No Shiprocket order ID found for order ID: {}. Skipping Shiprocket exchange creation.", orderId);
            }

            // Create and save ExchangeEntity
            ExchangeEntity exchangeEntity = new ExchangeEntity();
            exchangeEntity.setUser(order.getUser());
            exchangeEntity.setOrder(order);
            exchangeEntity.setShiprocketExchangeId(shiprocketExchangeId);
            exchangeEntity.setOrderItemIds(String.join(",", orderItemIds));
            exchangeEntity.setNewProductId(Long.parseLong(newProductId));
            exchangeEntity.setReason(reason);
            exchangeEntity.setExchangeStatus("initiated");
            exchangeEntity.setCreatedAt(LocalDateTime.now());
            exchangeRepository.save(exchangeEntity);

            // Update order status and product quantities
            order.setOrderStatus("exchange_initiated");
            try {
                restoreProductQuantities(order);
            } catch (Exception e) {
                logger.error("Failed to restore product quantities for order ID: {}: {}", orderId, e.getMessage());
                throw new RuntimeException("Failed to restore product quantities: " + e.getMessage());
            }
            newProduct.setProductQuantity(newProduct.getProductQuantity() - 1);
            productRepository.save(newProduct);
            orderRepository.save(order);

            logger.info("Exchange order created successfully for order ID: {}, Exchange ID: {}", orderId, shiprocketExchangeId);
            return shiprocketExchangeId != null ? shiprocketExchangeId : exchangeEntity.getExchangeId().toString();
        } catch (Exception e) {
            logger.error("Error creating exchange for order with id: {}", orderId, e);
            throw new RuntimeException("Failed to create exchange order: " + e.getMessage());
        }
    }

    private void restoreProductQuantities(OrderEntity order) {
        try {
            for (OrderItemEntity item : order.getOrderItems()) {
                ProductEntity product = item.getProduct();
                product.setProductQuantity(product.getProductQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        } catch (Exception e) {
            logger.error("Error restoring product quantities for order ID: {}: {}", order.getOrderId(), e.getMessage());
            throw new RuntimeException("Failed to restore product quantities: " + e.getMessage());
        }
    }

}