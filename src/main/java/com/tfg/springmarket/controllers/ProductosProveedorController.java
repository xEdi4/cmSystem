package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.ProductosProveedor;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.services.ProductosProveedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/proveedores")
public class ProductosProveedorController {

    private static final Logger logger = LoggerFactory.getLogger(ProductosProveedorController.class);

    @Autowired
    private ProductosProveedorService productosProveedorService;

    @PostMapping("/productos")
    public ResponseEntity<List<ProductosProveedor>> agregarProductosProveedor(@RequestParam("file") MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Usuario usuario = (Usuario) authentication.getPrincipal();
                Long proveedorId = usuario.getId();

                List<ProductosProveedor> productosDelArchivo = productosProveedorService.parsearArchivoJSON(file);
                return ResponseEntity.ok(productosProveedorService.agregarProductosProveedor(proveedorId, productosDelArchivo));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (IOException e) {
            logger.error("Error al procesar el archivo JSON", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductosProveedor>> obtenerTodosLosProductosProveedor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            List<ProductosProveedor> productosProveedor = productosProveedorService.obtenerTodosLosProductosProveedor(proveedorId);
            return ResponseEntity.ok(productosProveedor);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/allProductos")
    public ResponseEntity<List<ProductosProveedor>> obtenerTodosLosProductos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            List<ProductosProveedor> productosProveedor = productosProveedorService.obtenerTodosLosProductos();
            return ResponseEntity.ok(productosProveedor);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductosProveedor> actualizarProductoProveedor(@PathVariable Long id, @RequestBody ProductosProveedor productoProveedor) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            ProductosProveedor productoProveedorActualizado = productosProveedorService.actualizarProductoProveedor(proveedorId, id, productoProveedor);
            return ResponseEntity.ok(productoProveedorActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminarProductoProveedor(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            productosProveedorService.eliminarProductoProveedor(proveedorId, id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}