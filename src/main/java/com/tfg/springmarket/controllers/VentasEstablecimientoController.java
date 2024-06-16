package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.ProductSalesCountDTO;
import com.tfg.springmarket.dto.VentaDTO;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import com.tfg.springmarket.model.entities.VentasProveedor;
import com.tfg.springmarket.services.VentasEstablecimientoService;
import com.tfg.springmarket.utils.PDFGenerator;
import com.tfg.springmarket.utils.metrics.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/establecimientos/ventas")
public class VentasEstablecimientoController {

    @Autowired
    private VentasEstablecimientoService ventasEstablecimientoService;
    @Autowired
    private MetricsService metricsService;

    @PostMapping
    public ResponseEntity<String> procesarVentas(@RequestBody List<VentaDTO> ventasDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long establecimientoId = usuario.getId();

            // Verificar que los productos pertenecen al proveedor (usuario autenticado)
            boolean productosPertenecenAlProveedor = ventasEstablecimientoService.verificarProductosPertenecenAlProveedor(establecimientoId, ventasDTO);

            if (!productosPertenecenAlProveedor) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Uno o más productos no pertenecen al proveedor.");
            }

            String mensaje = ventasEstablecimientoService.procesarVentas(ventasDTO);
            return ResponseEntity.ok(mensaje);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/reporte")
    public ResponseEntity<byte[]> generarReporteEstablecimiento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long usuarioId = usuario.getId();

            List<VentasEstablecimiento> ventas = metricsService.getVentasEstablecimientoByUsuarioAndFecha(usuarioId, fechaInicio, fechaFin);
            List<VentasProveedor> compras = metricsService.getComprasProveedorByUsuarioAndFecha(usuarioId, fechaInicio, fechaFin);

            ByteArrayOutputStream baos = PDFGenerator.generarReporteEstablecimiento(usuario, ventas, compras, fechaInicio, fechaFin);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_establecimiento_" + usuarioId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());
        }

        // Si no se puede obtener el usuario autenticado, se podría devolver un ResponseEntity con un error 403 o similar
        return ResponseEntity.status(403).body(null);
    }

    @GetMapping("/{id}/top3productos")
    public List<ProductSalesCountDTO> getTop3ProductosEstablecimiento(
            @PathVariable Long id,
            @RequestParam int ano,
            @RequestParam int mes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long usuarioId = usuario.getId();

            return metricsService.getTop3ProductosEstablecimiento(usuarioId, ano, mes);
        }

        // Si no se puede obtener el usuario autenticado, se podría devolver un ResponseEntity con un error 403 o similar
        return null;
    }

    @GetMapping("/{id}/ingresos-gastos-beneficios")
    public Object[] getIngresosGastosBeneficiosEstablecimiento(
            @PathVariable Long id,
            @RequestParam int ano,
            @RequestParam int mes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long usuarioId = usuario.getId();

            return metricsService.getIngresosGastosBeneficiosEstablecimiento(usuarioId, ano, mes);
        }

        // Si no se puede obtener el usuario autenticado, se podría devolver un ResponseEntity con un error 403 o similar
        return null;
    }
}