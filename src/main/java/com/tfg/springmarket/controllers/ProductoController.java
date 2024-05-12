package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.Producto;
import com.tfg.springmarket.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> getProductos() {
        List<Producto> allProductos = productoService.getProductos();
        return new ResponseEntity<>(allProductos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable("id") Long id) {
        Producto theProducto = productoService.getProducto(id);
        return new ResponseEntity<>(theProducto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Producto> addProducto(@RequestBody Producto producto) {
        Producto theProducto = productoService.addProducto(producto);
        return new ResponseEntity<>(theProducto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto) {
        Producto theProducto = productoService.addProducto(producto);
        return new ResponseEntity<>(theProducto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable("id") Long id) {
        productoService.deleteProducto(id);
    }

}