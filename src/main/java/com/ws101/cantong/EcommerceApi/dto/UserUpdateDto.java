package com.ws101.cantong.EcommerceApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserUpdateDto {
    
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be 10-15 digits, optional + prefix")
    private String phoneNumber;
    
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;
}