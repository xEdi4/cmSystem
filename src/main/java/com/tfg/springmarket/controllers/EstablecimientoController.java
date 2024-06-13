package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import com.tfg.springmarket.model.entities.VentasProveedor;
import com.tfg.springmarket.services.EstablecimientoService;
import com.tfg.springmarket.utils.PDFGenerator;
import com.tfg.springmarket.utils.metrics.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/establecimientos")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @Autowired
    private MetricsService metricsService;

    @GetMapping("/{id}/top3productos")
    public List<Object[]> getTop3ProductosEstablecimiento(@RequestParam Long establecimientoId, @RequestBody int ano, @RequestBody int mes) {
        return metricsService.getTop3ProductosEstablecimiento(establecimientoId, ano, mes);
    }

    @GetMapping("/{id}/ingresos-gastos-beneficios")
    public Object[] getIngresosGastosBeneficiosEstablecimiento(@RequestBody int ano, @RequestBody int mes) {
        return metricsService.getIngresosGastosBeneficiosEstablecimiento(ano, mes);
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<byte[]> generarReporte(@PathVariable Long id,
                                                 @RequestBody String fechaInicio,
                                                 @RequestBody String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        List<VentasEstablecimiento> ventas = establecimientoService.obtenerVentasPorEstablecimiento(id, inicio, fin);
        List<VentasProveedor> compras = establecimientoService.obtenerComprasPorEstablecimiento(id, inicio, fin);

        ByteArrayOutputStream baos = PDFGenerator.generarReporteEstablecimiento(ventas, compras, inicio, fin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("reporte_establecimiento.pdf", "reporte_establecimiento.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }

    @PostMapping
    public ResponseEntity<Establecimiento> agregarEstablecimiento(@RequestBody Establecimiento establecimiento) {
        Establecimiento establecimientoGuardado = establecimientoService.agregarEstablecimiento(establecimiento);
        return ResponseEntity.ok(establecimientoGuardado);
    }

    @GetMapping
    public ResponseEntity<List<Establecimiento>> obtenerTodosLosEstablecimientos() {
        List<Establecimiento> establecimientos = establecimientoService.obtenerTodosLosEstablecimientos();
        return ResponseEntity.ok(establecimientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> obtenerEstablecimientoPorId(@PathVariable Long id) {
        Establecimiento establecimiento = establecimientoService.obtenerEstablecimientoPorId(id);
        if (establecimiento != null) {
            return ResponseEntity.ok(establecimiento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Establecimiento> actualizarEstablecimiento(@PathVariable Long id, @RequestBody Establecimiento establecimiento) {
        Establecimiento establecimientoActualizado = establecimientoService.actualizarEstablecimiento(id, establecimiento);
        return ResponseEntity.ok(establecimientoActualizado);
    }
}