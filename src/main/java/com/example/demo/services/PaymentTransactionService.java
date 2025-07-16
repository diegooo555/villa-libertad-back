package com.example.demo.services;
import com.example.demo.entities.PaymentTransaction;
import com.example.demo.repositories.PaymentTransactionRepository;
import com.example.demo.repositories.ReservationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentTransactionService {

    private final PaymentTransactionRepository repository;
    private final ReservationRepository reservationRepository;

    public  PaymentTransactionService(PaymentTransactionRepository repository, ReservationRepository reservationRepository) {
        this.repository = repository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public void upsertTransaction(JsonNode transactionData, String environment, long timestamp) {
        String id = transactionData.path("id").asText();

        PaymentTransaction transaction = repository.findById(id).orElse(PaymentTransaction.builder()
                .id(id)
                .build());

        String reference = transactionData.path("reference").asText();
        transaction.setAmountInCents(transactionData.path("amount_in_cents").asLong());
        transaction.setReference(reference);
        transaction.setCustomerEmail(transactionData.path("customer_email").asText());
        transaction.setCurrency(transactionData.path("currency").asText());
        transaction.setPaymentMethodType(transactionData.path("payment_method_type").asText());
        transaction.setRedirectUrl(transactionData.path("redirect_url").asText());
        transaction.setStatus(transactionData.path("status").asText());
        transaction.setEnvironment(environment);
        transaction.setPaymentLinkId(transactionData.path("payment_link_id").asText(null));
        transaction.setPaymentSourceId(transactionData.path("payment_source_id").asText(null));
        transaction.setTimestamp(timestamp);

        repository.save(transaction);
        reservationRepository.updateStatusByReference(reference, "CONFIRMED");
    }
}
