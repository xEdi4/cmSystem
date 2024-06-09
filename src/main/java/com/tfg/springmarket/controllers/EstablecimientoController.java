package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.services.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
