package com.tfg.springmarket.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "productos_proveedor")
public class ProductosProveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "stock")
    private Integer stock;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;

    @JsonIgnore
    @Column(name = "activo")
    private Boolean activo = true; // Nuevo campo para borrado lógico
}