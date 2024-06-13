package com.tfg.springmarket.utils.metrics;

import com.tfg.springmarket.dto.ProductSalesCountDTO;
import com.tfg.springmarket.model.repositories.VentasEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.VentasProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class MetricsService {

    @Autowired
    private VentasEstablecimientoRepository ventasEstablecimientoRepository;

    @Autowired
    private VentasProveedorRepository ventasProveedorRepository;

    public List<ProductSalesCountDTO> getTop3ProductosEstablecimiento(Long establecimientoId, int ano, int mes) {

        LocalDate fechaInicio = LocalDate.of(ano, mes, 1);
        LocalDate fechaFin = YearMonth.of(ano, mes).atEndOfMonth();
        return ventasEstablecimientoRepository.findTopProductsBySalesCountBetweenDates(fechaInicio, fechaFin, PageRequest.of(0,3));
    }

    public List<Object[]> getTop3ProductosProveedor(Long proveedorId, int ano, int mes) {
        LocalDate fechaInicio = LocalDate.of(ano, mes, 1);
        LocalDate fechaFin = YearMonth.of(ano, mes).atEndOfMonth();
        return ventasProveedorRepository.findTop3ProductosByMesAndAno(fechaInicio, fechaFin);
    }

    public Object[] getIngresosGastosBeneficiosEstablecimiento(int ano, int mes) {
        LocalDate fechaInicio = LocalDate.of(ano, mes, 1);
        LocalDate fechaFin = YearMonth.of(ano, mes).atEndOfMonth();
        return ventasEstablecimientoRepository.findIngresosGastosBeneficiosByMesAndAno(fechaInicio, fechaFin);
    }

    public Double getIngresosProveedor(int ano, int mes) {
        LocalDate fechaInicio = LocalDate.of(ano, mes, 1);
        LocalDate fechaFin = YearMonth.of(ano, mes).atEndOfMonth();
        return ventasProveedorRepository.findIngresosByMesAndAno(fechaInicio, fechaFin);
    }
}