package com.tfg.springmarket.dto;

import lombok.Data;

@Data
public class CambiarContrasenaRequest {
    private String contrasenaActual;
    private String nuevaContrasena;
}