package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.CreateOrderRequest;
import com.oy.oy_jewels.dto.request.UpdateOrderRequest;
import com.oy.oy_jewels.dto.response.OrderResponse;
import com.oy.oy_jewels.entity.OrderEntity;
import com.oy.oy_jewels.entity.ProductEntity;
import com.oy.oy_jewels.entity.UserEntity;
import com.oy.oy_jewels.repository.OrderRepository;
import com.oy.oy_jewels.repository.ProductRepository;
import com.oy.oy_jewels.repository.UserRepository;
import com.oy.oy_jewels.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        // Validate input
        if (request.getUserId() == null || request.getProductId() == null) {
            throw new IllegalArgumentException("User ID and Product ID are required");
        }

        // Fetch user from database
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        // Check if user is active
        if (!"active".equalsIgnoreCase(user.getStatus())) {
            throw new RuntimeException("User is not active. Cannot create order.");
        }

        // Fetch product from database
        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));

        // Check product stock
        if (!"instock".equalsIgnoreCase(product.getProductStock())) {
            throw new RuntimeException("Product is out of stock");
        }

        // Check if requested quantity is available
        if (request.getQuantity() > product.getProductQuantity()) {
            throw new RuntimeException("Requested quantity exceeds available stock");
        }

        // Create new order entity
        OrderEntity order = new OrderEntity();
        order.setQuantity(request.getQuantity());

        // Use product price if not provided in request
        order.setProductPrice(request.getProductPrice() != null ?
                request.getProductPrice() : product.getProductPrice());

        // Calculate total amount if not provided
        if (request.getTotalAmount() != null) {
            order.setTotalAmount(request.getTotalAmount());
        } else {
            BigDecimal totalAmount = order.getProductPrice().multiply(new BigDecimal(request.getQuantity()));
            order.setTotalAmount(totalAmount);
        }

        order.setPaymentMode(request.getPaymentMode());
        order.setDeliveryDate(request.getDeliveryDate());

        // Set relationships
        order.setUser(user);
        order.setProduct(product);

        // Set order date to current date if not provided
        order.setOrderDate(request.getOrderDate() != null ? request.getOrderDate() : LocalDate.now());

        // Set default order status if not provided
        order.setOrderStatus(request.getOrderStatus() != null && !request.getOrderStatus().isEmpty() ?
                request.getOrderStatus() : "PENDING");

        // Save the order
        OrderEntity savedOrder = orderRepository.save(order);

        // Update product quantity
        product.setProductQuantity(product.getProductQuantity() - request.getQuantity());
        productRepository.save(product);

        return new OrderResponse(savedOrder);
    }

    @Override
//    @Transactional(readOnly = true)
    @Transactional
    public List<OrderResponse> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Override
//    @Transactional(readOnly = true)
    @Transactional
    public OrderResponse getOrderById(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return new OrderResponse(order);
    }

    @Override
    public OrderResponse updateOrder(Long orderId, UpdateOrderRequest request) {
        OrderEntity existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Update only provided fields
        if (request.getQuantity() != null) {
            // Check if new quantity is available
            ProductEntity product = existingOrder.getProduct();
            int quantityDifference = request.getQuantity() - existingOrder.getQuantity();

            if (quantityDifference > 0 && quantityDifference > product.getProductQuantity()) {
                throw new RuntimeException("Requested quantity exceeds available stock");
            }

            // Update product quantity
            product.setProductQuantity(product.getProductQuantity() - quantityDifference);
            productRepository.save(product);

            existingOrder.setQuantity(request.getQuantity());

            // Recalculate total amount
            if (request.getTotalAmount() == null) {
                BigDecimal totalAmount = existingOrder.getProductPrice().multiply(new BigDecimal(request.getQuantity()));
                existingOrder.setTotalAmount(totalAmount);
            }
        }

        if (request.getProductPrice() != null) {
            existingOrder.setProductPrice(request.getProductPrice());
        }

        if (request.getTotalAmount() != null) {
            existingOrder.setTotalAmount(request.getTotalAmount());
        }

        if (request.getPaymentMode() != null) {
            existingOrder.setPaymentMode(request.getPaymentMode());
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
    public void deleteOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Restore product quantity
        ProductEntity product = order.getProduct();
        product.setProductQuantity(product.getProductQuantity() + order.getQuantity());
        productRepository.save(product);

        orderRepository.delete(order);
    }

    @Override
//    @Transactional(readOnly = true)
    @Transactional
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<OrderEntity> orders = orderRepository.findByUser_UserId(userId);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Override
//    @Transactional(readOnly = true)
    @Transactional
    public List<OrderResponse> getOrdersByStatus(String orderStatus) {
        List<OrderEntity> orders = orderRepository.findByOrderStatus(orderStatus);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Override
//    @Transactional(readOnly = true)
    @Transactional
    public List<OrderResponse> getOrdersByProductId(Long productId) {
        List<OrderEntity> orders = orderRepository.findByProduct_ProductId(productId);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Override
//    @Transactional(readOnly = true)
    @Transactional
    public List<OrderResponse> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        List<OrderEntity> orders = orderRepository.findByOrderDateBetween(startDate, endDate);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }
}
