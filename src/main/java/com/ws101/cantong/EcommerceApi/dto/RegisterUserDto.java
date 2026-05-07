package com.ws101.cantong.EcommerceApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterUserDto {
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters") 
    private String password;
    
    @Pattern(regexp = "USER|ADMIN|SELLER", message = "Role must be USER, ADMIN, or SELLER")
    private String role = "USER";
    
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be 10-15 digits, optional + prefix")
    private String phoneNumber;
}