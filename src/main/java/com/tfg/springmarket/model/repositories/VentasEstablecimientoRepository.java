package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.dto.ProductSalesCountDTO;
import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentasEstablecimientoRepository extends JpaRepository<VentasEstablecimiento, Long> {
    List<VentasEstablecimiento> findByEstablecimientoIdAndFechaVentaBetween(Long establecimientoId, LocalDate fechaInicio, LocalDate fechaFin);
    List<VentasEstablecimiento> findAllByEstablecimientoIdAndFechaVentaBetween(Long establecimientoId, LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT new com.tfg.springmarket.dto.ProductSalesCountDTO(ve.productosEstablecimiento.id, ve.productosEstablecimiento.nombre, COUNT(ve.id)) " +
            "FROM VentasEstablecimiento ve " +
            "WHERE ve.fechaVenta BETWEEN :startDate AND :endDate " +
            "GROUP BY ve.productosEstablecimiento.id, ve.productosEstablecimiento.nombre " +
            "ORDER BY COUNT(ve.id) DESC")
    List<ProductSalesCountDTO> findTopProductsBySalesCountBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);

    @Query("SELECT SUM(v.precioVenta * v.cantidad), SUM(v.precioCoste * v.cantidad), SUM((v.precioVenta - v.precioCoste) * v.cantidad) " +
            "FROM VentasEstablecimiento v " +
            "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin")
    Object[] findIngresosGastosBeneficiosByMesAndAno(LocalDate fechaInicio, LocalDate fechaFin);
}