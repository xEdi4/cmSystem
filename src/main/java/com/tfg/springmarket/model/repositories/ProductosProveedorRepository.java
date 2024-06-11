package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.ProductosProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductosProveedorRepository extends JpaRepository<ProductosProveedor, Long> {
    List<ProductosProveedor> findByProveedorIdAndActivoTrue(Long proveedorId);

    Optional<ProductosProveedor> findByProveedorIdAndIdAndActivoTrue(Long proveedorId, Long id);

    List<ProductosProveedor> findAllByActivoTrue();

    Optional<ProductosProveedor> findByIdAndActivoTrue(Long id); // Nuevo método
}