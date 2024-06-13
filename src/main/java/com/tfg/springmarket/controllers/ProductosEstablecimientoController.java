package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.CompraDTO;
import com.tfg.springmarket.model.entities.ProductosEstablecimiento;
import com.tfg.springmarket.services.ProductosEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/establecimientos/{establecimientoId}/productos")
public class ProductosEstablecimientoController {

    @Autowired
    private ProductosEstablecimientoService productosEstablecimientoService;

    @GetMapping
    public ResponseEntity<List<ProductosEstablecimiento>> obtenerTodosLosProductosEstablecimiento(@PathVariable Long establecimientoId) {
        List<ProductosEstablecimiento> productosEstablecimiento = productosEstablecimientoService.obtenerTodosLosProductosEstablecimiento(establecimientoId);
        return ResponseEntity.ok(productosEstablecimiento);
    }

    @PostMapping
    public ResponseEntity<String> comprarProductos(
            @PathVariable Long establecimientoId,
            @RequestBody List<CompraDTO> comprasDTO) {
        String mensaje = productosEstablecimientoService.comprarProductos(establecimientoId, comprasDTO);
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosEstablecimiento> actualizarProductoEstablecimiento(@PathVariable Long establecimientoId, @PathVariable Long id, @RequestBody ProductosEstablecimiento productoEstablecimiento) {
        ProductosEstablecimiento productoEstablecimientoActualizado = productosEstablecimientoService.actualizarProductoEstablecimiento(establecimientoId, id, productoEstablecimiento);
        return ResponseEntity.ok(productoEstablecimientoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProductoEstablecimiento(@PathVariable Long establecimientoId, @PathVariable Long id) {
        productosEstablecimientoService.eliminarProductoEstablecimiento(establecimientoId, id);
        return ResponseEntity.ok().build();
    }

}
