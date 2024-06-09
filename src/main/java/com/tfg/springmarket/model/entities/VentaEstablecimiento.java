package com.tfg.springmarket.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ventas_establecimiento")
public class VentaEstablecimiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_establecimiento_id")
    private ProductoEstablecimiento productoEstablecimiento;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "precio_coste")
    private Double precioCoste;

    @Column(name = "fecha_venta")
    private String fechaVenta;


    // Getters y setters

    // Constructor(s), hashCode, equals, etc. según sea necesario
}