package com.ws101.cantong.EcommerceApi.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListingDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
}