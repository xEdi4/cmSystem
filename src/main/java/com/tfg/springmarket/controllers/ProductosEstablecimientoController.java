package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.ProductosEstablecimiento;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.services.ProductosEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/establecimientos/productos")
public class ProductosEstablecimientoController {

    @Autowired
    private ProductosEstablecimientoService productosEstablecimientoService;

    @GetMapping
    public ResponseEntity<List<ProductosEstablecimiento>> obtenerTodosLosProductosEstablecimiento() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long establecimientoId = usuario.getId();
            List<ProductosEstablecimiento> productosEstablecimiento = productosEstablecimientoService.obtenerTodosLosProductosEstablecimiento(establecimientoId);
            return ResponseEntity.ok(productosEstablecimiento);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosEstablecimiento> actualizarProductoEstablecimiento(@PathVariable Long id, @RequestBody ProductosEstablecimiento productoEstablecimiento) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long establecimientoId = usuario.getId();
            ProductosEstablecimiento productoEstablecimientoActualizado = productosEstablecimientoService.actualizarProductoEstablecimiento(establecimientoId, id, productoEstablecimiento);
            return ResponseEntity.ok(productoEstablecimientoActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProductoEstablecimiento(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long establecimientoId = usuario.getId();
            productosEstablecimientoService.eliminarProductoEstablecimiento(establecimientoId, id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
