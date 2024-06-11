package com.tfg.springmarket.services;

import com.tfg.springmarket.dto.CompraDTO;
import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.ProductosEstablecimiento;
import com.tfg.springmarket.model.entities.ProductosProveedor;
import com.tfg.springmarket.model.entities.VentasProveedor;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import com.tfg.springmarket.model.repositories.ProductosEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.ProductosProveedorRepository;
import com.tfg.springmarket.model.repositories.VentasProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ComprasService {

    @Autowired
    private ProductosEstablecimientoRepository productosEstablecimientoRepository;

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private ProductosProveedorRepository productosProveedorRepository;

    @Autowired
    private VentasProveedorRepository ventasProveedorRepository;

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

            ProductosProveedor productosProveedor = productosProveedorRepository.findByIdAndActivoTrue(productoProveedorId).orElse(null);

            if (productosProveedor == null) {
                resultado.append("Producto del proveedor no encontrado: ").append(productoProveedorId).append("\n");
                continue;
            }

            if (productosProveedor.getStock() < cantidad) {
                resultado.append("Stock insuficiente en el proveedor para el producto: ").append(productoProveedorId).append("\n");
                continue;
            }

            // Actualizar el stock del proveedor
            productosProveedor.setStock(productosProveedor.getStock() - cantidad);
            productosProveedorRepository.save(productosProveedor);

            // Registrar la venta del proveedor
            VentasProveedor ventasProveedor = new VentasProveedor();
            ventasProveedor.setProductosProveedor(productosProveedor);
            ventasProveedor.setCantidad(cantidad);
            ventasProveedor.setPrecioVenta(productosProveedor.getPrecioVenta());
            ventasProveedor.setFechaVenta(LocalDate.now());
            ventasProveedorRepository.save(ventasProveedor);

            // Actualizar o crear el producto en el establecimiento
            ProductosEstablecimiento productosEstablecimiento = productosEstablecimientoRepository.findByEstablecimientoAndNombre(establecimiento, productosProveedor.getNombre());
            if (productosEstablecimiento == null) {
                productosEstablecimiento = new ProductosEstablecimiento();
                productosEstablecimiento.setNombre(productosProveedor.getNombre());
                productosEstablecimiento.setPrecioCoste(productosProveedor.getPrecioVenta());
                BigDecimal precioVenta = BigDecimal.valueOf(productosProveedor.getPrecioVenta() * 1.3)
                        .setScale(2, RoundingMode.HALF_UP);
                productosEstablecimiento.setPrecioVenta(precioVenta);
                productosEstablecimiento.setStock(cantidad);
                productosEstablecimiento.setEstablecimiento(establecimiento);
                productosEstablecimiento.setProveedor(productosProveedor.getProveedor());
                productosEstablecimiento.setActivo(true); // Asegurarse de que el producto en el establecimiento esté activo
            } else {
                productosEstablecimiento.setStock(productosEstablecimiento.getStock() + cantidad);
            }
            productosEstablecimientoRepository.save(productosEstablecimiento);
        }

        return resultado.toString();
    }
}