package com.tfg.springmarket.services;

import com.tfg.springmarket.dto.VentaDTO;
import com.tfg.springmarket.model.entities.ProductosEstablecimiento;
import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import com.tfg.springmarket.model.repositories.ProductosEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.VentasEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentasEstablecimientoService {

    @Autowired
    private VentasEstablecimientoRepository ventaRepository;

    @Autowired
    private ProductosEstablecimientoRepository productosEstablecimientoRepository;

    @Transactional
    public String procesarVentas(List<VentaDTO> ventasDTO) {
        StringBuilder resultado = new StringBuilder();

        for (VentaDTO ventaDTO : ventasDTO) {
            ProductosEstablecimiento productosEstablecimiento = productosEstablecimientoRepository.findByIdAndActivoTrue(ventaDTO.getProductoEstablecimientoId()).orElse(null);

            if (productosEstablecimiento == null) {
                resultado.append("Producto no encontrado en el establecimiento: ").append(ventaDTO.getProductoEstablecimientoId()).append("\n");
                continue;
            }

            if (productosEstablecimiento.getStock() < ventaDTO.getCantidad()) {
                resultado.append("Stock insuficiente en el establecimiento para el producto: ").append(ventaDTO.getProductoEstablecimientoId()).append("\n");
                continue;
            }

            // Registrar la ventasEstablecimiento
            VentasEstablecimiento ventasEstablecimiento = new VentasEstablecimiento();
            ventasEstablecimiento.setEstablecimiento(productosEstablecimiento.getEstablecimiento());
            ventasEstablecimiento.setProductosEstablecimiento(productosEstablecimiento);
            ventasEstablecimiento.setCantidad(ventaDTO.getCantidad());
            ventasEstablecimiento.setPrecioVenta(productosEstablecimiento.getPrecioVenta());
            ventasEstablecimiento.setPrecioCoste(productosEstablecimiento.getPrecioCoste());
            ventasEstablecimiento.setFechaVenta(LocalDate.now());
            ventaRepository.save(ventasEstablecimiento);

            // Actualizar el stock en el establecimiento
            productosEstablecimiento.setStock(productosEstablecimiento.getStock() - ventaDTO.getCantidad());
            productosEstablecimientoRepository.save(productosEstablecimiento);

            resultado.append("Venta con id "+ventasEstablecimiento.getId() +" ha sido guardada");
        }

        return resultado.toString();
    }
}