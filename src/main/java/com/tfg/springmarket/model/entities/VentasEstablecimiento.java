package com.tfg.springmarket.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ventas_establecimiento")
public class VentasEstablecimiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_coste")
    private Double precioCoste;

    @Column(name = "precio_venta")
    private BigDecimal precioVenta;

    @Column(name = "fecha_venta")
    private String fechaVenta;

    @ManyToOne
    @JoinColumn(name = "productos_establecimiento_id", referencedColumnName = "id")
    private ProductosEstablecimiento productosEstablecimiento;

}