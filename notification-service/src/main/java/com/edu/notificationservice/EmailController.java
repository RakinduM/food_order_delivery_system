package com.edu.notificationservice;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins="*")
public class EmailController {

    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/send")
    public String sendTestEmail(@RequestParam String toEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("hirunisliit@gmail.com"); // Replace with your verified sender email
            helper.setTo(toEmail);
            helper.setSubject("Test Email - Snap Eats");
            helper.setText("<h3>This is a test email from Snap Eats Notification Service.</h3>", true);

            mailSender.send(message);
            return "Test email sent successfully to: " + toEmail;
        } catch (Exception e) {
            return "Failed to send test email: " + e.getMessage();
        }
    }

    @GetMapping("/send-otp")
    public String sendOtpEmail(@RequestParam String toEmail) {
        try {
            // Generate 6-digit OTP
            int otp = (int) (Math.random() * 900000) + 100000;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("hirunisliit@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Your Snap Eats OTP Code");

            // Load HTML template and replace [OTP]
            try (var inputStream = Objects.requireNonNull(
                    EmailController.class.getResourceAsStream("/templates/registerVerifyMail.html"))) {
                String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                html = html.replace("[OTP]", String.valueOf(otp));
                helper.setText(html, true);
            }

            mailSender.send(message);
            return "OTP sent successfully to: " + toEmail + " (OTP: " + otp + ")";
        } catch (Exception e) {
            return "Failed to send OTP: " + e.getMessage();
        }
    }

    // Restaurant Registration Success Email
    @GetMapping("/send-restaurant-registration")
    public String sendRestaurantRegistrationEmail(@RequestParam String toEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("hirunisliit@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Restaurant Registration Success - Snap Eats");

            // Load HTML template for restaurant registration success email
            try (var inputStream = Objects.requireNonNull(
                    EmailController.class.getResourceAsStream("/templates/restaurantSuccessMail.html"))) {
                String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                helper.setText(html, true);
            }

            mailSender.send(message);
            return "Restaurant registration email sent successfully to: " + toEmail;
        } catch (Exception e) {
            return "Failed to send restaurant registration email: " + e.getMessage();
        }
    }

    // Driver Registration Success Email
    @GetMapping("/send-driver-registration")
    public String sendDriverRegistrationEmail(@RequestParam String toEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("hirunisliit@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Driver Registration Success - Snap Eats");

            // Load HTML template for driver registration success email
            try (var inputStream = Objects.requireNonNull(
                    EmailController.class.getResourceAsStream("/templates/driverRegisteredMail.html"))) {
                String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                helper.setText(html, true);
            }

            mailSender.send(message);
            return "Driver registration email sent successfully to: " + toEmail;
        } catch (Exception e) {
            return "Failed to send driver registration email: " + e.getMessage();
        }
    }
}
