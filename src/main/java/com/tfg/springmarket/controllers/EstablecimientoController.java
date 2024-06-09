package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.FechaRequestDTO;
import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.VentaEstablecimiento;
import com.tfg.springmarket.services.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/establecimientos")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @GetMapping("/{establecimientoId}/ventas")
    public ResponseEntity<List<VentaEstablecimiento>> obtenerVentasEntreFechasDeEstablecimiento(
            @PathVariable Long establecimientoId,
            @RequestBody FechaRequestDTO fechaRequestDTO) {

        List<VentaEstablecimiento> ventas = establecimientoService.obtenerVentasEntreFechasDeEstablecimiento(establecimientoId, fechaRequestDTO);

        if (ventas == null) {
            // Manejar el caso donde el establecimiento no existe
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ventas);
    }

    @PostMapping
    public ResponseEntity<Establecimiento> agregarEstablecimiento(@RequestBody Establecimiento establecimiento) {
        Establecimiento establecimientoGuardado = establecimientoService.agregarEstablecimiento(establecimiento);
        return ResponseEntity.ok(establecimientoGuardado);
    }
}
