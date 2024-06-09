package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.VentaDTO;
import com.tfg.springmarket.services.VentaEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentasEstablecimientoController {

    @Autowired
    private VentaEstablecimientoService ventaEstablecimientoService;

    @PostMapping
    public ResponseEntity<String> procesarVentas(@RequestBody List<VentaDTO> ventasDTO) {
        String mensaje = ventaEstablecimientoService.procesarVentas(ventasDTO);
        return ResponseEntity.ok(mensaje);
    }
}