package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.VentaEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<VentaEstablecimiento, Long> {
    // Métodos adicionales si es necesario
}
