package com.tfg.springmarket.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.springmarket.model.entities.ProductosProveedor;
import com.tfg.springmarket.services.ProductosProveedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/proveedor/productos")
public class ProductosProveedorController {

    private static final Logger logger = LoggerFactory.getLogger(ProductosProveedorController.class);

    @Autowired
    private ProductosProveedorService productosProveedorService;

    @PostMapping("/{proveedorId}")
    public ResponseEntity<List<ProductosProveedor>> agregarProductosProveedor(@PathVariable Long proveedorId, @RequestParam("file") MultipartFile file) {
        try {
            List<ProductosProveedor> productosDelArchivo = parsearArchivoJSON(file);
            return ResponseEntity.ok(productosProveedorService.agregarProductosProveedor(proveedorId, productosDelArchivo));
        } catch (IOException e) {
            logger.error("Error al procesar el archivo JSON", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{proveedorId}")
    public ResponseEntity<List<ProductosProveedor>> obtenerTodosLosProductosProveedor(@PathVariable Long proveedorId) {
        List<ProductosProveedor> productosProveedor = productosProveedorService.obtenerTodosLosProductosProveedor(proveedorId);
        return ResponseEntity.ok(productosProveedor);
    }

    @GetMapping
    public ResponseEntity<List<ProductosProveedor>> obtenerTodosLosProductos() {
        List<ProductosProveedor> productosProveedor = productosProveedorService.obtenerTodosLosProductos();
        return ResponseEntity.ok(productosProveedor);
    }

    @PutMapping("/{proveedorId}/{id}")
    public ResponseEntity<ProductosProveedor> actualizarProductoProveedor(@PathVariable Long proveedorId, @PathVariable Long id, @RequestBody ProductosProveedor productoProveedor) {
        ProductosProveedor productoProveedorActualizado = productosProveedorService.actualizarProductoProveedor(proveedorId, id, productoProveedor);
        return ResponseEntity.ok(productoProveedorActualizado);
    }

    @DeleteMapping("/{proveedorId}/{id}")
    public ResponseEntity<?> eliminarProductoProveedor(@PathVariable Long proveedorId, @PathVariable Long id) {
        productosProveedorService.eliminarProductoProveedor(proveedorId, id);
        return ResponseEntity.ok().build();
    }

    private List<ProductosProveedor> parsearArchivoJSON(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file.getInputStream(), new TypeReference<List<ProductosProveedor>>() {
        });
    }

}