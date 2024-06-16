package com.tfg.springmarket.dto;

import com.tfg.springmarket.model.entities.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String telefono;
    private String correo;
    private Rol rol;
}