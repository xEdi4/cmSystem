package com.tfg.springmarket.services;

import com.tfg.springmarket.dto.CompraDTO;
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
import java.util.List;

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
    public String comprarProductos(Long establecimientoId, List<CompraDTO> compras) {
        Establecimiento establecimiento = establecimientoRepository.findById(establecimientoId).orElse(null);

        if (establecimiento == null) {
            return "Establecimiento no encontrado";
        }

        StringBuilder resultado = new StringBuilder();

        for (CompraDTO compra : compras) {
            Long productoProveedorId = compra.getProductoProveedorId();
            Integer cantidad = compra.getCantidad();

            ProductoProveedor productoProveedor = productoProveedorRepository.findById(productoProveedorId).orElse(null);

            if (productoProveedor == null) {
                resultado.append("Producto del proveedor no encontrado: ").append(productoProveedorId).append("\n");
                continue;
            }

            if (productoProveedor.getStock() < cantidad) {
                resultado.append("Stock insuficiente en el proveedor para el producto: ").append(productoProveedorId).append("\n");
                continue;
            }

            // Actualizar el stock del proveedor
            productoProveedor.setStock(productoProveedor.getStock() - cantidad);
            productoProveedorRepository.save(productoProveedor);

            // Registrar la venta del proveedor
            VentaProveedor ventaProveedor = new VentaProveedor();
            ventaProveedor.setProductoProveedor(productoProveedor);
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
            productoEstablecimientoRepository.save(productoEstablecimiento); // Asegurarse de guardar el productoEstablecimiento

            resultado.append("Compra realizada con éxito para el producto: ").append(productoProveedorId).append("\n");
        }

        return resultado.toString();
    }
}