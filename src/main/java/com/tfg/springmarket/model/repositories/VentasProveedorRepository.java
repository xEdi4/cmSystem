package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.VentasProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VentasProveedorRepository extends JpaRepository<VentasProveedor, Long> {
    List<VentasProveedor> findAllByEstablecimientoIdAndFechaVentaBetween(Long establecimientoId, LocalDate fechaInicio, LocalDate fechaFin);
    List<VentasProveedor> findAllByProveedorIdAndFechaVentaBetween(Long proveedorId, LocalDate fechaInicio, LocalDate fechaFin);
    List<VentasProveedor> findByProveedorIdAndFechaVentaBetween(Long proveedorId, LocalDate fechaInicio, LocalDate fechaFin);
    List<VentasProveedor> findByEstablecimientoIdAndFechaVentaBetween(Long establecimientoId, LocalDate fechaInicio, LocalDate fechaFin);
    @Query("SELECT v.productosProveedor.id, SUM(v.cantidad) as totalCantidad " +
            "FROM VentasProveedor v " +
            "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY v.productosProveedor.id " +
            "ORDER BY totalCantidad DESC")
    List<Object[]> findTop3ProductosByMesAndAno(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT SUM(v.precioVenta * v.cantidad) " +
            "FROM VentasProveedor v " +
            "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin")
    Double findIngresosByMesAndAno(LocalDate fechaInicio, LocalDate fechaFin);
}