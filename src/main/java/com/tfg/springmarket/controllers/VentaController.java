package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.VentaDTO;
import com.tfg.springmarket.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<String> procesarVenta(@RequestBody VentaDTO ventaDTO) {
        String mensaje = ventaService.procesarVenta(ventaDTO.getProductoEstablecimientoId(), ventaDTO.getCantidad());
        return ResponseEntity.ok(mensaje);
    }
}