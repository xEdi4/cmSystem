package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.model.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public Proveedor agregarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    // Otros métodos para obtener, actualizar o eliminar proveedores si es necesario
}
