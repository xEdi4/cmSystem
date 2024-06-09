package com.tfg.springmarket.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.springmarket.model.entities.ProductoProveedor;
import com.tfg.springmarket.services.ProductoProveedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/proveedores/{proveedorId}/productos")
public class ProductoProveedorController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoProveedorController.class);

    @Autowired
    private ProductoProveedorService productoProveedorService;

    @PostMapping
    public ResponseEntity<List<ProductoProveedor>> agregarProductosProveedor(
            @PathVariable Long proveedorId,
            @RequestParam("file") MultipartFile file) {
        try {
            List<ProductoProveedor> productosDelArchivo = parsearArchivoJSON(file);
            return ResponseEntity.ok(productoProveedorService.agregarProductosProveedor(proveedorId, productosDelArchivo));
        } catch (IOException e) {
            logger.error("Error al procesar el archivo JSON", e);
            return ResponseEntity.badRequest().build();
        }
    }

    private List<ProductoProveedor> parsearArchivoJSON(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file.getInputStream(), new TypeReference<List<ProductoProveedor>>() {
        });
    }

    // Otros endpoints para obtener, actualizar o eliminar productos asociados a un proveedor si es necesario
}