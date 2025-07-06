package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
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
    private String id;
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
}
