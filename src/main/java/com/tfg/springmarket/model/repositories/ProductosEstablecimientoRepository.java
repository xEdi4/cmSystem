package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.ProductosEstablecimiento;
import com.tfg.springmarket.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductosEstablecimientoRepository extends JpaRepository<ProductosEstablecimiento, Long> {
    List<ProductosEstablecimiento> findByEstablecimientoIdAndActivoTrue(Long establecimientoId);

    Optional<ProductosEstablecimiento> findByIdAndEstablecimientoIdAndActivoTrue(Long id, Long establecimientoId);

    ProductosEstablecimiento findByEstablecimientoAndNombre(Usuario establecimiento, String nombre);

    Optional<ProductosEstablecimiento> findByIdAndActivoTrue(Long id);
}