package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.Producto;
import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import com.tfg.springmarket.model.repositories.ProductoRepository;
import com.tfg.springmarket.model.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Producto> getProductosByProveedor(Long proveedorId) {
        return productoRepository.findByProveedorId(proveedorId);
    }

    public List<Producto> getProductosByEstablecimiento(Long establecimientoId) {
        return productoRepository.findByEstablecimientoId(establecimientoId);
    }

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    public Producto getProducto(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto addProducto(Producto producto, Optional<Long> proveedorId, Optional<Long> establecimientoId) {
        if (proveedorId.isPresent()){
            Optional<Proveedor> proveedorOpt = proveedorRepository.findById(proveedorId.get());
            producto.setProveedor(proveedorOpt.get());
            return productoRepository.save(producto);
        }else if(establecimientoId.isPresent()) {
            Optional<Establecimiento> establecimientoOpt = establecimientoRepository.findById(establecimientoId.get());
            producto.setEstablecimiento(establecimientoOpt.get());
            return productoRepository.save(producto);
        }
        else{
            throw new RuntimeException("Proveedor no encontrado");
        }

    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

}