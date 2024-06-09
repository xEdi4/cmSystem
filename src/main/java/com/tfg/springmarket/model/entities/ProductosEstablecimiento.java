package com.tfg.springmarket.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

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
    private Establecimiento establecimiento;

}