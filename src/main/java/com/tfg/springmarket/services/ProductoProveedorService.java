package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.ProductoProveedor;
import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.model.repositories.ProductoProveedorRepository;
import com.tfg.springmarket.model.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProductoProveedorRepository productoProveedorRepository;

    public List<ProductoProveedor> agregarProductosProveedor(Long proveedorId, List<ProductoProveedor> productosProveedor) {
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + proveedorId));

        List<ProductoProveedor> productosGuardados = new ArrayList<>();
        for (ProductoProveedor productoProveedor : productosProveedor) {
            productoProveedor.setProveedor(proveedor);
            productosGuardados.add(productoProveedorRepository.save(productoProveedor));
        }

        return productosGuardados;
    }

    // Otros métodos para obtener, actualizar o eliminar productos asociados a un proveedor si es necesario
}