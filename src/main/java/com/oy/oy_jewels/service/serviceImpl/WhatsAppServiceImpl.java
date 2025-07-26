package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.service.WhatsAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WhatsAppServiceImpl implements WhatsAppService {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppServiceImpl.class);

    @Value("${aisensy.api.key}")
    private String apiKey;

    @Value("${aisensy.api.url}")
    private String apiUrl;

    @Value("${aisensy.template.order_confirmation}")
    private String orderConfirmationTemplate;

    @Value("${aisensy.template.order_cancelled}")
    private String orderCancelledTemplate;

    @Value("${aisensy.template.return_initiated}")
    private String returnInitiatedTemplate;

    @Value("${aisensy.template.exchange_initiated}")
    private String exchangeInitiatedTemplate;

    @Value("${aisensy.template.birthday_wishes_promotion}")
    private String birthdayWishesTemplate;

    // Static URLs
    private static final String TRACKING_URL = "https://www.shiprocket.in/shipment-tracking/";
    private static final String SUPPORT_URL = "https://jewelry-frontend-six.vercel.app/index.html";

    // Origin for CORS (optional, only if needed)
    @Value("${aisensy.origin:http://localhost:8080}")
    private String origin;

    private final RestTemplate restTemplate;

    public WhatsAppServiceImpl( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendOrderConfirmationMessage(String userPhone, String userName, String orderId, String orderAmount, List<String> productNames, String templateName) {
        // Validate inputs
        if (userPhone == null || userPhone.isBlank()) {
            logger.error("Invalid phone number for order {}", orderId);
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (userName == null || userName.isBlank()) {
            logger.error("Invalid user name for order {}", orderId);
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (orderId == null || orderId.isBlank()) {
            logger.error("Invalid order ID");
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        if (orderAmount == null || orderAmount.isBlank()) {
            logger.error("Invalid order amount for order {}", orderId);
            throw new IllegalArgumentException("Order amount cannot be null or empty");
        }
        if (productNames == null) {
            logger.warn("Product names list is null for order {}, setting to empty list", orderId);
            productNames = List.of();
        }

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "OY-Jewels-Order-System/1.0");
        headers.set("X-Requested-With", "XMLHttpRequest");

        // Format phone number and product names
        String cleanPhoneNumber = userPhone.startsWith("+") ? userPhone.substring(1) : userPhone;
        String formattedProductNames = productNames.isEmpty() ? "N/A" : String.join(", ", productNames);
        String url = templateName.equals("order_cancelled") ? SUPPORT_URL : TRACKING_URL;

        // Build payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("apiKey", apiKey);
        payload.put("campaignName", resolveCampaignName(templateName));
        payload.put("destination", cleanPhoneNumber);
        payload.put("userName", userName);
        payload.put("templateParams", Arrays.asList(
                userName,              // {{1}} - Customer name
                formattedProductNames, // {{2}} - Products
                orderAmount,           // {{3}} - Total Amount
                cleanPhoneNumber,      // {{4}} - Phone number for tracking
                url                    // {{5}} - Tracking/Support URL
        ));
        payload.put("source", "oy-jewels-order-system");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            logger.info("Sending WhatsApp message for order {} to phone {} with campaign {}",
                    orderId, cleanPhoneNumber, resolveCampaignName(templateName));
            logger.debug("Payload: {}", payload);
            logger.debug("Request headers: {}", headers);
            logger.debug("API URL: {}", apiUrl);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("✅ Successfully sent WhatsApp message for order {}. Response: {}", orderId, response.getBody());
            } else {
                logger.warn("⚠️ Unexpected response for order {}: Status={}, Response={}",
                        orderId, response.getStatusCode(), response.getBody());
            }

        } catch (HttpClientErrorException e) {
            logger.error("❌ HTTP Error for order {}. Status: {}, Response: {}",
                    orderId, e.getStatusCode(), e.getResponseBodyAsString());
            logger.debug("Failed payload: {}", payload);
            logger.debug("Request headers: {}", headers);
            logger.debug("API URL: {}", apiUrl);
            logger.debug("Phone number: original='{}', cleaned='{}'", userPhone, cleanPhoneNumber);
            logger.debug("Template params: {}", payload.get("templateParams"));

            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                logger.error("Authentication failure. Please verify the API key in application.properties.");
                throw new IllegalStateException("Invalid or expired API key. Check aisensy.api.key in configuration.");
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                logger.error("Bad request. Verify template parameters and campaign name in AiSensy dashboard.");
                throw new IllegalArgumentException("Invalid request payload or template configuration.");
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("API endpoint not found. Verify the aisensy.api.url in application.properties.");
                throw new IllegalStateException("API endpoint not found. Check aisensy.api.url in configuration or contact AiSensy support.");
            }
            throw e;

        } catch (Exception e) {
            logger.error("❌ Unexpected error for order {}. Error: {}", orderId, e.getMessage(), e);
            throw new RuntimeException("Failed to send WhatsApp message for order " + orderId, e);
        }
    }

    @Override
    public void sendBirthdayCouponMessage(String userPhone, String userName, String couponDiscount, List<String> categories, String couponCode, String validUntilDate) {
        // Validate inputs
        if (userPhone == null || userPhone.isBlank()) {
            logger.error("Invalid phone number for birthday coupon");
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (userName == null || userName.isBlank()) {
            logger.error("Invalid user name for birthday coupon");
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (couponDiscount == null || couponDiscount.isBlank()) {
            logger.error("Invalid coupon discount");
            throw new IllegalArgumentException("Coupon discount cannot be null or empty");
        }
        if (categories == null || categories.isEmpty()) {
            logger.error("Invalid categories list for birthday coupon");
            throw new IllegalArgumentException("Categories list cannot be null or empty");
        }
        if (couponCode == null || couponCode.isBlank()) {
            logger.error("Invalid coupon code");
            throw new IllegalArgumentException("Coupon code cannot be null or empty");
        }
        if (validUntilDate == null || validUntilDate.isBlank()) {
            logger.error("Invalid valid until date");
            throw new IllegalArgumentException("Valid until date cannot be null or empty");
        }

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "OY-Jewels-Order-System/1.0");
        headers.set("X-Requested-With", "XMLHttpRequest");

        // Format phone number and categories
        String cleanPhoneNumber = userPhone.startsWith("+") ? userPhone.substring(1) : userPhone;
        String formattedCategories = String.join(" and ", categories);

        // Build payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("apiKey", apiKey);
        payload.put("campaignName", birthdayWishesTemplate);
        payload.put("destination", cleanPhoneNumber);
        payload.put("userName", userName);
        payload.put("templateParams", Arrays.asList(
                couponDiscount,      // {{1}} - Discount percentage
                formattedCategories, // {{2}} - Categories
                couponCode,         // {{3}} - Coupon code
                validUntilDate      // {{4}} - Valid until date
        ));
        payload.put("source", "oy-jewels-birthday-campaign");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            logger.info("Sending birthday coupon message to phone {} with campaign {}", cleanPhoneNumber, birthdayWishesTemplate);
            logger.debug("Payload: {}", payload);
            logger.debug("Request headers: {}", headers);
            logger.debug("API URL: {}", apiUrl);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("✅ Successfully sent birthday coupon message to {}. Response: {}", cleanPhoneNumber, response.getBody());
            } else {
                logger.warn("⚠️ Unexpected response for birthday coupon to {}: Status={}, Response={}",
                        cleanPhoneNumber, response.getStatusCode(), response.getBody());
            }

        } catch (HttpClientErrorException e) {
            logger.error("❌ HTTP Error for birthday coupon to {}. Status: {}, Response: {}",
                    cleanPhoneNumber, e.getStatusCode(), e.getResponseBodyAsString());
            logger.debug("Failed payload: {}", payload);
            logger.debug("Request headers: {}", headers);
            logger.debug("API URL: {}", apiUrl);
            logger.debug("Phone number: original='{}', cleaned='{}'", userPhone, cleanPhoneNumber);
            logger.debug("Template params: {}", payload.get("templateParams"));

            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                logger.error("Authentication failure. Please verify the API key in application.properties.");
                throw new IllegalStateException("Invalid or expired API key. Check aisensy.api.key in configuration.");
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                logger.error("Bad request. Verify template parameters and campaign name in AiSensy dashboard.");
                throw new IllegalArgumentException("Invalid request payload or template configuration.");
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("API endpoint not found. Verify the aisensy.api.url in application.properties.");
                throw new IllegalStateException("API endpoint not found. Check aisensy.api.url in configuration or contact AiSensy support.");
            }
            throw e;

        } catch (Exception e) {
            logger.error("❌ Unexpected error for birthday coupon to {}. Error: {}", cleanPhoneNumber, e.getMessage(), e);
            throw new RuntimeException("Failed to send birthday coupon message to " + cleanPhoneNumber, e);
        }
    }

    private String resolveCampaignName(String templateName) {
        switch (templateName) {
            case "order_confirmation":
                return orderConfirmationTemplate;
            case "order_cancelled":
                return orderCancelledTemplate;
            case "return_initiated":
                return returnInitiatedTemplate;
            case "exchange_initiated":
                return exchangeInitiatedTemplate;
            case "birthday_wishes_promotion":
                return birthdayWishesTemplate;
            default:
                logger.warn("Unknown template name: {}, using default Order Confirmation", templateName);
                return orderConfirmationTemplate;
        }
    }

    public void testWithRealData() {
        // Test with a single, verified configuration
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "OY-Jewels-Order-System/1.0");
        headers.set("X-Requested-With", "XMLHttpRequest");

        Map<String, Object> payload = new HashMap<>();
        payload.put("apiKey", apiKey);
        payload.put("campaignName", orderConfirmationTemplate);
        payload.put("destination", "918317289305");
        payload.put("userName", "Pranjal Prasad");
        payload.put("templateParams", Arrays.asList(
                "Pranjal Prasad",
                "Gold Ring",
                "799.99",
                "918317289305",
                TRACKING_URL
        ));
        payload.put("source", "oy-jewels-order-system");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            logger.info("🧪 Testing WhatsApp API - {}", apiUrl);
            logger.debug("📤 Payload: {}", payload);
            logger.debug("📤 Headers: {}", headers);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            logger.info("✅ SUCCESS with API test! Status: {}, Response: {}",
                    response.getStatusCode(), response.getBody());

        } catch (HttpClientErrorException e) {
            logger.error("❌ Test failed: Status: {}, Response: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            logger.debug("Failed payload: {}", payload);
            logger.debug("Request headers: {}", headers);
            logger.debug("API URL: {}", apiUrl);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                logger.error("Authentication failure. Please verify the API key in application.properties.");
                throw new IllegalStateException("Invalid or expired API key. Check aisensy.api.key in configuration.");
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                logger.error("Bad request. Verify template parameters and campaign name in AiSensy dashboard.");
                throw new IllegalArgumentException("Invalid request payload or template configuration.");
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("API endpoint not found. Verify the aisensy.api.url in application.properties.");
                throw new IllegalStateException("API endpoint not found. Check aisensy.api.url in configuration or contact AiSensy support.");
            }
            throw e;
        } catch (Exception e) {
            logger.error("❌ Test failed with unexpected error: {}", e.getMessage());
            throw new RuntimeException("Test failed", e);
        }
    }
}