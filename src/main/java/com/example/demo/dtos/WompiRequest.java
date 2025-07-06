package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WompiRequest {
    @NotBlank(message = "Reference cannot be empty")
    private String reference;
    @NotBlank(message = "Amount cannot be empty")
    private String amount;
    @NotBlank(message = "Currency cannot be empty")
    private String currency;
}
