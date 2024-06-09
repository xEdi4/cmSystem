package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.ProductosProveedor;
import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.model.repositories.ProductosProveedorRepository;
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
    private ProductosProveedorRepository productosProveedorRepository;

    public List<ProductosProveedor> agregarProductosProveedor(Long proveedorId, List<ProductosProveedor> productosProveedor) {
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + proveedorId));

        List<ProductosProveedor> productosGuardados = new ArrayList<>();
        for (ProductosProveedor productoProveedor : productosProveedor) {
            productoProveedor.setProveedor(proveedor);
            productosGuardados.add(productosProveedorRepository.save(productoProveedor));
        }

        return productosGuardados;
    }

    // Otros métodos para obtener, actualizar o eliminar productos asociados a un proveedor si es necesario
}