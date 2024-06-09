package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.ProductosEstablecimiento;
import com.tfg.springmarket.model.repositories.ProductosEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosEstablecimientoService {

    @Autowired
    private ProductosEstablecimientoRepository productosEstablecimientoRepository;

    public List<ProductosEstablecimiento> obtenerTodosLosProductosEstablecimiento(Long establecimientoId) {
        return productosEstablecimientoRepository.findByEstablecimientoId(establecimientoId);
    }

    public ProductosEstablecimiento actualizarProductoEstablecimiento(Long establecimientoId, Long id, ProductosEstablecimiento productoEstablecimiento) {
        ProductosEstablecimiento existente = productosEstablecimientoRepository.findByEstablecimientoIdAndId(establecimientoId, id);
        if (existente != null) {
            existente.setNombre(productoEstablecimiento.getNombre());
            existente.setPrecioCoste(productoEstablecimiento.getPrecioCoste());
            existente.setPrecioVenta(productoEstablecimiento.getPrecioVenta());
            existente.setStock(productoEstablecimiento.getStock());
            return productosEstablecimientoRepository.save(existente);
        }
        return null;
    }

    public void eliminarProductoEstablecimiento(Long establecimientoId, Long id) {
        ProductosEstablecimiento existente = productosEstablecimientoRepository.findByEstablecimientoIdAndId(establecimientoId, id);
        if (existente != null) {
            productosEstablecimientoRepository.delete(existente);
        }
    }
}