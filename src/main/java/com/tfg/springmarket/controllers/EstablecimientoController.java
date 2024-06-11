package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.services.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/establecimientos")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @PostMapping
    public ResponseEntity<Establecimiento> agregarEstablecimiento(@RequestBody Establecimiento establecimiento) {
        Establecimiento establecimientoGuardado = establecimientoService.agregarEstablecimiento(establecimiento);
        return ResponseEntity.ok(establecimientoGuardado);
    }

    @GetMapping
    public ResponseEntity<List<Establecimiento>> obtenerTodosLosEstablecimientos() {
        List<Establecimiento> establecimientos = establecimientoService.obtenerTodosLosEstablecimientos();
        return ResponseEntity.ok(establecimientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> obtenerEstablecimientoPorId(@PathVariable Long id) {
        Establecimiento establecimiento = establecimientoService.obtenerEstablecimientoPorId(id);
        return ResponseEntity.ok(establecimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Establecimiento> actualizarEstablecimiento(@PathVariable Long id, @RequestBody Establecimiento establecimiento) {
        Establecimiento establecimientoActualizado = establecimientoService.actualizarEstablecimiento(id, establecimiento);
        return ResponseEntity.ok(establecimientoActualizado);
    }

}
