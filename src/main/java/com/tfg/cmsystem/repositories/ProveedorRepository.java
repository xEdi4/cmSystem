package com.tfg.cmsystem.repositories;

import com.tfg.cmsystem.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
//holaa
}
