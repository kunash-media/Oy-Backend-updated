package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.OrderEntity;
import com.oy.oy_jewels.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create new order
    @PostMapping("/create-order")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity order) {
        OrderEntity createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // Get all orders
    @GetMapping("/get-all-order")
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        List<OrderEntity> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Get order by ID
    @GetMapping("/get-by-orderId/{orderId}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long orderId) {
        OrderEntity order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Update order
    @PutMapping("/update-order/{orderId}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long orderId, @RequestBody OrderEntity order) {
        OrderEntity updatedOrder = orderService.updateOrder(orderId, order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    // Delete order
    @DeleteMapping("/delete-order/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
    }

    // Get orders by customer name
    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<OrderEntity>> getOrdersByCustomerName(@PathVariable String customerName) {
        List<OrderEntity> orders = orderService.getOrdersByCustomerName(customerName);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Get orders by status
    @GetMapping("/status/{orderStatus}")
    public ResponseEntity<List<OrderEntity>> getOrdersByStatus(@PathVariable String orderStatus) {
        List<OrderEntity> orders = orderService.getOrdersByStatus(orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Get orders by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderEntity>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}