package com.tfg.springmarket.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.springmarket.model.entities.ProductosProveedor;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.model.repositories.ProductosProveedorRepository;
import com.tfg.springmarket.model.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductosProveedorService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductosProveedorRepository productosProveedorRepository;

    public List<ProductosProveedor> parsearArchivoJSON(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file.getInputStream(), new TypeReference<List<ProductosProveedor>>() {
        });
    }

    public List<ProductosProveedor> agregarProductosProveedor(Long proveedorId, List<ProductosProveedor> productosProveedor) {
        Usuario proveedor = usuarioRepository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + proveedorId));

        List<ProductosProveedor> productosGuardados = new ArrayList<>();
        for (ProductosProveedor productoProveedor : productosProveedor) {
            productoProveedor.setProveedor(proveedor);
            productosGuardados.add(productosProveedorRepository.save(productoProveedor));
        }

        return productosGuardados;
    }

    public List<ProductosProveedor> obtenerTodosLosProductosProveedor(Long proveedorId) {
        return productosProveedorRepository.findByProveedorIdAndActivoTrue(proveedorId);
    }

    public List<ProductosProveedor> obtenerTodosLosProductos() {
        return productosProveedorRepository.findAllByActivoTrue();
    }

    public ProductosProveedor actualizarProductoProveedor(Long proveedorId, Long id, ProductosProveedor productoProveedor) {
        ProductosProveedor existente = productosProveedorRepository.findByProveedorIdAndIdAndActivoTrue(proveedorId, id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id + " para el proveedor con id: " + proveedorId));

        existente.setNombre(productoProveedor.getNombre());
        existente.setPrecioVenta(productoProveedor.getPrecioVenta());
        existente.setStock(productoProveedor.getStock());

        return productosProveedorRepository.save(existente);
    }

    public void eliminarProductoProveedor(Long proveedorId, Long id) {
        ProductosProveedor existente = productosProveedorRepository.findByProveedorIdAndIdAndActivoTrue(proveedorId, id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id + " para el proveedor con id: " + proveedorId));

        existente.setActivo(false); // Realizar borrado lógico
        productosProveedorRepository.save(existente);
    }

}