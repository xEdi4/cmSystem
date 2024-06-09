package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentasEstablecimientoRepository extends JpaRepository<VentasEstablecimiento, Long> {

}
