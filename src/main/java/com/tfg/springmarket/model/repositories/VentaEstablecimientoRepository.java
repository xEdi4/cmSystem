package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.VentaEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaEstablecimientoRepository extends JpaRepository<VentaEstablecimiento, Long> {
    List<VentaEstablecimiento> findByFechaVentaBetweenAndProductoEstablecimiento_Establecimiento(
            String fechaInicio, String fechaFin, Establecimiento establecimiento);
}
