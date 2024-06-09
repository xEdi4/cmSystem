package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.model.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public Proveedor agregarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> obtenerTodosLosProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor obtenerProveedorPorId(Long id) {
        Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
        return proveedorOptional.orElse(null);
    }

    public Proveedor actualizarProveedor(Long id, Proveedor proveedor) {
        // Verificar si el proveedor existe
        Proveedor proveedorExistente = obtenerProveedorPorId(id);
        if (proveedorExistente != null) {
            proveedor.setId(id); // Asegurarse de establecer el ID para la actualización
            return proveedorRepository.save(proveedor);
        } else {
            return null; // Manejar el caso donde el proveedor no existe
        }
    }

}
