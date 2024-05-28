package com.tfg.springmarket.dto;

import com.tfg.springmarket.dto.ProductoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductosDTO {
    private List<ProductoDTO> productos;
}
