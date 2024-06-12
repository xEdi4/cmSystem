package com.tfg.springmarket.controllers;

import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.model.entities.VentasProveedor;
import com.tfg.springmarket.services.ProveedorService;
import com.tfg.springmarket.utils.PDFGenerator;
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
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

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

    @PostMapping
    public ResponseEntity<Proveedor> agregarProveedor(@RequestBody Proveedor proveedor) {
        Proveedor proveedorGuardado = proveedorService.agregarProveedor(proveedor);
        return ResponseEntity.ok(proveedorGuardado);
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerTodosLosProveedores() {
        List<Proveedor> proveedores = proveedorService.obtenerTodosLosProveedores();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.obtenerProveedorPorId(id);
        return ResponseEntity.ok(proveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        Proveedor proveedorActualizado = proveedorService.actualizarProveedor(id, proveedor);
        return ResponseEntity.ok(proveedorActualizado);
    }

}
