package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.VentaDTO;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.services.VentasEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/establecimientos/ventas")
public class VentasEstablecimientoController {

    @Autowired
    private VentasEstablecimientoService ventasEstablecimientoService;

    @PostMapping
    public ResponseEntity<String> procesarVentas(@RequestBody List<VentaDTO> ventasDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long establecimientoId = usuario.getId();

            // Verificar que los productos pertenecen al proveedor (usuario autenticado)
            boolean productosPertenecenAlProveedor = ventasEstablecimientoService.verificarProductosPertenecenAlProveedor(establecimientoId, ventasDTO);

            if (!productosPertenecenAlProveedor) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Uno o más productos no pertenecen al proveedor.");
            }

            String mensaje = ventasEstablecimientoService.procesarVentas(ventasDTO);
            return ResponseEntity.ok(mensaje);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}