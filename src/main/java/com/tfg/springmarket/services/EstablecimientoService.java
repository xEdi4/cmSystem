package com.tfg.springmarket.services;

import com.tfg.springmarket.dto.FechaRequestDTO;
import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.VentaEstablecimiento;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import com.tfg.springmarket.model.repositories.ProductoEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.VentaEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class EstablecimientoService {

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private ProductoEstablecimientoRepository productoEstablecimientoRepository;

    @Autowired
    private VentaEstablecimientoRepository ventaEstablecimientoRepository;

    public Establecimiento agregarEstablecimiento(Establecimiento establecimiento) {
        return establecimientoRepository.save(establecimiento);
    }

    public List<VentaEstablecimiento> obtenerVentasEntreFechasDeEstablecimiento(Long establecimientoId, FechaRequestDTO fechaRequestDTO) {
        LocalDateTime fechaInicio = LocalDateTime.of(
                fechaRequestDTO.getAñoInicio(),
                fechaRequestDTO.getMesInicio(),
                fechaRequestDTO.getDiaInicio(),
                0, 0, 0); // Hora, minuto, segundo: 0

        LocalDateTime fechaFin = LocalDateTime.of(
                fechaRequestDTO.getAñoFin(),
                fechaRequestDTO.getMesFin(),
                fechaRequestDTO.getDiaFin(),
                23, 59, 59); // Última hora del día

        // Obtener el establecimiento correspondiente al establecimientoId
        Establecimiento establecimiento = establecimientoRepository.findById(establecimientoId).orElse(null);

        if (establecimiento == null) {
            // Manejar el caso donde el establecimiento no existe
            return null;
        }

        // Formatear las fechas como cadenas en el formato adecuado
        String fechaInicioStr = fechaInicio.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String fechaFinStr = fechaFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Realizar la consulta en el repositorio utilizando las fechas formateadas y el establecimiento
        return ventaEstablecimientoRepository.findByFechaVentaBetweenAndProductoEstablecimiento_Establecimiento(
                fechaInicioStr, fechaFinStr, establecimiento);
    }

}
