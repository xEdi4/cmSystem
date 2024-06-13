package com.tfg.springmarket.controllers;


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

    @GetMapping("/{id}/reporte")
    public ResponseEntity<byte[]> generarReporte(@PathVariable Long id,
                                                 @RequestBody String fechaInicio,
                                                 @RequestBody String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);


        List<VentasProveedor> ventas = proveedorService.obtenerVentasPorProveedor(id, inicio, fin);

        ByteArrayOutputStream baos = PDFGenerator.generarReporteProveedor(ventas, inicio, fin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("reporte_proveedor.pdf", "reporte_proveedor.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }
    @Autowired
    private VentasProveedorRepository ventasProveedorRepository;
    @GetMapping("/proveedor/{proveedorId}/reporte")
    public ResponseEntity<byte[]> generarReporteProveedor(
            @PathVariable Long proveedorId,
            @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<VentasProveedor> ventas = ventasProveedorRepository.findByProveedorIdAndFechaVentaBetween(proveedorId, fechaInicio, fechaFin);

        ByteArrayOutputStream baos = PDFGenerator.generarReporteProveedor(ventas, fechaInicio, fechaFin);

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
