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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductosEstablecimientoService {

    @Autowired
    private ProductosProveedorRepository productosProveedorRepository;

    @Autowired
    private ProductosEstablecimientoRepository productosEstablecimientoRepository;

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

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

            ProductosProveedor productosProveedor = productosProveedorRepository.findById(productoProveedorId).orElse(null);

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
            ventasProveedor.setFechaVenta(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ventasProveedorRepository.save(ventasProveedor);

            // Actualizar o crear el producto en el establecimiento
            ProductosEstablecimiento productosEstablecimiento = productosEstablecimientoRepository.findByEstablecimientoAndNombre(establecimiento, productosProveedor.getNombre());
            if (productosEstablecimiento == null) {
                productosEstablecimiento = new ProductosEstablecimiento();
                productosEstablecimiento.setNombre(productosProveedor.getNombre());
                productosEstablecimiento.setPrecioCoste(productosProveedor.getPrecioVenta());
                productosEstablecimiento.setPrecioVenta(productosProveedor.getPrecioVenta() * 1.3); // Precio de venta inicial
                productosEstablecimiento.setStock(cantidad);
                productosEstablecimiento.setEstablecimiento(establecimiento);
            } else {
                productosEstablecimiento.setStock(productosEstablecimiento.getStock() + cantidad);
            }
            productosEstablecimientoRepository.save(productosEstablecimiento); // Asegurarse de guardar el productosEstablecimiento

            resultado.append("Compra realizada con éxito para el producto: ").append(productoProveedorId).append("\n");
        }

        return resultado.toString();
    }
}