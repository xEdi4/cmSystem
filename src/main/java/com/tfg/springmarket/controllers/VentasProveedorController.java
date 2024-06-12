package com.tfg.springmarket.controllers;


import com.tfg.springmarket.services.VentasEstablecimientoService;
import com.tfg.springmarket.utils.metrics.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/ventas")
public class VentasProveedorController {

    @Autowired
    private MetricsService metricsService;

    @GetMapping("/{id}/top3productos")
    public List<Object[]> getTop3ProductosProveedor(@RequestParam Long proveedorId, @RequestBody int ano, @RequestBody int mes) {
        return metricsService.getTop3ProductosProveedor(proveedorId, ano, mes);
    }
    @GetMapping("/{id}/ingresos")
    public Double getIngresosProveedor(@RequestBody int ano, @RequestBody int mes) {
        return metricsService.getIngresosProveedor(ano, mes);
    }
}
