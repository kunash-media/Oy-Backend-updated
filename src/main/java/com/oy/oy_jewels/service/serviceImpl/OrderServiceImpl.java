package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.OrderEntity;
import com.oy.oy_jewels.repository.OrderRepository;
import com.oy.oy_jewels.repository.ProductRepository;
import com.oy.oy_jewels.repository.UserRepository;
import com.oy.oy_jewels.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderEntity createOrder(OrderEntity order) {
        // Set order date to current date if not provided
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDate.now());
        }

        // Set default order status if not provided
        if (order.getOrderStatus() == null || order.getOrderStatus().isEmpty()) {
            order.setOrderStatus("pending");
        }

        return orderRepository.save(order);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    @Override
    public OrderEntity updateOrder(Long orderId, OrderEntity order) {
        OrderEntity existingOrder = getOrderById(orderId);

        // Update fields
        existingOrder.setCustomerName(order.getCustomerName());
        existingOrder.setProductName(order.getProductName());
        existingOrder.setQuantity(order.getQuantity());
        existingOrder.setProductPrice(order.getProductPrice());
        existingOrder.setTotalAmount(order.getTotalAmount());
        existingOrder.setPaymentMode(order.getPaymentMode());
        existingOrder.setOrderStatus(order.getOrderStatus());
        existingOrder.setDeliveryDate(order.getDeliveryDate());
        existingOrder.setOrderDate(order.getOrderDate());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        OrderEntity order = getOrderById(orderId);
        orderRepository.delete(order);
    }

    @Override
    public List<OrderEntity> getOrdersByCustomerName(String customerName) {
        return orderRepository.findByCustomerName(customerName);
    }

    @Override
    public List<OrderEntity> getOrdersByStatus(String orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public List<OrderEntity> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_UserId(userId);
    }
}

