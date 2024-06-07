package com.tfg.springmarket.services;

import com.tfg.springmarket.exceptions.ProveedorNotFoundException;
import com.tfg.springmarket.model.entities.ProductoProveedor;
import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.model.repositories.ProductoProveedorRepository;
import com.tfg.springmarket.model.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProductoProveedorRepository productoProveedorRepository;

    public ProductoProveedor agregarProductoProveedor(Long proveedorId, ProductoProveedor productoProveedor) {
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado con id: " + proveedorId));

        productoProveedor.setProveedor(proveedor);
        return productoProveedorRepository.save(productoProveedor);
    }

    // Otros métodos para obtener, actualizar o eliminar productos asociados a un proveedor si es necesario
}