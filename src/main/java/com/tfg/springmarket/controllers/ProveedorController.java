package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<Proveedor> agregarProveedor(@RequestBody Proveedor proveedor) {
        Proveedor proveedorGuardado = proveedorService.agregarProveedor(proveedor);
        return ResponseEntity.ok(proveedorGuardado);
    }

    // Otros endpoints para obtener, actualizar o eliminar proveedores si es necesario
}
