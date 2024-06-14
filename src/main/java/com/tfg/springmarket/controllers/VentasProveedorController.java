package com.tfg.springmarket.controllers;


import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.model.entities.VentasProveedor;
import com.tfg.springmarket.model.repositories.VentasProveedorRepository;
import com.tfg.springmarket.services.ProveedorService;
import com.tfg.springmarket.services.VentasEstablecimientoService;
import com.tfg.springmarket.utils.PDFGenerator;
import com.tfg.springmarket.utils.metrics.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/ventas")
public class VentasProveedorController {
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private MetricsService metricsService;


    @Autowired
    private VentasProveedorRepository ventasProveedorRepository;
    @GetMapping("/proveedor/{proveedorId}/reporte")
    public ResponseEntity<byte[]> generarReporteProveedor(
            @PathVariable Long proveedorId,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        Proveedor proveedor = proveedorService.obtenerProveedorPorId(proveedorId);
        List<VentasProveedor> ventas = ventasProveedorRepository.findByProveedorIdAndFechaVentaBetween(proveedorId, inicio, fin);
        ByteArrayOutputStream baos = PDFGenerator.generarReporteProveedor(proveedor,ventas, inicio, fin);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_proveedor_" + proveedorId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }

    @GetMapping("/{id}/top3productos")
    public List<Object[]> getTop3ProductosProveedor(@RequestParam Long proveedorId, @RequestBody int ano, @RequestBody int mes) {
        return metricsService.getTop3ProductosProveedor(proveedorId, ano, mes);
    }
    @GetMapping("/{id}/ingresos")
    public Double getIngresosProveedor(@RequestBody int ano, @RequestBody int mes) {
        return metricsService.getIngresosProveedor(ano, mes);
    }
}
