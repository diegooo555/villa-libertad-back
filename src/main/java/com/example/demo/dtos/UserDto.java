package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Phone cannot be empty")
    private String phone;
    @NotBlank(message = "City cannot be empty")
    private String city;
}
