package com.tfg.springmarket.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "productos_proveedor")
public class ProductoProveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precioVenta;
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")

    private Proveedor proveedor;

}