package com.tfg.springmarket.services;

import com.tfg.springmarket.dto.ProductoDTO;
import com.tfg.springmarket. dto.ProductosDTO;
import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.Producto;
import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import com.tfg.springmarket.model.repositories.ProductoRepository;
import com.tfg.springmarket.model.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (proveedorId.isPresent()) {
            Optional<Proveedor> proveedorOpt = proveedorRepository.findById(proveedorId.get());
            if (!proveedorOpt.isPresent()) {
                throw new RuntimeException("Proveedor no encontrado");
            }
            producto.setProveedor(proveedorOpt.get());
        } else if (establecimientoId.isPresent()) {
            Optional<Establecimiento> establecimientoOpt = establecimientoRepository.findById(establecimientoId.get());
            if (!establecimientoOpt.isPresent()) {
                throw new RuntimeException("Establecimiento no encontrado");
            }
            producto.setEstablecimiento(establecimientoOpt.get());
        } else {
            throw new RuntimeException("Debe proporcionar un ID de proveedor o establecimiento");
        }
        return productoRepository.save(producto);
    }

    public List<Producto> addMultipleProductos(ProductosDTO productosDTO) {
        List<Producto> savedProductos = new ArrayList<>();
        for (ProductoDTO productoDTO : productosDTO.getProductos()) {
            Producto producto = new Producto();
            producto.setNombre(productoDTO.getNombre());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setStock(productoDTO.getStock());

            Optional<Long> proveedorId = Optional.ofNullable(productoDTO.getProveedorId());
            Optional<Long> establecimientoId = Optional.ofNullable(productoDTO.getEstablecimientoId());

            if (proveedorId.isPresent()) {
                Proveedor proveedor = proveedorRepository.findById(proveedorId.get())
                        .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
                producto.setProveedor(proveedor);
            }
            if (establecimientoId.isPresent()) {
                Establecimiento establecimiento = establecimientoRepository.findById(establecimientoId.get())
                        .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado"));
                producto.setEstablecimiento(establecimiento);
            }

            savedProductos.add(productoRepository.save(producto));
        }
        return savedProductos;
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
