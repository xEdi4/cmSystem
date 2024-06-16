package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.CompraDTO;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.services.ComprasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/establecimientos/compras")
public class ComprasEstablecimientoController {

    private static final Logger logger = LoggerFactory.getLogger(ComprasEstablecimientoController.class);

    @Autowired
    private ComprasService comprasService;

    @PostMapping
    public ResponseEntity<String> comprarProductos(@RequestBody List<CompraDTO> comprasDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Usuario usuario = (Usuario) authentication.getPrincipal();
                Long establecimientoId = usuario.getId();

                String mensaje = comprasService.comprarProductos(establecimientoId, comprasDTO);
                return ResponseEntity.ok(mensaje);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            logger.error("Error al realizar la compra", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar la compra");
        }
    }
}