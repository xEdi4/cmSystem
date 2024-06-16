package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.ProductosEstablecimiento;
import com.tfg.springmarket.model.repositories.ProductosEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosEstablecimientoService {

    @Autowired
    private ProductosEstablecimientoRepository productosEstablecimientoRepository;

    public List<ProductosEstablecimiento> obtenerTodosLosProductosEstablecimiento(Long establecimientoId) {
        return productosEstablecimientoRepository.findByEstablecimientoIdAndActivoTrue(establecimientoId);
    }

    public ProductosEstablecimiento actualizarProductoEstablecimiento(Long establecimientoId, Long id, ProductosEstablecimiento productoEstablecimiento) {
        Optional<ProductosEstablecimiento> productoExistente = productosEstablecimientoRepository.findByIdAndEstablecimientoIdAndActivoTrue(id, establecimientoId);
        if (productoExistente.isPresent()) {
            ProductosEstablecimiento productoActualizado = productoExistente.get();
            productoActualizado.setNombre(productoEstablecimiento.getNombre());
            productoActualizado.setPrecioCoste(productoEstablecimiento.getPrecioCoste());
            productoActualizado.setPrecioVenta(productoEstablecimiento.getPrecioVenta());
            productoActualizado.setStock(productoEstablecimiento.getStock());
            return productosEstablecimientoRepository.save(productoActualizado);
        } else {
            return null; // Manejar el caso de no encontrado si es necesario
        }
    }

    public void eliminarProductoEstablecimiento(Long establecimientoId, Long id) {
        Optional<ProductosEstablecimiento> productoExistente = productosEstablecimientoRepository.findByIdAndEstablecimientoIdAndActivoTrue(id, establecimientoId);
        if (productoExistente.isPresent()) {
            ProductosEstablecimiento producto = productoExistente.get();
            producto.setActivo(false); // Realizar borrado lógico
            productosEstablecimientoRepository.save(producto);
        }
    }
}