package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Your OTP for OY Jewells");

            String htmlContent = buildOtpEmailTemplate(otp);
            helper.setText(htmlContent, true);

            // Try to add logo, but don't fail if it doesn't exist
            addLogoIfExists(helper);

            mailSender.send(message);
            logger.info("OTP email sent successfully to: {}", toEmail);
        } catch (MessagingException e) {
            logger.error("Failed to send OTP email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    @Override
    public void sendOrderConfirmationEmail(String toEmail, String customerName, String orderId,
                                           BigDecimal totalAmount, List<String> productNames, String mobileNumber) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Order Placed Successfully - OY Jewells");

            String htmlContent = buildOrderConfirmationEmailTemplate(customerName, orderId, totalAmount, productNames, mobileNumber);
            helper.setText(htmlContent, true);

            // Try to add logo, but don't fail if it doesn't exist
            addLogoIfExists(helper);

            mailSender.send(message);
            logger.info("Order confirmation email sent successfully to: {} for order: {}", toEmail, orderId);
        } catch (MessagingException e) {
            logger.error("Failed to send order confirmation email to {} for order {}: {}", toEmail, orderId, e.getMessage());
            throw new RuntimeException("Failed to send order confirmation email: " + e.getMessage());
        }
    }

    /**
     * Attempts to add logo as inline attachment if the file exists
     * If file doesn't exist, logs a warning but doesn't throw an exception
     */
    private void addLogoIfExists(MimeMessageHelper helper) {
        try {
            ClassPathResource logoResource = new ClassPathResource("static/images/01.png");
            if (logoResource.exists() && logoResource.isReadable()) {
                helper.addInline("logo", logoResource);
                logger.info("Logo added successfully to email from: {}", logoResource.getURL());
            } else {
                logger.warn("Logo file not found or not readable at static/images/01.png - email will be sent without logo");
                logger.warn("Logo resource exists: {}, Logo resource readable: {}",
                        logoResource.exists(), logoResource.isReadable());
            }
        } catch (Exception e) {
            logger.warn("Could not add logo to email: {} - email will be sent without logo", e.getMessage());
            logger.debug("Full exception: ", e);
        }
    }

    private String buildOtpEmailTemplate(String otp) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }" +
                ".container { max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
                ".header { text-align: center; color: #333; margin-bottom: 30px; }" +
                ".logo { max-width: 150px; height: auto; margin-bottom: 20px; }" +
                ".company-name { font-size: 24px; font-weight: bold; color: #800020; margin-bottom: 10px; display: none; }" +
                ".otp-box { background-color: #f8f9fa; border: 2px dashed #800020; padding: 20px; text-align: center; margin: 20px 0; border-radius: 8px; }" +
                ".otp-code { font-size: 32px; font-weight: bold; color: #800020; letter-spacing: 5px; }" +
                ".info { color: #666; line-height: 1.6; margin: 20px 0; }" +
                ".footer { text-align: center; color: #999; font-size: 12px; margin-top: 30px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<img src='cid:logo' alt='OY Jewells Logo' class='logo' onerror='this.style.display=\"none\"; document.querySelector(\".company-name\").style.display=\"block\";'>" +
                "<div class='company-name'>OY Jewells</div>" +
                "<h1>OTP Verification</h1>" +
                "</div>" +
                "<div class='info'>" +
                "<p>Hello,</p>" +
                "<p>You have requested an OTP for verification. Please use the following code:</p>" +
                "</div>" +
                "<div class='otp-box'>" +
                "<div class='otp-code'>" + otp + "</div>" +
                "</div>" +
                "<div class='info'>" +
                "<p><strong>Important:</strong></p>" +
                "<ul>" +
                "<li>This OTP is valid for <strong>5 minutes</strong> only</li>" +
                "<li>Please do not share this code with anyone</li>" +
                "<li>If you didn't request this OTP, please ignore this email</li>" +
                "</ul>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>© 2025 OY Jewells. All rights reserved.</p>" +
                "<p>This is an automated email, please do not reply.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    private String buildOrderConfirmationEmailTemplate(String customerName, String orderId,
                                                       BigDecimal totalAmount, List<String> productNames, String mobileNumber) {

        // Build the tracking URL using mobile number
        String trackingUrl = "https://www.shiprocket.in/shipment-tracking/" + mobileNumber;

        // Build product details list in the requested format
        StringBuilder productDetailsList = new StringBuilder();
        for (int i = 0; i < productNames.size(); i++) {
            productDetailsList.append("<div class='product-item'>")
                    .append("<div class='product-row'>")
                    .append("<span class='product-label'>Product Title:</span>")
                    .append("<span class='product-value'>").append(productNames.get(i)).append("</span>")
                    .append("</div>")
                    .append("<div class='product-row'>")
                    .append("<span class='product-label'>Product Qty: </span>")
                    .append("<span class='product-value'>1</span>") // You can pass actual quantity as parameter
                    .append("</div>")
                    .append("</div>");
        }

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Order Confirmation - OY Jewells</title>" +
                "<style>" +
                "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 20px; background-color: #f5f5f5; color: #333; }" +
                ".container { max-width: 600px; margin: 0 auto; background-color: white; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.1); }" +
                ".header { text-align: center; padding: 40px 30px 30px 30px; background-color: white; }" +
                ".logo { max-width: 120px; height: auto; margin-bottom: 25px; }" +
                ".company-name { font-size: 28px; font-weight: bold; color: #800020; margin-bottom: 20px; display: none; }" +
                ".title { font-size: 28px; font-weight: 600; color: #800020; margin: 15px 0 10px 0; }" +
                ".subtitle { font-size: 16px; color: #800020; margin: 5px 0 20px 0; opacity: 0.8; }" +
                ".diamond { color: #800020; }" +
                ".content { padding: 0 30px; }" +
                ".greeting { color: #666; font-size: 16px; margin: 20px 0; line-height: 1.6; }" +
                ".customer-greeting { color: #666; font-size: 16px; margin: 15px 0; }" +
                ".customer-name { color: #800020; font-weight: 600; }" +
                ".sparkle { color: #DAA520; }" +
                ".order-section { background-color: #fff; border-left: 4px solid #800020; padding: 25px; margin: 25px 0; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }" +
                ".order-title { font-size: 20px; font-weight: 600; color: #800020; margin-bottom: 20px; text-decoration: underline; }" +
                ".product-list { margin: 15px 0; }" +
                ".product-item { background-color: #f8f9fa; padding: 15px; margin-bottom: 15px; border-radius: 8px; border-left: 3px solid #800020; }" +
                ".product-row { display: flex; justify-content: flex-start; align-items: center; margin-bottom: 8px; }" +
                ".product-row:last-child { margin-bottom: 0; }" +
                ".product-label { font-weight: 600; color: #800020; min-width: 120px; font-size: 15px; }" +
                ".product-value { color: #333; font-size: 15px; margin-left: 10px; }" +
                ".total-section { background: linear-gradient(135deg, #800020 0%, #600018 100%); color: white; padding: 20px; border-radius: 8px; margin: 20px 0; text-align: center; }" +
                ".total-amount { font-size: 22px; font-weight: 700; }" +
                ".tracking-section { background: linear-gradient(135deg, #f8f2f2 0%, #f0e6e6 100%); padding: 25px; border-radius: 10px; margin: 25px 0; text-align: center; border: 1px solid #800020; }" +
                ".tracking-text { font-size: 16px; color: #800020; margin-bottom: 18px; font-weight: 500; }" +
                ".mobile-number { font-weight: 700; color: #800020; }" +
                ".tracking-link { display: inline-block; background: linear-gradient(135deg, #800020 0%, #600018 100%); color: white; padding: 14px 28px; text-decoration: none; border-radius: 25px; font-weight: 600; font-size: 15px; transition: all 0.3s ease; box-shadow: 0 3px 10px rgba(128, 0, 32, 0.3); }" +
                ".tracking-link:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(128, 0, 32, 0.4); }" +
                ".divider { text-align: center; margin: 30px 0; color: #800020; font-size: 24px; }" +
                ".closing { text-align: center; margin: 30px 0; padding: 0 30px; }" +
                ".happy-shopping { font-size: 20px; font-weight: 600; margin: 20px 0; color: #800020; }" +
                ".shopping-bag { color: #DAA520; }" +
                ".party-emoji { color: #DAA520; }" +
                ".contact-info { font-size: 15px; color: #666; margin: 15px 0; line-height: 1.5; }" +
                ".footer { text-align: center; background-color: #f8f9fa; padding: 25px 30px; color: #6c757d; font-size: 12px; line-height: 1.4; }" +
                ".footer p { margin: 5px 0; }" +
                "@media (max-width: 600px) {" +
                ".container { margin: 10px; }" +
                ".header, .content, .closing { padding: 20px; }" +
                ".title { font-size: 24px; }" +
                ".product-row { flex-direction: column; align-items: flex-start; }" +
                ".product-label { min-width: auto; margin-bottom: 5px; }" +
                ".product-value { margin-left: 0; }" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<img src='cid:logo' alt='OY Jewells Logo' class='logo' onerror='this.style.display=\"none\"; document.querySelector(\".company-name\").style.display=\"block\";'>" +
                "<div class='company-name'>OY Jewells</div>" +
                "<div class='title'>Order Placed Successfully!</div>" +
                "<div class='subtitle'>Thank you for purchasing from OY Jewells! <span class='diamond'>&#128142;</span></div>" +
                "</div>" +

                "<div class='content'>" +
                "<div class='greeting'>" +
                "We're thrilled to have you as part of the OY Jewells family. Your support means the world to us! <span class='sparkle'>&#10024;</span>" +
                "</div>" +

                "<div class='customer-greeting'>" +
                "Hi <span class='customer-name'>" + customerName + "</span>," +
                "</div>" +

                "<div class='order-section'>" +
                "<div class='order-title'>Order Details:</div>" +
                "<div class='product-list'>" +
                productDetailsList.toString() +
                "</div>" +
                "<div class='total-section'>" +
                // FIXED LINE: Using HTML entity instead of Unicode symbol
                "<div class='total-amount'>Total Amount: &#8377;" + totalAmount.toPlainString() + "</div>" +
                "</div>" +
                "</div>" +

                "<div class='tracking-section'>" +
                "<div class='tracking-text'>Track your order using mobile number: <span class='mobile-number'>" + mobileNumber + "</span></div>" +
                "<a href='" + trackingUrl + "' class='tracking-link'>Click here to track your order</a>" +
                "</div>" +
                "</div>" +

                "<div class='divider'>&#8226; &#8226; &#8226;</div>" +

                "<div class='closing'>" +
                "<div class='happy-shopping'><span class='shopping-bag'>&#128717;</span> Happy Shopping! <span class='party-emoji'>&#129395;&#129395;</span></div>" +
                "<div class='contact-info'>If you have any questions, feel free to reach out anytime.</div>" +
                "</div>" +

                "<div class='footer'>" +
                "<p>&copy; 2025 OY Jewells. All rights reserved.</p>" +
                "<p>This is an automated email, please do not reply.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}