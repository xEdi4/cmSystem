package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByProveedorId(Long proveedorId);
    List<Producto> findByEstablecimientoId(Long establecimientoId);
}
