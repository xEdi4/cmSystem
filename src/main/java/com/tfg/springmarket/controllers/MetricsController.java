/** package com.tfg.springmarket.controllers;

import com.tfg.springmarket.utils.metrics.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MetricsController {

    @Autowired
    private MetricsService metricsService;

    @GetMapping("/metrics/top3productos/establecimiento")
    public List<Object[]> getTop3ProductosEstablecimiento(@RequestParam Long establecimientoId, @RequestBody int ano, @RequestBody int mes) {
        return metricsService.getTop3ProductosEstablecimiento(establecimientoId, ano, mes);
    }

    @GetMapping("/metrics/top3productos/proveedor")
    public List<Object[]> getTop3ProductosProveedor(@RequestParam Long proveedorId, @RequestBody int ano, @RequestBody int mes) {
        return metricsService.getTop3ProductosProveedor(proveedorId, ano, mes);
    }

    @GetMapping("/metrics/ingresos-gastos-beneficios/establecimiento")
    public Object[] getIngresosGastosBeneficiosEstablecimiento(@RequestBody int ano, @RequestBody int mes) {
        return metricsService.getIngresosGastosBeneficiosEstablecimiento(ano, mes);
    }

    @GetMapping("/metrics/ingresos/proveedor")
    public Double getIngresosProveedor(@RequestBody int ano, @RequestBody int mes) {
        return metricsService.getIngresosProveedor(ano, mes);
    }
}
 **/