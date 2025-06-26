package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

    @Id
    private String id; // Este es el ID de la transacción de Wompi

    private Long amountInCents;
    private String reference;
    private String customerEmail;
    private String currency;
    private String paymentMethodType;
    private String redirectUrl;
    private String status;
    private String environment;
    private String paymentLinkId;
    private String paymentSourceId;
    private Long timestamp;

    @Lob
    private String rawEvent;
}
