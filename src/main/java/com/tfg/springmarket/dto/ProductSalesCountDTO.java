package com.tfg.springmarket.dto;

import lombok.Data;

@Data
public class ProductSalesCountDTO {
    private Long productId;
    private String productName;
    private long salesCount;

    public ProductSalesCountDTO(Long productId, String productName, long salesCount) {
        this.productId = productId;
        this.productName = productName;
        this.salesCount = salesCount;
    }
}
