package com.tfg.springmarket.controllers;

import com.tfg.springmarket.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping
    public ResponseEntity<String> comprarProducto(@RequestParam Long establecimientoId, @RequestParam Long productoProveedorId, @RequestParam Integer cantidad) {
        String mensaje = compraService.comprarProducto(establecimientoId, productoProveedorId, cantidad);
        return ResponseEntity.ok(mensaje);
    }
}