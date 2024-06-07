package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.ProductoProveedor;
import com.tfg.springmarket.services.ProductoProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proveedores/{proveedorId}/productos")
public class ProductoProveedorController {

    @Autowired
    private ProductoProveedorService productoProveedorService;

    @PostMapping
    public ResponseEntity<ProductoProveedor> agregarProductoProveedor(
            @PathVariable Long proveedorId,
            @RequestBody ProductoProveedor productoProveedor) {
        ProductoProveedor productoGuardado = productoProveedorService.agregarProductoProveedor(proveedorId, productoProveedor);
        return ResponseEntity.ok(productoGuardado);
    }

    // Otros endpoints para obtener, actualizar o eliminar productos asociados a un proveedor si es necesario
}