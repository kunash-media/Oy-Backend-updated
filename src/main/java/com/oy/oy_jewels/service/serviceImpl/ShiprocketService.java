package com.oy.oy_jewels.service.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oy.oy_jewels.dto.request.ShiprocketLoginRequest;
import com.oy.oy_jewels.dto.request.ShiprocketOrderRequest;
import com.oy.oy_jewels.dto.response.ShiprocketLoginResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShiprocketService {

    private static final Logger logger = LoggerFactory.getLogger(ShiprocketService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${shiprocket.api.url:https://apiv2.shiprocket.in/v1/external}")
    private String shiprocketApiUrl;

    @Value("${shiprocket.email}")
    private String shiprocketEmail;

    @Value("${shiprocket.password}")
    private String shiprocketPassword;

    private final RestTemplate restTemplate;
    private String cachedToken;
    private LocalDateTime tokenExpiry;

    public ShiprocketService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Get or refresh Shiprocket authentication token
     */
    private String getAuthToken() {
        // Check if token is still valid (assuming 24-hour validity)
        if (cachedToken != null && tokenExpiry != null && LocalDateTime.now().isBefore(tokenExpiry)) {
            logger.info("Using cached Shiprocket token (expires at: {})", tokenExpiry);
            return cachedToken;
        }

        try {
            String loginUrl = shiprocketApiUrl + "/auth/login";
            logger.info("Attempting to authenticate with Shiprocket at: {}", loginUrl);
            logger.debug("Authentication email: {}", shiprocketEmail);

            ShiprocketLoginRequest loginRequest = new ShiprocketLoginRequest();
            loginRequest.setEmail(shiprocketEmail);
            loginRequest.setPassword(shiprocketPassword);

            // Log the request payload (without password for security)
            ShiprocketLoginRequest logRequest = new ShiprocketLoginRequest();
            logRequest.setEmail(shiprocketEmail);
            logRequest.setPassword("***MASKED***");
            logger.debug("Authentication request payload: {}", objectMapper.writeValueAsString(logRequest));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            logger.debug("Authentication request headers: {}", headers);

            HttpEntity<ShiprocketLoginRequest> request = new HttpEntity<>(loginRequest, headers);

            ResponseEntity<ShiprocketLoginResponse> response = restTemplate.postForEntity(
                    loginUrl, request, ShiprocketLoginResponse.class);

            logger.info("Authentication response status: {}", response.getStatusCode());
            logger.debug("Authentication response headers: {}", response.getHeaders());

            if (response.getBody() != null && response.getBody().getToken() != null) {
                cachedToken = response.getBody().getToken();
                tokenExpiry = LocalDateTime.now().plusHours(23); // Set expiry to 23 hours
                logger.info("Shiprocket authentication successful. Token cached until: {}", tokenExpiry);
                logger.debug("Token (first 20 chars): {}...", cachedToken.substring(0, Math.min(20, cachedToken.length())));
                return cachedToken;
            } else {
                logger.error("Authentication failed - No token received in response: {}", response.getBody());
                throw new RuntimeException("Failed to authenticate with Shiprocket - No token received");
            }

        } catch (HttpClientErrorException e) {
            logger.error("HTTP error during Shiprocket authentication:");
            logger.error("Status Code: {}", e.getStatusCode());
            logger.error("Response Body: {}", e.getResponseBodyAsString());
            logger.error("Request Headers: {}", e.getResponseHeaders());
            throw new RuntimeException("Shiprocket authentication failed: " + e.getMessage());
        } catch (ResourceAccessException e) {
            logger.error("Network error during Shiprocket authentication", e);
            throw new RuntimeException("Network error during Shiprocket authentication: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during Shiprocket authentication", e);
            throw new RuntimeException("Shiprocket authentication failed: " + e.getMessage());
        }
    }

    /**
     * Create order in Shiprocket
     */
    public String createOrder(ShiprocketOrderRequest orderRequest) {
        try {
            logger.info("Starting Shiprocket order creation for order ID: {}", orderRequest.getOrder_id());

            String token = getAuthToken();
            String createOrderUrl = shiprocketApiUrl + "/orders/create/adhoc";

            logger.info("Shiprocket order creation URL: {}", createOrderUrl);
            logger.debug("Using token (first 20 chars): {}...", token.substring(0, Math.min(20, token.length())));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            logger.debug("Request headers: {}", headers);
            String requestJson = objectMapper.writeValueAsString(orderRequest);
            logger.info("Shiprocket order request payload: {}", requestJson);

            HttpEntity<ShiprocketOrderRequest> request = new HttpEntity<>(orderRequest, headers);

            logger.info("Sending POST request to Shiprocket...");
            ResponseEntity<Map> response = restTemplate.postForEntity(createOrderUrl, request, Map.class);

            logger.info("Shiprocket API response status: {}", response.getStatusCode());
            logger.debug("Shiprocket API response headers: {}", response.getHeaders());

            if (response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                String responseJson = objectMapper.writeValueAsString(responseBody);
                logger.info("Shiprocket API complete response: {}", responseJson);

                if (response.getStatusCode().is2xxSuccessful()) {
                    // Try different possible field names for order_id
                    String orderId = null;
                    if (responseBody.containsKey("order_id")) {
                        orderId = String.valueOf(responseBody.get("order_id"));
                    } else if (responseBody.containsKey("id")) {
                        orderId = String.valueOf(responseBody.get("id"));
                    } else if (responseBody.containsKey("data") && responseBody.get("data") instanceof Map) {
                        Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                        orderId = String.valueOf(data.getOrDefault("order_id", data.get("id")));
                    }

                    if (orderId != null && !orderId.isEmpty()) {
                        logger.info("Shiprocket order created successfully. Order ID: {}", orderId);
                        return orderId;
                    } else {
                        logger.error("Shiprocket order creation response does not contain a valid order_id: {}", responseBody);
                        throw new RuntimeException("Shiprocket order creation failed: No valid order_id in response");
                    }
                } else {
                    logger.error("Shiprocket order creation failed - Response: {}", responseBody);
                    throw new RuntimeException("Shiprocket order creation failed: " + responseBody.getOrDefault("message", "Unknown error"));
                }
            } else {
                logger.error("Empty response body from Shiprocket");
                throw new RuntimeException("Empty response from Shiprocket");
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Client Error during Shiprocket order creation: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Shiprocket order creation failed: " + e.getStatusCode() + " " + e.getStatusText());
        } catch (ResourceAccessException e) {
            logger.error("Network/Resource Access error during Shiprocket order creation:", e);
            throw new RuntimeException("Network error during Shiprocket order creation: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during Shiprocket order creation:", e);
            throw new RuntimeException("Failed to create Shiprocket order: " + e.getMessage());
        }
    }

    /**
     * Test method to verify Shiprocket connectivity
     */
    public String testConnection() {
        try {
            logger.info("Testing Shiprocket connection...");
            String token = getAuthToken();
            logger.info("Connection test successful");
            return "Successfully connected to Shiprocket. Token: " + token.substring(0, Math.min(20, token.length())) + "...";
        } catch (Exception e) {
            logger.error("Connection test failed: {}", e.getMessage());
            return "Failed to connect to Shiprocket: " + e.getMessage();
        }
    }

    /**
     * Additional method to validate request data before sending
     */
    public void validateOrderRequest(ShiprocketOrderRequest orderRequest) {
        logger.info("Validating order request for order ID: {}", orderRequest.getOrder_id());

        // Add validation logic here and log any issues
        if (orderRequest.getOrder_id() == null || orderRequest.getOrder_id().isEmpty()) {
            logger.error("Order ID is null or empty");
        }

        if (orderRequest.getOrder_date() == null || orderRequest.getOrder_date().isEmpty()) {
            logger.error("Order date is null or empty");
        }

        if (orderRequest.getPickup_location() == null || orderRequest.getPickup_location().isEmpty()) {
            logger.error("Pickup location is null or empty");
        }

        if (orderRequest.getBilling_customer_name() == null || orderRequest.getBilling_customer_name().isEmpty()) {
            logger.error("Billing customer name is null or empty");
        }

        if (orderRequest.getBilling_address() == null || orderRequest.getBilling_address().isEmpty()) {
            logger.error("Billing address is null or empty");
        }

        if (orderRequest.getBilling_pincode() == null || orderRequest.getBilling_pincode().isEmpty()) {
            logger.error("Billing pincode is null or empty");
        }

        if (orderRequest.getBilling_city() == null || orderRequest.getBilling_city().isEmpty()) {
            logger.error("Billing city is null or empty");
        }

        if (orderRequest.getBilling_state() == null || orderRequest.getBilling_state().isEmpty()) {
            logger.error("Billing state is null or empty");
        }

        if (orderRequest.getBilling_country() == null || orderRequest.getBilling_country().isEmpty()) {
            logger.error("Billing country is null or empty");
        }

        if (orderRequest.getBilling_phone() == null || orderRequest.getBilling_phone().isEmpty()) {
            logger.error("Billing phone is null or empty");
        }

        if (orderRequest.getOrder_items() == null || orderRequest.getOrder_items().isEmpty()) {
            logger.error("Order items are null or empty");
        }

        if (orderRequest.getPayment_method() == null || orderRequest.getPayment_method().isEmpty()) {
            logger.error("Payment method is null or empty");
        }

        if (orderRequest.getShipping_phone() == null) {
            logger.error("Shipping charges is null");
        }

        if (orderRequest.getGiftwrap_charges() == null) {
            logger.error("Giftwrap charges is null");
        }

        if (orderRequest.getTransaction_charges() == null) {
            logger.error("Transaction charges is null");
        }

        if (orderRequest.getTotal_discount() == null) {
            logger.error("Total discount is null");
        }

        if (orderRequest.getSub_total() == null) {
            logger.error("Sub total is null");
        }

        if (orderRequest.getLength() == null) {
            logger.error("Length is null");
        }

        if (orderRequest.getBreadth() == null) {
            logger.error("Breadth is null");
        }

        if (orderRequest.getHeight() == null) {
            logger.error("Height is null");
        }

        if (orderRequest.getWeight() == null) {
            logger.error("Weight is null");
        }

        logger.info("Order request validation completed");
    }



    public String getOrderStatus(String shiprocketOrderId) {
        try {
            logger.info("Fetching status for Shiprocket order ID: {}", shiprocketOrderId);
            String token = getAuthToken();
            String url = shiprocketApiUrl + "/orders/show/" + shiprocketOrderId;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            HttpEntity<?> request = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

            logger.info("Shiprocket API response status: {}", response.getStatusCode());
            logger.debug("Shiprocket API response headers: {}", response.getHeaders());
            logger.debug("Shiprocket API response body: {}", response.getBody());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                if (data != null) {
                    Map<String, Object> order = (Map<String, Object>) data.get("order");
                    if (order != null) {
                        String status = (String) order.get("status");
                        if (status != null) {
                            logger.info("Order status for Shiprocket order ID {}: {}", shiprocketOrderId, status);
                            return status;
                        } else {
                            logger.error("Status field not found in order data: {}", order);
                            throw new RuntimeException("Order status not found in response");
                        }
                    } else {
                        logger.error("Order object not found in data: {}", data);
                        throw new RuntimeException("Order object not found in response");
                    }
                } else {
                    logger.error("Data object not found in response: {}", responseBody);
                    throw new RuntimeException("Data object not found in response");
                }
            } else {
                logger.error("Failed to fetch order status: Invalid response or status code {}", response.getStatusCode());
                throw new RuntimeException("Failed to fetch order status: Invalid response");
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Client Error during Shiprocket order status fetch: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Shiprocket order status fetch failed: " + e.getStatusCode() + " " + e.getStatusText());
        } catch (ResourceAccessException e) {
            logger.error("Network error during Shiprocket order status fetch: {}", e.getMessage());
            throw new RuntimeException("Network error during Shiprocket order status fetch: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during Shiprocket order status fetch: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch Shiprocket order status: " + e.getMessage());
        }
    }

    public void cancelOrder(String shiprocketOrderId) {
        try {
            logger.info("Starting Shiprocket order cancellation for Shiprocket order ID: {}", shiprocketOrderId);
            String token = getAuthToken();
            String cancelOrderUrl = shiprocketApiUrl + "/orders/cancel";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            Map<String, Object> cancelRequest = new HashMap<>();
            cancelRequest.put("ids", List.of(shiprocketOrderId));

            logger.debug("Request headers: {}", headers);
            String requestJson = objectMapper.writeValueAsString(cancelRequest);
            logger.info("Shiprocket cancel order request payload: {}", requestJson);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(cancelRequest, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(cancelOrderUrl, request, Map.class);

            logger.info("Shiprocket API response status: {}", response.getStatusCode());
            logger.debug("Shiprocket API response headers: {}", response.getHeaders());

            if (response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                String responseJson = objectMapper.writeValueAsString(responseBody);
                logger.info("Shiprocket API complete response: {}", responseJson);

                if (response.getStatusCode().is2xxSuccessful() &&
                        responseBody.containsKey("status_code") &&
                        responseBody.get("status_code").equals(200) &&
                        responseBody.containsKey("message") &&
                        "Order cancelled successfully.".equalsIgnoreCase(responseBody.get("message").toString())) {
                    logger.info("Shiprocket order cancelled successfully for ID: {}", shiprocketOrderId);
                } else {
                    logger.error("Shiprocket order cancellation failed - Response: {}", responseBody);
                    throw new RuntimeException("Shiprocket order cancellation failed: " +
                            responseBody.getOrDefault("message", "Unknown error"));
                }
            } else {
                logger.error("Empty response body from Shiprocket");
                throw new RuntimeException("Empty response from Shiprocket");
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Client Error during Shiprocket order cancellation: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Shiprocket order cancellation failed: " + e.getStatusCode() + " " + e.getStatusText());
        } catch (ResourceAccessException e) {
            logger.error("Network/Resource Access error during Shiprocket order cancellation:", e);
            throw new RuntimeException("Network error during Shiprocket order cancellation: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during Shiprocket order cancellation:", e);
            throw new RuntimeException("Failed to cancel Shiprocket order: " + e.getMessage());
        }
    }


    public String createReturnOrder(String shiprocketOrderId, List<String> orderItemIds, String reason) {
        try {
            logger.info("Starting Shiprocket return order creation for Shiprocket order ID: {}", shiprocketOrderId);

            String token = getAuthToken();
            String returnOrderUrl = shiprocketApiUrl + "/orders/create/return";

            logger.info("Shiprocket return order creation URL: {}", returnOrderUrl);
            logger.debug("Using token (first 20 chars): {}...", token.substring(0, Math.min(20, token.length())));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            Map<String, Object> returnRequest = new HashMap<>();
            returnRequest.put("order_id", shiprocketOrderId);
            returnRequest.put("order_item_ids", orderItemIds);
            returnRequest.put("reason", reason);

            logger.debug("Request headers: {}", headers);
            try {
                String requestJson = objectMapper.writeValueAsString(returnRequest);
                logger.info("Shiprocket return order request payload: {}", requestJson);
            } catch (Exception e) {
                logger.warn("Failed to serialize return request payload for logging: {}", e.getMessage());
                logger.info("Shiprocket return order request object: {}", returnRequest);
            }

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(returnRequest, headers);

            logger.info("Sending POST request to Shiprocket for return order...");
            ResponseEntity<Map> response = restTemplate.postForEntity(returnOrderUrl, request, Map.class);

            logger.info("Shiprocket API response status: {}", response.getStatusCode());
            logger.debug("Shiprocket API response headers: {}", response.getHeaders());

            if (response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                try {
                    String responseJson = objectMapper.writeValueAsString(responseBody);
                    logger.info("Shiprocket API complete response: {}", responseJson);
                } catch (Exception e) {
                    logger.warn("Failed to serialize response for logging: {}", e.getMessage());
                    logger.info("Shiprocket API response object: {}", responseBody);
                }

                if (response.getStatusCode().is2xxSuccessful()) {
                    String returnId = (String) responseBody.get("return_id");
                    logger.info("Shiprocket return order created successfully. Return ID: {}", returnId);
                    return returnId;
                } else {
                    logger.error("Shiprocket return order creation failed - Response: {}", responseBody);
                    throw new RuntimeException("Shiprocket return order creation failed: " + responseBody.get("message"));
                }
            } else {
                logger.error("Empty response body from Shiprocket");
                throw new RuntimeException("Empty response from Shiprocket");
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Client Error during Shiprocket return order creation:");
            logger.error("Status Code: {}", e.getStatusCode());
            logger.error("Status Text: {}", e.getStatusText());
            logger.error("Response Body: {}", e.getResponseBodyAsString());
            logger.error("Response Headers: {}", e.getResponseHeaders());
            try {
                String errorBody = e.getResponseBodyAsString();
                if (errorBody != null && !errorBody.isEmpty()) {
                    logger.error("Detailed error response: {}", errorBody);
                    try {
                        JsonNode errorJson = objectMapper.readTree(errorBody);
                        logger.error("Parsed error details: {}", errorJson.toPrettyString());
                    } catch (Exception jsonEx) {
                        logger.debug("Error response is not valid JSON: {}", jsonEx.getMessage());
                    }
                }
            } catch (Exception ex) {
                logger.warn("Could not parse error response: {}", ex.getMessage());
            }
            throw new RuntimeException("Shiprocket return order creation failed: " + e.getStatusCode() + " " + e.getStatusText());
        } catch (ResourceAccessException e) {
            logger.error("Network/Resource Access error during Shiprocket return order creation:", e);
            throw new RuntimeException("Network error during Shiprocket return order creation: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during Shiprocket return order creation:", e);
            throw new RuntimeException("Failed to create Shiprocket return order: " + e.getMessage());
        }
    }

    public String createExchangeOrder(String shiprocketOrderId, List<String> orderItemIds, String newProductId, String reason) {
        try {
            logger.info("Starting Shiprocket exchange order creation for Shiprocket order ID: {}", shiprocketOrderId);

            String token = getAuthToken();
            String exchangeOrderUrl = shiprocketApiUrl + "/orders/create/exchange";

            logger.info("Shiprocket exchange order creation URL: {}", exchangeOrderUrl);
            logger.debug("Using token (first 20 chars): {}...", token.substring(0, Math.min(20, token.length())));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            Map<String, Object> exchangeRequest = new HashMap<>();
            exchangeRequest.put("order_id", shiprocketOrderId);
            exchangeRequest.put("order_item_ids", orderItemIds);
            exchangeRequest.put("new_product_id", newProductId);
            exchangeRequest.put("reason", reason);

            logger.debug("Request headers: {}", headers);
            try {
                String requestJson = objectMapper.writeValueAsString(exchangeRequest);
                logger.info("Shiprocket exchange order request payload: {}", requestJson);
            } catch (Exception e) {
                logger.warn("Failed to serialize exchange request payload for logging: {}", e.getMessage());
                logger.info("Shiprocket exchange order request object: {}", exchangeRequest);
            }

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(exchangeRequest, headers);

            logger.info("Sending POST request to Shiprocket for exchange order...");
            ResponseEntity<Map> response = restTemplate.postForEntity(exchangeOrderUrl, request, Map.class);

            logger.info("Shiprocket API response status: {}", response.getStatusCode());
            logger.debug("Shiprocket API response headers: {}", response.getHeaders());

            if (response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                try {
                    String responseJson = objectMapper.writeValueAsString(responseBody);
                    logger.info("Shiprocket API complete response: {}", responseJson);
                } catch (Exception e) {
                    logger.warn("Failed to serialize response for logging: {}", e.getMessage());
                    logger.info("Shiprocket API response object: {}", responseBody);
                }

                if (response.getStatusCode().is2xxSuccessful()) {
                    String exchangeId = (String) responseBody.get("exchange_id");
                    logger.info("Shiprocket exchange order created successfully. Exchange ID: {}", exchangeId);
                    return exchangeId;
                } else {
                    logger.error("Shiprocket exchange order creation failed - Response: {}", responseBody);
                    throw new RuntimeException("Shiprocket exchange order creation failed: " + responseBody.get("message"));
                }
            } else {
                logger.error("Empty response body from Shiprocket");
                throw new RuntimeException("Empty response from Shiprocket");
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Client Error during Shiprocket exchange order creation:");
            logger.error("Status Code: {}", e.getStatusCode());
            logger.error("Status Text: {}", e.getStatusText());
            logger.error("Response Body: {}", e.getResponseBodyAsString());
            logger.error("Response Headers: {}", e.getResponseHeaders());
            try {
                String errorBody = e.getResponseBodyAsString();
                if (errorBody != null && !errorBody.isEmpty()) {
                    logger.error("Detailed error response: {}", errorBody);
                    try {
                        JsonNode errorJson = objectMapper.readTree(errorBody);
                        logger.error("Parsed error details: {}", errorJson.toPrettyString());
                    } catch (Exception jsonEx) {
                        logger.debug("Error response is not valid JSON: {}", jsonEx.getMessage());
                    }
                }
            } catch (Exception ex) {
                logger.warn("Could not parse error response: {}", ex.getMessage());
            }
            throw new RuntimeException("Shiprocket exchange order creation failed: " + e.getStatusCode() + " " + e.getStatusText());
        } catch (ResourceAccessException e) {
            logger.error("Network/Resource Access error during Shiprocket exchange order creation:", e);
            throw new RuntimeException("Network error during Shiprocket exchange order creation: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during Shiprocket exchange order creation:", e);
            throw new RuntimeException("Failed to create Shiprocket exchange order: " + e.getMessage());
        }
    }
}