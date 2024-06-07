package com.tfg.springmarket.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    @JsonIgnore // Evita que la lista de productos se incluya en la respuesta JSON del proveedor
    // Evita la serialización de productos cuando se serializa el establecimiento
    private List<ProductoProveedor> productos;

}
