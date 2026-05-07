package com.ws101.cantong.EcommerceApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddressDto {
    
    @NotBlank(message = "Street address is required")
    @Size(max = 255, message = "Street address cannot exceed 255 characters")
    private String street;
    
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;
    
    @NotBlank(message = "State/Province is required")
    @Size(max = 100, message = "State/Province cannot exceed 100 characters")
    private String state;
    
    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "^[0-9]{4,10}$", message = "Postal code must be 4-10 digits")
    private String postalCode;
    
    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters")
    private String country;
    
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be 10-15 digits, optional + prefix")
    private String phoneNumber;
}