package com.tfg.springmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;

    private String nombre;

    private String contraseña;

    private String email;

    private String estado;

    private String rol;

}
