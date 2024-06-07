package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.ProductoEstablecimiento;
import com.tfg.springmarket.model.entities.ProductoProveedor;
import com.tfg.springmarket.model.entities.VentaProveedor;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import com.tfg.springmarket.model.repositories.ProductoEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.ProductoProveedorRepository;
import com.tfg.springmarket.model.repositories.VentaProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CompraService {

    @Autowired
    private ProductoProveedorRepository productoProveedorRepository;

    @Autowired
    private ProductoEstablecimientoRepository productoEstablecimientoRepository;

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private VentaProveedorRepository ventaProveedorRepository;

    @Transactional
    public String comprarProducto(Long establecimientoId, Long productoProveedorId, Integer cantidad) {
        ProductoProveedor productoProveedor = productoProveedorRepository.findById(productoProveedorId).orElse(null);
        Establecimiento establecimiento = establecimientoRepository.findById(establecimientoId).orElse(null);

        if (productoProveedor == null) {
            return "Producto del proveedor no encontrado";
        }

        if (establecimiento == null) {
            return "Establecimiento no encontrado";
        }

        if (productoProveedor.getStock() < cantidad) {
            return "Stock insuficiente en el proveedor";
        }

        // Actualizar el stock del proveedor
        productoProveedor.setStock(productoProveedor.getStock() - cantidad);
        productoProveedorRepository.save(productoProveedor);

        // Registrar la venta del proveedor
        VentaProveedor ventaProveedor = new VentaProveedor();
        ventaProveedor.setProductoProveedor(productoProveedor);
        ventaProveedor.setEstablecimiento(establecimiento);
        ventaProveedor.setCantidad(cantidad);
        ventaProveedor.setPrecioVenta(productoProveedor.getPrecioVenta());
        ventaProveedor.setFechaVenta(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        ventaProveedorRepository.save(ventaProveedor);

        // Actualizar o crear el producto en el establecimiento
        ProductoEstablecimiento productoEstablecimiento = productoEstablecimientoRepository.findByEstablecimientoAndNombre(establecimiento, productoProveedor.getNombre());
        if (productoEstablecimiento == null) {
            productoEstablecimiento = new ProductoEstablecimiento();
            productoEstablecimiento.setNombre(productoProveedor.getNombre());
            productoEstablecimiento.setPrecioCoste(productoProveedor.getPrecioVenta());
            productoEstablecimiento.setPrecioVenta(productoProveedor.getPrecioVenta() * 1.3); // Precio de venta inicial
            productoEstablecimiento.setStock(cantidad);
            productoEstablecimiento.setEstablecimiento(establecimiento);
        } else {
            productoEstablecimiento.setStock(productoEstablecimiento.getStock() + cantidad);
        }
        productoEstablecimientoRepository.save(productoEstablecimiento);

        return "Compra realizada con éxito";
    }
}