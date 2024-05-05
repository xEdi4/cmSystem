package com.tfg.springmarket.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
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

    @Column(unique = true)
    private String direccion;

    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;

}
