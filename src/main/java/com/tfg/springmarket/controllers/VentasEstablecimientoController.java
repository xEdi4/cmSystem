package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.ProductSalesCountDTO;
import com.tfg.springmarket.dto.VentaDTO;
import com.tfg.springmarket.services.VentasEstablecimientoService;
import com.tfg.springmarket.utils.metrics.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/ventas")
public class VentasEstablecimientoController {

    @Autowired
    private VentasEstablecimientoService ventasEstablecimientoService;

    @Autowired
    private MetricsService metricsService;

    @GetMapping("/establecimiento/{id}/top3productos")
    public List<ProductSalesCountDTO> getTop3ProductosEstablecimiento(@RequestParam Long establecimientoId, @RequestBody int ano, @RequestBody int mes) {
        return metricsService.getTop3ProductosEstablecimiento(establecimientoId, ano, mes);
    }

    @GetMapping("/establecimiento/{id}/ingresos-gastos-beneficios")
    public Object[] getIngresosGastosBeneficiosEstablecimiento(@RequestParam int ano, @RequestParam int mes) {
        return metricsService.getIngresosGastosBeneficiosEstablecimiento(ano, mes);
    }

    @PostMapping
    public ResponseEntity<String> procesarVentas(@RequestBody List<VentaDTO> ventasDTO) {
        String mensaje = ventasEstablecimientoService.procesarVentas(ventasDTO);
        return ResponseEntity.ok(mensaje);
    }
}