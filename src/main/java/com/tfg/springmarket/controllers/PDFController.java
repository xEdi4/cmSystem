package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import com.tfg.springmarket.model.entities.VentasProveedor;
import com.tfg.springmarket.model.repositories.VentasEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.VentasProveedorRepository;
import com.tfg.springmarket.utils.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
public class PDFController {

    @Autowired
    private VentasEstablecimientoRepository ventasEstablecimientoRepository;

    @Autowired
    private VentasProveedorRepository ventasProveedorRepository;

    @GetMapping("/establecimiento/{id}/reporte")
    public ResponseEntity<byte[]> generarReporteEstablecimiento(
            @RequestParam Long establecimientoId,
            @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<VentasEstablecimiento> ventas = ventasEstablecimientoRepository.findByEstablecimientoIdAndFechaVentaBetween(establecimientoId, fechaInicio, fechaFin);
        List<VentasProveedor> compras = ventasProveedorRepository.findByEstablecimientoIdAndFechaVentaBetween(establecimientoId, fechaInicio, fechaFin);

        ByteArrayOutputStream baos = PDFGenerator.generarReporteEstablecimiento(ventas, compras, fechaInicio, fechaFin);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_establecimiento_" + establecimientoId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }

    @GetMapping("/proveedor/{id}/reporte")
    public ResponseEntity<byte[]> generarReporteProveedor(
            @RequestParam Long proveedorId,
            @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<VentasProveedor> ventas = ventasProveedorRepository.findByProveedorIdAndFechaVentaBetween(proveedorId, fechaInicio, fechaFin);

        ByteArrayOutputStream baos = PDFGenerator.generarReporteProveedor(ventas, fechaInicio, fechaFin);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_proveedor_" + proveedorId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }
}
