package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> getProveedores() {
        List<Proveedor> allProveedores = proveedorService.getProveedores();
        return new ResponseEntity<>(allProveedores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getProveedor(@PathVariable("id") Long id) {
        Proveedor theProveedor = proveedorService.getProveedor(id);
        return new ResponseEntity<>(theProveedor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Proveedor> addProveedor(@RequestBody Proveedor proveedor) {
        Proveedor theProveedor = proveedorService.addProveedor(proveedor);
        return new ResponseEntity<>(theProveedor, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Proveedor> updateProveedor(@RequestBody Proveedor proveedor) {
        Proveedor theProveedor = proveedorService.addProveedor(proveedor);
        return new ResponseEntity<>(theProveedor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProveedor(@PathVariable("id") Long id) {
        proveedorService.deleteProveedor(id);
    }

}
