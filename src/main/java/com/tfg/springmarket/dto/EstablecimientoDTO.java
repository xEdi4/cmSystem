package com.tfg.springmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstablecimientoDTO {

    private Long id;

    private String empresa;

    private String email;

    private String telefono;

}