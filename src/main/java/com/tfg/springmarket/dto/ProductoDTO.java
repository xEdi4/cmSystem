package com.tfg.springmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private String nombre;

    private String descripcion;

    private Float precio;

    private Integer stock;

    private Long proveedorId;

    private Long establecimientoId;
}