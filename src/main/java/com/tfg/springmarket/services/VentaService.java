package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.ProductoEstablecimiento;
import com.tfg.springmarket.model.entities.VentaEstablecimiento;
import com.tfg.springmarket.model.repositories.ProductoEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    @Autowired
    private ProductoEstablecimientoRepository productoEstablecimientoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    public String procesarVenta(Long productoEstablecimientoId, Integer cantidad) {
        ProductoEstablecimiento productoEstablecimiento = productoEstablecimientoRepository.findById(productoEstablecimientoId).orElse(null);

        if (productoEstablecimiento == null) {
            return "Producto no encontrado en el establecimiento";
        }

        if (productoEstablecimiento.getStock() < cantidad) {
            return "Stock insuficiente en el establecimiento";
        }

        // Registrar la ventaEstablecimiento
        VentaEstablecimiento ventaEstablecimiento = new VentaEstablecimiento();
        ventaEstablecimiento.setProductoEstablecimiento(productoEstablecimiento);
        ventaEstablecimiento.setCantidad(cantidad);
        ventaEstablecimiento.setPrecioVenta(productoEstablecimiento.getPrecioVenta()); // Utilizar el precio de ventaEstablecimiento establecido para el producto
        ventaEstablecimiento.setPrecioCoste(productoEstablecimiento.getPrecioCoste()); // Utilizar el precio de costo establecido para el producto
        ventaRepository.save(ventaEstablecimiento);

        // Actualizar el stock en el establecimiento
        productoEstablecimiento.setStock(productoEstablecimiento.getStock() - cantidad);
        productoEstablecimientoRepository.save(productoEstablecimiento);

        return "VentaEstablecimiento realizada con éxito";
    }
}