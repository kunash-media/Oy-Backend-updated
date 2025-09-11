package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    // Find orders by user ID
    List<OrderEntity> findByUser_UserId(Long userId);

    // Find orders by order status
    List<OrderEntity> findByOrderStatus(String orderStatus);

    // Find orders by date range
    List<OrderEntity> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    // Find orders by user and status
    List<OrderEntity> findByUser_UserIdAndOrderStatus(Long userId, String orderStatus);

    Optional<OrderEntity> findByShiprocketOrderId(String shiprocketOrderId);

    // You can also add other useful methods
    Optional<OrderEntity> findByAwbCode(String awbCode);
    Optional<OrderEntity> findByShiprocketShipmentId(String shiprocketShipmentId);


    // New method to check if a coupon code exists in orders
    // boolean existsByCouponAppliedCode(String couponAppliedCode);

    // New method with explicit query
    @Query("SELECT EXISTS (SELECT 1 FROM OrderEntity o WHERE o.couponAppliedCode = :couponCode AND o.user.userId = :userId)")
    boolean existsByCouponAppliedCodeAndUserId(@Param("couponCode") String couponAppliedCode, @Param("userId") Long userId);


    //------- dashboard data-----//
    // Count orders for today
    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.orderDate = :today")
    long countTodayOrders(LocalDate today);

    // Count orders for yesterday
    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.orderDate = :yesterday")
    long countYesterdayOrders(LocalDate yesterday);

    // Count orders for the current month
    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month")
    long countThisMonthOrders(int year, int month);

    // Count orders for the previous month
    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month")
    long countLastMonthOrders(int year, int month);

    // Sum total_amount for all orders
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM OrderEntity o")
    java.math.BigDecimal getAllTimeSales();

    // Count total orders
    @Query("SELECT COUNT(o) FROM OrderEntity o")
    long countTotalOrders();

    // Count orders by status
    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.orderStatus = :status")
    long countOrdersByStatus(String status);

    // Count orders by payment method
    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.paymentMethod = :method")
    long countOrdersByPaymentMethod(String method);

}
