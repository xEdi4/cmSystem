package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, Long> {
}