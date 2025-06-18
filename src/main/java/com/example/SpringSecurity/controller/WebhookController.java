package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.request.WearableWebhookRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@RestController
@RequestMapping("/api/public/wearable")
public class WebhookController {

    private static final String SECRET_KEY = "supersecret"; // dùng giống thiết bị

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @Valid @RequestBody WearableWebhookRequest request,
            @RequestHeader("X-Signature") String signatureHeader) {

        String computedSignature = hmacSha256(request.getUserId(), SECRET_KEY);

        if (!computedSignature.equals(signatureHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature");
        }

        // ✅ Parse JSON nếu chữ ký đúng
        System.out.println("Webhook received: " + request);
        return ResponseEntity.ok("Webhook received and verified");
    }

    @PostMapping("/debug-signature")
    public ResponseEntity<String> debugSignature(
            @RequestParam String userId) {

        String signature = hmacSha256(userId, SECRET_KEY);
        return ResponseEntity.ok("Computed Signature: " + signature);
    }

    private String hmacSha256(String data, String secret) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(data.getBytes());

            // Chuyển thành hex
            StringBuilder sb = new StringBuilder();
            for (byte b : hmacBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC", e);
        }
    }
}
