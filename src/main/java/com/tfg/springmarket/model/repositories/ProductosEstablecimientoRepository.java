package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.ProductosEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosEstablecimientoRepository extends JpaRepository<ProductosEstablecimiento, Long> {

    ProductosEstablecimiento findByEstablecimientoAndNombre(Establecimiento establecimiento, String nombre);

}