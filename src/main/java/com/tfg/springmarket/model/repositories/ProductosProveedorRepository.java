package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.ProductosProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosProveedorRepository extends JpaRepository<ProductosProveedor, Long> {

}