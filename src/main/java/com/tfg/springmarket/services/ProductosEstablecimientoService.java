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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductosEstablecimientoService {

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private ProductosEstablecimientoRepository productosEstablecimientoRepository;

    @Autowired
    private ProductosProveedorRepository productosProveedorRepository;

    @Autowired
    private VentasProveedorRepository ventasProveedorRepository;
    public List<ProductosEstablecimiento> obtenerTodosLosProductosEstablecimiento(Long establecimientoId) {
        return productosEstablecimientoRepository.findByEstablecimientoIdAndActivoTrue(establecimientoId);
    }

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
            ventasProveedor.setProveedor(productosProveedor.getProveedor()); // Asegurar asignación del proveedor
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
                Double precioVenta = BigDecimal.valueOf(productosProveedor.getPrecioVenta() * 1.3)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
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

    public ProductosEstablecimiento actualizarProductoEstablecimiento(Long establecimientoId, Long id, ProductosEstablecimiento productoEstablecimiento) {
        Optional<ProductosEstablecimiento> productoExistente = productosEstablecimientoRepository.findByIdAndEstablecimientoIdAndActivoTrue(id, establecimientoId);
        if (productoExistente.isPresent()) {
            ProductosEstablecimiento productoActualizado = productoExistente.get();
            productoActualizado.setNombre(productoEstablecimiento.getNombre());
            productoActualizado.setPrecioCoste(productoEstablecimiento.getPrecioCoste());
            productoActualizado.setPrecioVenta(productoEstablecimiento.getPrecioVenta());
            productoActualizado.setStock(productoEstablecimiento.getStock());
            productoActualizado.setProveedor(productoEstablecimiento.getProveedor());
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