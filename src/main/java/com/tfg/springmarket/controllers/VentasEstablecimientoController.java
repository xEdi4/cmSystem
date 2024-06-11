package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.VentaDTO;
import com.tfg.springmarket.services.VentasEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/ventas")
public class VentasEstablecimientoController {

    @Autowired
    private VentasEstablecimientoService ventasEstablecimientoService;

    @PostMapping
    public ResponseEntity<String> procesarVentas(@RequestBody List<VentaDTO> ventasDTO) {
        String mensaje = ventasEstablecimientoService.procesarVentas(ventasDTO);
        return ResponseEntity.ok(mensaje);
    }
}