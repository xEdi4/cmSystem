package com.tfg.springmarket.services;

import com.tfg.springmarket.dto.VentaDTO;
import com.tfg.springmarket.model.entities.ProductosEstablecimiento;
import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import com.tfg.springmarket.model.repositories.ProductosEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.VentasEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            ProductosEstablecimiento productosEstablecimiento = productosEstablecimientoRepository.findById(ventaDTO.getProductoEstablecimientoId()).orElse(null);

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
            ventasEstablecimiento.setProductosEstablecimiento(productosEstablecimiento);
            ventasEstablecimiento.setCantidad(ventaDTO.getCantidad());
            ventasEstablecimiento.setPrecioVenta(productosEstablecimiento.getPrecioVenta());
            ventasEstablecimiento.setPrecioCoste(productosEstablecimiento.getPrecioCoste());
            ventasEstablecimiento.setFechaVenta(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ventaRepository.save(ventasEstablecimiento);

            // Actualizar el stock en el establecimiento
            productosEstablecimiento.setStock(productosEstablecimiento.getStock() - ventaDTO.getCantidad());
            productosEstablecimientoRepository.save(productosEstablecimiento);
        }

        return resultado.toString();
    }
}