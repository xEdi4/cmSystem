package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.CompraDTO;
import com.tfg.springmarket.services.ComprasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras/{establecimientoId}")
public class ComprasEstablecimientoController {

    @Autowired
    private ComprasService comprasService;

    @PostMapping
    public ResponseEntity<String> comprarProductos(@PathVariable Long establecimientoId, @RequestBody List<CompraDTO> comprasDTO) {
        String mensaje = comprasService.comprarProductos(establecimientoId, comprasDTO);
        return ResponseEntity.ok(mensaje);
    }
}