package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.services.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/establecimientos")
public class EstablecimientoController {

    private final EstablecimientoService establecimientoService;

    @Autowired
    public EstablecimientoController(EstablecimientoService establecimientoService) {
        this.establecimientoService = establecimientoService;
    }

    @GetMapping
    public ResponseEntity<List<Establecimiento>> getEstablecimientos() {
        List<Establecimiento> allEstablecimientos = establecimientoService.getEstablecimientos();
        return new ResponseEntity<>(allEstablecimientos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> getEstablecimiento(@PathVariable("id") Long id) {
        Establecimiento theEstablecimiento = establecimientoService.getEstablecimiento(id);
        return new ResponseEntity<>(theEstablecimiento, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Establecimiento> addEstablecimiento(@RequestBody Establecimiento establecimiento) {
        Establecimiento theEstablecimiento = establecimientoService.addEstablecimiento(establecimiento);
        return new ResponseEntity<>(theEstablecimiento, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Establecimiento> updateEstablecimiento(@PathVariable("id") Long id, @RequestBody Establecimiento establecimiento) {
        Establecimiento theEstablecimiento = establecimientoService.addEstablecimiento(establecimiento);
        return new ResponseEntity<>(theEstablecimiento, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteEstablecimiento(@PathVariable("id") Long id) {
        establecimientoService.deleteEstablecimiento(id);
    }
}
