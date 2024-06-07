package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.ProductoEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoEstablecimientoRepository extends JpaRepository<ProductoEstablecimiento, Long> {
    Optional<ProductoEstablecimiento> findByEstablecimientoIdAndNombre(Long establecimientoId, String nombre);

    ProductoEstablecimiento findByEstablecimientoAndNombre(Establecimiento establecimiento, String nombre);
}