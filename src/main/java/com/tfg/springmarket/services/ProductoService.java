package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Producto;
import com.tfg.springmarket.exceptions.ProductoNotFoundException;
import com.tfg.springmarket.model.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    public Producto getProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new ProductoNotFoundException("El producto no se ha podido encontrar"));
    }

    public Producto addProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

}