package com.tfg.springmarket.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String empresa;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String teléfono;
}
