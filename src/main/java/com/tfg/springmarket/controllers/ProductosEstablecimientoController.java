package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.CompraDTO;
import com.tfg.springmarket.services.ProductosEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/establecimientos/{establecimientoId}/compras")
public class ProductosEstablecimientoController {

    @Autowired
    private ProductosEstablecimientoService productosEstablecimientoService;

    @PostMapping
    public ResponseEntity<String> comprarProductos(
            @PathVariable Long establecimientoId,
            @RequestBody List<CompraDTO> comprasDTO) {
        String mensaje = productosEstablecimientoService.comprarProductos(establecimientoId, comprasDTO);
        return ResponseEntity.ok(mensaje);
    }
}