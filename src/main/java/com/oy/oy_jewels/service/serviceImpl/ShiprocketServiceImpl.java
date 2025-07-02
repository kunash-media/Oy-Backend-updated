package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.ShiprocketLoginRequest;
import com.oy.oy_jewels.dto.request.ShiprocketOrderItem;
import com.oy.oy_jewels.dto.request.ShiprocketOrderRequest;
import com.oy.oy_jewels.dto.response.ShiprocketLoginResponse;
import com.oy.oy_jewels.dto.response.ShiprocketOrderResponse;
import com.oy.oy_jewels.entity.OrderEntity;
import com.oy.oy_jewels.entity.OrderItemEntity;
import com.oy.oy_jewels.entity.ProductEntity;
import com.oy.oy_jewels.repository.OrderRepository;
import com.oy.oy_jewels.repository.ProductRepository;
import com.oy.oy_jewels.service.ShiprocketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShiprocketServiceImpl implements ShiprocketService {

    @Value("${shiprocket.api.base-url:https://apiv2.shiprocket.in/v1/external}")
    private String baseUrl;

    @Value("${shiprocket.email}")
    private String email;

    @Value("${shiprocket.password}")
    private String password;

    @Value("${shiprocket.pickup-location:Primary}")
    private String pickupLocation;

    @Value("${shiprocket.channel-id:}")
    private String channelId;


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private String authToken;
    private LocalDateTime tokenExpiry;

    public ShiprocketServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper,
                             OrderRepository orderRepository, ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    /**
     * Get authentication token from Shiprocket
     */
    public String getAuthToken() {
        // Check if token is still valid (assuming 24-hour validity)
        if (authToken != null && tokenExpiry != null && LocalDateTime.now().isBefore(tokenExpiry)) {
            return authToken;
        }

        try {
            String loginUrl = baseUrl + "/auth/login";

            ShiprocketLoginRequest loginRequest = new ShiprocketLoginRequest(email, password);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ShiprocketLoginRequest> request = new HttpEntity<>(loginRequest, headers);

            ResponseEntity<ShiprocketLoginResponse> response = restTemplate.exchange(
                    loginUrl, HttpMethod.POST, request, ShiprocketLoginResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ShiprocketLoginResponse loginResponse = response.getBody();
                if (loginResponse.isSuccess()) {
                    this.authToken = loginResponse.getToken();
                    this.tokenExpiry = LocalDateTime.now().plusHours(23); // Set expiry to 23 hours
                    return this.authToken;
                } else {
                    throw new RuntimeException("Shiprocket login failed: " + loginResponse.getMessage());
                }
            } else {
                throw new RuntimeException("Failed to authenticate with Shiprocket");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during Shiprocket authentication: " + e.getMessage(), e);
        }
    }

    /**
     * Create order in Shiprocket
     */
    public ShiprocketOrderResponse createShiprocketOrder(OrderEntity order) {
        try {
            String createOrderUrl = baseUrl + "/orders/create/adhoc";
            String token = getAuthToken();

            ShiprocketOrderRequest shiprocketOrder = convertToShiprocketOrder(order);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            HttpEntity<ShiprocketOrderRequest> request = new HttpEntity<>(shiprocketOrder, headers);

            ResponseEntity<ShiprocketOrderResponse> response = restTemplate.exchange(
                    createOrderUrl, HttpMethod.POST, request, ShiprocketOrderResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ShiprocketOrderResponse shiprocketResponse = response.getBody();

                // Update order with Shiprocket details
                order.setShiprocketOrderId(shiprocketResponse.getOrder_id());
                order.setShiprocketShipmentId(shiprocketResponse.getShipment_id());
                order.setAwbCode(shiprocketResponse.getAwb_code());
                if (shiprocketResponse.getCourier_company_id() != null) {
                    order.setCourierCompanyId(Integer.valueOf(shiprocketResponse.getCourier_company_id()));
                }

                orderRepository.save(order);

                return shiprocketResponse;
            } else {
                throw new RuntimeException("Failed to create order in Shiprocket");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating Shiprocket order: " + e.getMessage(), e);
        }
    }

    /**
     * Convert OrderEntity to ShiprocketOrderRequest
     */
    private ShiprocketOrderRequest convertToShiprocketOrder(OrderEntity order) {
        ShiprocketOrderRequest shiprocketOrder = new ShiprocketOrderRequest();

        // Basic order details
        shiprocketOrder.setOrder_id(String.valueOf(order.getOrderId()));
        shiprocketOrder.setOrder_date(order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        shiprocketOrder.setPickup_location(pickupLocation);
        shiprocketOrder.setChannel_id(channelId);
        shiprocketOrder.setComment("Order from ecommerce platform");

        // Billing details (assuming billing = shipping for simplicity)
        shiprocketOrder.setBilling_customer_name(order.getCustomerName());
        shiprocketOrder.setBilling_last_name(""); // You may need to split name
        shiprocketOrder.setBilling_address(order.getShippingAddress());
        shiprocketOrder.setBilling_address_2("");
        shiprocketOrder.setBilling_city(order.getShippingCity());
        shiprocketOrder.setBilling_pincode(order.getShippingPincode());
        shiprocketOrder.setBilling_state(order.getShippingState());
        shiprocketOrder.setBilling_country(order.getShippingCountry());
        shiprocketOrder.setBilling_email(order.getCustomerEmail());
        shiprocketOrder.setBilling_phone(order.getCustomerPhone());

        // Shipping details
        shiprocketOrder.setShipping_is_billing("1"); // 1 = true, 0 = false
        shiprocketOrder.setShipping_customer_name(order.getCustomerName());
        shiprocketOrder.setShipping_last_name("");
        shiprocketOrder.setShipping_address(order.getShippingAddress());
        shiprocketOrder.setShipping_address_2("");
        shiprocketOrder.setShipping_city(order.getShippingCity());
        shiprocketOrder.setShipping_pincode(order.getShippingPincode());
        shiprocketOrder.setShipping_state(order.getShippingState());
        shiprocketOrder.setShipping_country(order.getShippingCountry());
        shiprocketOrder.setShipping_email(order.getCustomerEmail());
        shiprocketOrder.setShipping_phone(order.getCustomerPhone());

        // Payment method
        shiprocketOrder.setPayment_method(order.getPaymentMethod().equals("COD") ? "COD" : "Prepaid");

        // Order items
        List<ShiprocketOrderItem> shiprocketItems = new ArrayList<>();
        double totalWeight = 0.0;
        int totalLength = 0, totalBreadth = 0, totalHeight = 0;

        for (OrderItemEntity orderItem : order.getOrderItems()) {
            ProductEntity product = productRepository.findById(orderItem.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            ShiprocketOrderItem item = new ShiprocketOrderItem();
            item.setName(product.getProductTitle());
            item.setSku(product.getSkuNo() != null ? product.getSkuNo() : "SKU-" + product.getProductId());
            item.setUnits(orderItem.getQuantity());
            item.setSelling_price(product.getProductPrice().doubleValue());
            item.setDiscount(0.0);
            item.setTax("0"); // You can calculate tax based on your business logic
//            item.setHsn(product.getHsnCode() != null ? product.getHsnCode() : "");

            shiprocketItems.add(item);

            // Calculate total dimensions and weight
//            if (product.getWeight() != null) {
//                totalWeight += product.getWeight() * orderItem.getQuantity();
//            }
//            if (product.getLength() != null) {
//                totalLength = Math.max(totalLength, product.getLength().intValue());
//            }
//            if (product.getBreadth() != null) {
//                totalBreadth = Math.max(totalBreadth, product.getBreadth().intValue());
//            }
//            if (product.getHeight() != null) {
//                totalHeight += product.getHeight().intValue(); // Stack height
//            }
        }

        shiprocketOrder.setOrder_items(shiprocketItems);

        // Financial details
        shiprocketOrder.setSub_total(order.getTotalAmount().doubleValue());
        shiprocketOrder.setShipping_charges(0.0);
        shiprocketOrder.setGiftwrap_charges(0.0);
        shiprocketOrder.setTransaction_charges(0.0);
        shiprocketOrder.setTotal_discount(0.0);

        // Package dimensions and weight
        shiprocketOrder.setLength(totalLength > 0 ? totalLength : 10); // Default 10cm if not specified
        shiprocketOrder.setBreadth(totalBreadth > 0 ? totalBreadth : 10);
        shiprocketOrder.setHeight(totalHeight > 0 ? totalHeight : 10);
        shiprocketOrder.setWeight(totalWeight > 0 ? totalWeight : 0.5); // Default 0.5kg if not specified

        return shiprocketOrder;
    }

    /**
     * Get tracking details for an order
     */
    public String getTrackingDetails(String awbCode) {
        try {
            String trackingUrl = baseUrl + "/courier/track/awb/" + awbCode;
            String token = getAuthToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<?> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    trackingUrl, HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to get tracking details");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting tracking details: " + e.getMessage(), e);
        }
    }

    /**
     * Cancel order in Shiprocket
     */
    public boolean cancelShiprocketOrder(String orderId) {
        try {
            String cancelUrl = baseUrl + "/orders/cancel";
            String token = getAuthToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            String requestBody = "{\"ids\":[\"" + orderId + "\"]}";
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    cancelUrl, HttpMethod.POST, request, String.class);

            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            throw new RuntimeException("Error cancelling Shiprocket order: " + e.getMessage(), e);
        }
    }

    /**
     * Get available courier services for pincode
     */

    public String getAvailableCouriers(String pickupPincode, String deliveryPincode, double weight) {
        try {
            String courierUrl = baseUrl + "/courier/serviceability" +
                    "?pickup_postcode=" + pickupPincode +
                    "&delivery_postcode=" + deliveryPincode +
                    "&weight=" + weight +
                    "&cod=1";

            String token = getAuthToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<?> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    courierUrl, HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to get courier serviceability");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting courier serviceability: " + e.getMessage(), e);
        }
    }
}
