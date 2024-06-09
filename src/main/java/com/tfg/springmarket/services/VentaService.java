package com.tfg.springmarket.services;

import com.tfg.springmarket.dto.VentaDTO;
import com.tfg.springmarket.model.entities.ProductoEstablecimiento;
import com.tfg.springmarket.model.entities.VentaEstablecimiento;
import com.tfg.springmarket.model.repositories.ProductoEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.VentaEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private ProductoEstablecimientoRepository productoEstablecimientoRepository;

    @Autowired
    private VentaEstablecimientoRepository ventaRepository;

    @Transactional
    public String procesarVentas(List<VentaDTO> ventasDTO) {
        StringBuilder resultado = new StringBuilder();

        for (VentaDTO ventaDTO : ventasDTO) {
            ProductoEstablecimiento productoEstablecimiento = productoEstablecimientoRepository.findById(ventaDTO.getProductoEstablecimientoId()).orElse(null);

            if (productoEstablecimiento == null) {
                resultado.append("Producto no encontrado en el establecimiento: ").append(ventaDTO.getProductoEstablecimientoId()).append("\n");
                continue;
            }

            if (productoEstablecimiento.getStock() < ventaDTO.getCantidad()) {
                resultado.append("Stock insuficiente en el establecimiento para el producto: ").append(ventaDTO.getProductoEstablecimientoId()).append("\n");
                continue;
            }

            // Registrar la ventaEstablecimiento
            VentaEstablecimiento ventaEstablecimiento = new VentaEstablecimiento();
            ventaEstablecimiento.setProductoEstablecimiento(productoEstablecimiento);
            ventaEstablecimiento.setCantidad(ventaDTO.getCantidad());
            ventaEstablecimiento.setPrecioVenta(productoEstablecimiento.getPrecioVenta());
            ventaEstablecimiento.setPrecioCoste(productoEstablecimiento.getPrecioCoste());
            ventaEstablecimiento.setFechaVenta(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ventaRepository.save(ventaEstablecimiento);

            // Actualizar el stock en el establecimiento
            productoEstablecimiento.setStock(productoEstablecimiento.getStock() - ventaDTO.getCantidad());
            productoEstablecimientoRepository.save(productoEstablecimiento);

            resultado.append("Venta realizada con éxito para el producto: ").append(ventaDTO.getProductoEstablecimientoId()).append("\n");
        }

        return resultado.toString();
    }
}