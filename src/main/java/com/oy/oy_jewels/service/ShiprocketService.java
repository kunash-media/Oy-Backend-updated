package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.response.ShiprocketOrderResponse;
import com.oy.oy_jewels.entity.OrderEntity;


public interface ShiprocketService {

    /**
     * Authenticates with Shiprocket and returns a token.
     * @return Auth token
     */
    String getAuthToken();

    /**
     * Creates an order in Shiprocket.
     * @param order The order entity to be synced
     * @return Shiprocket order response
     */
    ShiprocketOrderResponse createShiprocketOrder(OrderEntity order);

    /**
     * Fetches tracking details for an AWB code.
     * @param awbCode The AWB number
     * @return Tracking details as JSON/String
     */
    String getTrackingDetails(String awbCode);

    /**
     * Cancels an order in Shiprocket.
     * @param orderId The Shiprocket order ID
     * @return true if cancellation was successful
     */
    boolean cancelShiprocketOrder(String orderId);

    /**
     * Checks available courier services for a pincode.
     * @param pickupPincode    Origin pincode
     * @param deliveryPincode  Destination pincode
     * @param weight          Package weight (kg)
     * @return Courier serviceability details as JSON/String
     */
    String getAvailableCouriers(String pickupPincode, String deliveryPincode, double weight);
}