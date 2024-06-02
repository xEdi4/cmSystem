package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.ProductosDTO;
import com.tfg.springmarket.model.entities.Producto;
import com.tfg.springmarket.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> getProductos(@RequestParam Optional<Long> proveedor, @RequestParam Optional<Long> establecimiento) {
        List<Producto> allProductos = new ArrayList<>();
        if(proveedor.isPresent()) {
            allProductos = productoService.getProductosByProveedor(proveedor.get());
        }else if(establecimiento.isPresent()){
            allProductos = productoService.getProductosByEstablecimiento(establecimiento.get());
        }else {
            allProductos = productoService.getProductos();
        }
        return new ResponseEntity<>(allProductos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable("id") Long id) {
        Producto theProducto = productoService.getProducto(id);
        return new ResponseEntity<>(theProducto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Producto> addProducto(@RequestBody Producto producto, @RequestParam Optional<Long> proveedor, @RequestParam Optional<Long> establecimiento) {
        Producto theProducto = productoService.addProducto(producto, proveedor, establecimiento);
        return new ResponseEntity<>(theProducto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto, @RequestParam Optional<Long> proveedor, @RequestParam Optional<Long> establecimiento) {
        Producto theProducto = productoService.addProducto(producto, proveedor, establecimiento);
        return new ResponseEntity<>(theProducto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable("id") Long id) {
        productoService.deleteProducto(id);
    }

    @PostMapping("/productosjson")
    public ResponseEntity<List<Producto>> addMultipleProductos(@RequestBody ProductosDTO productosDTO) {
        List<Producto> savedProductos = productoService.addMultipleProductos(productosDTO);
        return new ResponseEntity<>(savedProductos, HttpStatus.OK);
    }
}