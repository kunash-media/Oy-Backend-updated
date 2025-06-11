package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.CreateOrderRequest;
import com.oy.oy_jewels.dto.request.OrderItemRequest;
import com.oy.oy_jewels.dto.request.UpdateOrderRequest;
import com.oy.oy_jewels.dto.response.OrderResponse;
import com.oy.oy_jewels.entity.OrderEntity;
import com.oy.oy_jewels.entity.OrderItemEntity;
import com.oy.oy_jewels.entity.ProductEntity;
import com.oy.oy_jewels.entity.UserEntity;
import com.oy.oy_jewels.repository.OrderItemRepository;
import com.oy.oy_jewels.repository.OrderRepository;
import com.oy.oy_jewels.repository.ProductRepository;
import com.oy.oy_jewels.repository.UserRepository;
import com.oy.oy_jewels.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        // Fetch and validate user
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        if (!"active".equalsIgnoreCase(user.getStatus())) {
            throw new RuntimeException("User is not active. Cannot create order.");
        }

        // Create new order
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("processing");

        // Calculate total and create order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItemEntity> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {
            // Fetch product
            ProductEntity product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.getProductId()));

            // Check stock availability
//            if (!"instock".equalsIgnoreCase(product.getProductStock())) {
//                return new OrderResponse("out_of_stock",
//                        "Product '" + product.getProductTitle() + "' is out of stock",
//                        product.getProductId());
//            }

            if (itemRequest.getProductQuantity() > product.getProductQuantity()) {
                return new OrderResponse("insufficient_stock",
                        "Product '" + product.getProductTitle() + "' only has " + product.getProductQuantity() +
                                " items in stock (requested: " + itemRequest.getProductQuantity() + ")",
                        product.getProductId());
            }

            // Create order item
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getProductQuantity());
            orderItem.setProductPrice(product.getProductPrice());

            BigDecimal subtotal = product.getProductPrice().multiply(new BigDecimal(itemRequest.getProductQuantity()));
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(subtotal);

            // Update product quantity
            product.setProductQuantity(product.getProductQuantity() - itemRequest.getProductQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        // Save order
        OrderEntity savedOrder = orderRepository.save(order);

        return new OrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return new OrderResponse(order);
    }

    @Override
    public OrderResponse updateOrder(Long orderId, UpdateOrderRequest request) {
        OrderEntity existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Update provided fields
        if (request.getShippingAddress() != null) {
            existingOrder.setShippingAddress(request.getShippingAddress());
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
        return new OrderResponse(updatedOrder);
    }

    @Override
    public OrderResponse patchOrder(Long orderId, Map<String, Object> updates) {
        OrderEntity existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Apply partial updates
        updates.forEach((key, value) -> {
            switch (key) {
                case "shippingAddress":
                    existingOrder.setShippingAddress((String) value);
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
            }
        });

        OrderEntity updatedOrder = orderRepository.save(existingOrder);
        return new OrderResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Restore product quantities
        for (OrderItemEntity orderItem : order.getOrderItems()) {
            ProductEntity product = orderItem.getProduct();
            product.setProductQuantity(product.getProductQuantity() + orderItem.getQuantity());
            productRepository.save(product);
        }

        orderRepository.delete(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<OrderEntity> orders = orderRepository.findByUser_UserId(userId);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(String orderStatus) {
        List<OrderEntity> orders = orderRepository.findByOrderStatus(orderStatus);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrdersByProductId(Long productId) {
        return List.of();
    }

    @Override
    public List<OrderResponse> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        List<OrderEntity> orders = orderRepository.findByOrderDateBetween(startDate, endDate);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderEntity> findOrdersByProductId(Long productId) {
        List<OrderItemEntity> items = orderItemRepository.findByProduct_ProductId(productId);
        return items.stream()
                .map(OrderItemEntity::getOrder)
                .distinct()
                .collect(Collectors.toList());
    }

}