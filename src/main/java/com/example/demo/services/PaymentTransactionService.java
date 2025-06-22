package com.example.demo.services;
import com.example.demo.entities.PaymentTransaction;
import com.example.demo.repositories.PaymentTransactionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository repository;

    public void upsertTransaction(JsonNode transactionData, String environment, long timestamp, String rawEvent) {
        String id = transactionData.path("id").asText();

        PaymentTransaction tx = repository.findById(id).orElse(PaymentTransaction.builder()
                .id(id)
                .build());

        tx.setAmountInCents(transactionData.path("amount_in_cents").asLong());
        tx.setReference(transactionData.path("reference").asText());
        tx.setCustomerEmail(transactionData.path("customer_email").asText());
        tx.setCurrency(transactionData.path("currency").asText());
        tx.setPaymentMethodType(transactionData.path("payment_method_type").asText());
        tx.setRedirectUrl(transactionData.path("redirect_url").asText());
        tx.setStatus(transactionData.path("status").asText());
        tx.setEnvironment(environment);
        tx.setPaymentLinkId(transactionData.path("payment_link_id").asText(null));
        tx.setPaymentSourceId(transactionData.path("payment_source_id").asText(null));
        tx.setTimestamp(timestamp);
        tx.setRawEvent(rawEvent);

        repository.save(tx);
    }
}
