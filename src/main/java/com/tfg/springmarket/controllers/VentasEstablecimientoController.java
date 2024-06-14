package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.ProductSalesCountDTO;
import com.tfg.springmarket.dto.VentaDTO;
import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import com.tfg.springmarket.model.entities.VentasProveedor;
import com.tfg.springmarket.model.repositories.VentasEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.VentasProveedorRepository;
import com.tfg.springmarket.services.EstablecimientoService;
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
public class VentasEstablecimientoController {
    @Autowired
    private EstablecimientoService establecimientoService;
    @Autowired
    private VentasEstablecimientoService ventasEstablecimientoService;

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private VentasEstablecimientoRepository ventasEstablecimientoRepository;

    @Autowired
    private VentasProveedorRepository ventasProveedorRepository;

    @GetMapping("/establecimiento/{establecimientoId}/reporte")
    public ResponseEntity<byte[]> generarReporteEstablecimiento(
            @PathVariable Long establecimientoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        Establecimiento establecimiento = establecimientoService.obtenerEstablecimientoPorId(establecimientoId);
        List<VentasEstablecimiento> ventas = ventasEstablecimientoRepository.findByEstablecimientoIdAndFechaVentaBetween(establecimientoId, fechaInicio, fechaFin);
        List<VentasProveedor> compras = ventasProveedorRepository.findByEstablecimientoIdAndFechaVentaBetween(establecimientoId, fechaInicio, fechaFin);

        ByteArrayOutputStream baos = PDFGenerator.generarReporteEstablecimiento(establecimiento,ventas, compras, fechaInicio, fechaFin);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_establecimiento_" + establecimientoId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }

    @GetMapping("/establecimiento/{id}/top3productos")
    public List<ProductSalesCountDTO> getTop3ProductosEstablecimiento(@RequestParam Long establecimientoId, @RequestBody int ano, @RequestBody int mes) {
        return metricsService.getTop3ProductosEstablecimiento(establecimientoId, ano, mes);
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<byte[]> generarReporte(@PathVariable Long id,
                                                 @RequestParam String fechaInicio,
                                                 @RequestParam String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        Establecimiento establecimiento = establecimientoService.obtenerEstablecimientoPorId(id);
        List<VentasEstablecimiento> ventas = establecimientoService.obtenerVentasPorEstablecimiento(id, inicio, fin);
        List<VentasProveedor> compras = establecimientoService.obtenerComprasPorEstablecimiento(id, inicio, fin);

        ByteArrayOutputStream baos = PDFGenerator.generarReporteEstablecimiento(establecimiento,ventas, compras, inicio, fin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("reporte_establecimiento.pdf", "reporte_establecimiento.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
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