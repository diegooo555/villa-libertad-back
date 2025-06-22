package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WompiRequest {
    private String reference;
    private String amount;
    private String currency;
}
