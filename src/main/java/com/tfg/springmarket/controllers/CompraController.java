package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.CompraDTO;
import com.tfg.springmarket.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/establecimientos/{establecimientoId}/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping
    public ResponseEntity<String> comprarProductos(
            @PathVariable Long establecimientoId,
            @RequestBody List<CompraDTO> comprasDTO) {
        String mensaje = compraService.comprarProductos(establecimientoId, comprasDTO);
        return ResponseEntity.ok(mensaje);
    }
}