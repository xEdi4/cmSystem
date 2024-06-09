package com.tfg.springmarket.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ventas_proveedor")
public class VentasProveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "fecha_venta")
    private String fechaVenta;

    @ManyToOne
    @JoinColumn(name = "productos_proveedor_id", referencedColumnName = "id")
    private ProductosProveedor productosProveedor;

}