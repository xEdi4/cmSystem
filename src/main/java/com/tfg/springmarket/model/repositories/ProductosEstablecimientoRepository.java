package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.ProductosEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosEstablecimientoRepository extends JpaRepository<ProductosEstablecimiento, Long> {
    List<ProductosEstablecimiento> findByEstablecimientoId(Long establecimientoId);

    ProductosEstablecimiento findByEstablecimientoIdAndId(Long establecimientoId, Long id);

    ProductosEstablecimiento findByEstablecimientoAndNombre(Establecimiento establecimiento, String nombre);
}