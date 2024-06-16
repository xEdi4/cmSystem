package com.tfg.springmarket.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "productos_establecimiento")
public class ProductosEstablecimiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio_coste")
    private Double precioCoste;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "stock")
    private Integer stock;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", referencedColumnName = "id")
    private Usuario establecimiento;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Usuario proveedor;

    @JsonIgnore
    @Column(name = "activo")
    private Boolean activo = true; // Nuevo campo para borrado lógico
}