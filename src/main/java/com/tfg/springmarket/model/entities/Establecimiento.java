package com.tfg.springmarket.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "establecimientos")
public class Establecimiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "teléfono")
    private String teléfono;

    @Column(name = "email")
    private String email;

}
