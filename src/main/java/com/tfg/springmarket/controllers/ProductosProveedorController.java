package com.tfg.springmarket.controllers;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.tfg.springmarket.model.entities.ProductosProveedor;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.model.entities.VentasProveedor;
import com.tfg.springmarket.model.repositories.VentasProveedorRepository;
import com.tfg.springmarket.services.ProductosProveedorService;
import com.tfg.springmarket.utils.PDFGenerator;
import com.tfg.springmarket.utils.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/proveedores")
public class ProductosProveedorController {

    private static final Logger logger = LoggerFactory.getLogger(ProductosProveedorController.class);

    @Autowired
    private ProductosProveedorService productosProveedorService;

    @Autowired
    private VentasProveedorRepository ventasProveedorRepository;

    @Autowired
    private MetricsService metricsService;
    @PostMapping("/productos")
    public ResponseEntity<List<ProductosProveedor>> agregarProductosProveedor(@RequestParam("file") MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Usuario usuario = (Usuario) authentication.getPrincipal();
                Long proveedorId = usuario.getId();

                List<ProductosProveedor> productosDelArchivo = productosProveedorService.parsearArchivoJSON(file);
                return ResponseEntity.ok(productosProveedorService.agregarProductosProveedor(proveedorId, productosDelArchivo));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (IOException e) {
            logger.error("Error al procesar el archivo JSON", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductosProveedor>> obtenerTodosLosProductosProveedor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            List<ProductosProveedor> productosProveedor = productosProveedorService.obtenerTodosLosProductosProveedor(proveedorId);
            return ResponseEntity.ok(productosProveedor);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/allProductos")
    public ResponseEntity<List<ProductosProveedor>> obtenerTodosLosProductos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            List<ProductosProveedor> productosProveedor = productosProveedorService.obtenerTodosLosProductos();
            return ResponseEntity.ok(productosProveedor);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductosProveedor> actualizarProductoProveedor(@PathVariable Long id, @RequestBody ProductosProveedor productoProveedor) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            ProductosProveedor productoProveedorActualizado = productosProveedorService.actualizarProductoProveedor(proveedorId, id, productoProveedor);
            return ResponseEntity.ok(productoProveedorActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminarProductoProveedor(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            productosProveedorService.eliminarProductoProveedor(proveedorId, id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/reporte")
    public ResponseEntity<byte[]> generarReporteProveedor(
            Authentication authentication,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        // Verificar autenticación y obtener usuario
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
            LocalDate fin = LocalDate.parse(fechaFin, formatter);

            // Obtener ventas del proveedor
            List<VentasProveedor> ventas = ventasProveedorRepository.findAllByUsuarioIdAndFechaVentaBetween(proveedorId, inicio, fin);
            ByteArrayOutputStream baos = (ByteArrayOutputStream) PDFGenerator.generarReporteProveedor(usuario, ventas, inicio, fin);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_proveedor_" + proveedorId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/top3productos")
    public ResponseEntity<List<Object[]>> getTop3ProductosProveedor(
            Authentication authentication,
            @RequestParam int ano,
            @RequestParam int mes) {

        // Verificar autenticación y obtener usuario
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            List<Object[]> top3Productos = metricsService.getTop3ProductosProveedor(proveedorId, ano, mes);
            return ResponseEntity.ok().body(top3Productos);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/ingresos")
    public ResponseEntity<Double> getIngresosProveedor(
            Authentication authentication,
            @RequestParam int ano,
            @RequestParam int mes) {

        // Verificar autenticación y obtener usuario
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            Long proveedorId = usuario.getId();

            Double ingresos = metricsService.getIngresosProveedor(proveedorId, ano, mes);
            return ResponseEntity.ok().body(ingresos);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}