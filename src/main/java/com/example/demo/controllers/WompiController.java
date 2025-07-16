package com.example.demo.controllers;

import com.example.demo.dtos.WompiRequest;
import com.example.demo.services.PaymentTransactionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping("/wompi")
public class WompiController {

    @Value("${wompi.integrity.key}")
    private String secret;

    @Value("${wompi.event}")
    private String eventKey;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PaymentTransactionService transactionService;

    public WompiController(PaymentTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/signature")
    public SignatureResponse getSignature(@RequestBody WompiRequest request) {
        try {
            // Concatenación estricta en orden: <Referencia><Monto><Moneda><Secreto>
            String dataToHash = request.getReference()
                    + request.getAmount()
                    + request.getCurrency()
                    + secret;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return new SignatureResponse(hexString.toString());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }


    @PostMapping("/event")
    public ResponseEntity<?> handleEvent(@RequestBody String body) throws IOException {
        JsonNode eventJson = objectMapper.readTree(body);

        String receivedChecksum = eventJson.path("signature").path("checksum").asText();
        JsonNode properties = eventJson.path("signature").path("properties");
        long timestamp = eventJson.path("timestamp").asLong();
        JsonNode dataNode = eventJson.path("data");

        StringBuilder toHash = new StringBuilder();
        for (JsonNode property : properties) {
            String[] path = property.asText().split("\\.");
            JsonNode current = dataNode;
            for (String p : path) {
                current = current.path(p);
            }
            toHash.append(current.asText());
        }
        toHash.append(timestamp).append(eventKey);
        String calculatedChecksum = sha256(toHash.toString());

        if (!calculatedChecksum.equalsIgnoreCase(receivedChecksum)) {
            return ResponseEntity.status(400).body("Invalid checksum");
        }

        if (eventJson.path("event").asText().equals("transaction.updated")) {
            JsonNode transaction = eventJson.path("data").path("transaction");
            transactionService.upsertTransaction(
                    transaction,
                    eventJson.path("environment").asText(),
                    timestamp
            );
        }

        return ResponseEntity.ok().build();
    }

    private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b).toUpperCase();
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    @Setter
    @Getter
    public static class SignatureResponse {
        private String signature;

        public SignatureResponse(String signature) {
            this.signature = signature;
        }

    }
}
