package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Producto;
import com.tfg.springmarket.exceptions.ProductoNotFoundException;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import com.tfg.springmarket.model.repositories.ProductoRepository;
import com.tfg.springmarket.model.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final EstablecimientoRepository establecimientoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, ProveedorRepository proveedorRepository, EstablecimientoRepository establecimientoRepository) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.establecimientoRepository = establecimientoRepository;
    }

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    public Producto getProducto(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto addProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

}