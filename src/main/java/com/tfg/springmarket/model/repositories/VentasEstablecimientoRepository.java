package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VentasEstablecimientoRepository extends JpaRepository<VentasEstablecimiento, Long> {
    List<VentasEstablecimiento> findByEstablecimientoIdAndFechaVentaBetween(Long establecimientoId, LocalDate fechaInicio, LocalDate fechaFin);
    List<VentasEstablecimiento> findAllByEstablecimientoIdAndFechaVentaBetween(Long establecimientoId, LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT v.productosEstablecimiento.id, SUM(v.cantidad) as totalCantidad " +
            "FROM VentasEstablecimiento v " +
            "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY v.productosEstablecimiento.id " +
            "ORDER BY totalCantidad DESC")
    List<Object[]> findTop3ProductosByMesAndAno(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT SUM(v.precioVenta * v.cantidad), SUM(v.precioCoste * v.cantidad), SUM((v.precioVenta - v.precioCoste) * v.cantidad) " +
            "FROM VentasEstablecimiento v " +
            "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin")
    Object[] findIngresosGastosBeneficiosByMesAndAno(LocalDate fechaInicio, LocalDate fechaFin);
}