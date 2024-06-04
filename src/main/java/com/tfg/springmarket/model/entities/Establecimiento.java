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

    @Column(unique = true)
    private String nombre;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telefono;

}
