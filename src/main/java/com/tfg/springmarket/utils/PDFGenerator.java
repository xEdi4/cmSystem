package com.tfg.springmarket.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import com.tfg.springmarket.model.entities.VentasProveedor;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

public class PDFGenerator {

    public static ByteArrayOutputStream generarReporteEstablecimiento(List<VentasEstablecimiento> ventas, List<VentasProveedor> compras, LocalDate fechaInicio, LocalDate fechaFin) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Reporte de Ventas del Establecimiento"));
        document.add(new Paragraph("Desde: " + fechaInicio + " Hasta: " + fechaFin));

        // Tabla de Ventas
        Table tableVentas = new Table(new float[]{3, 3, 3});
        tableVentas.addHeaderCell("Nombre Producto");
        tableVentas.addHeaderCell("Cantidad Vendida");
        tableVentas.addHeaderCell("Beneficio");

        double totalIngresos = 0;
        double totalGastos = 0;

        for (VentasEstablecimiento venta : ventas) {
            tableVentas.addCell(venta.getProductosEstablecimiento().getNombre());
            tableVentas.addCell(venta.getCantidad().toString());
            double beneficio = (venta.getPrecioVenta() - venta.getPrecioCoste()) * venta.getCantidad();
            tableVentas.addCell(String.valueOf(beneficio));

            totalIngresos += venta.getPrecioVenta() * venta.getCantidad();
            totalGastos += venta.getPrecioCoste() * venta.getCantidad();
        }

        document.add(tableVentas);

        double totalBeneficios = totalIngresos - totalGastos;

        document.add(new Paragraph("Total Ingresos: " + totalIngresos));
        document.add(new Paragraph("Total Gastos: " + totalGastos));
        document.add(new Paragraph("Total Beneficios: " + totalBeneficios));

        // Tabla de Gastos
        document.add(new Paragraph("Detalle de Gastos"));

        Table tableGastos = new Table(new float[]{3, 3, 3});
        tableGastos.addHeaderCell("Nombre Producto");
        tableGastos.addHeaderCell("Cantidad Comprada");
        tableGastos.addHeaderCell("Precio de Coste");

        for (VentasProveedor compra : compras) {
            tableGastos.addCell(compra.getProductosProveedor().getNombre());
            tableGastos.addCell(compra.getCantidad().toString());
            tableGastos.addCell(String.valueOf(compra.getPrecioVenta())); // assuming precioVenta is the cost price here
        }

        document.add(tableGastos);

        document.close();

        return baos;
    }

    public static ByteArrayOutputStream generarReporteProveedor(List<VentasProveedor> ventas, LocalDate fechaInicio, LocalDate fechaFin) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Reporte de Ventas del Proveedor"));
        document.add(new Paragraph("Desde: " + fechaInicio + " Hasta: " + fechaFin));

        Table table = new Table(new float[]{3, 3, 3});
        table.addHeaderCell("Nombre Producto");
        table.addHeaderCell("Cantidad Vendida");
        table.addHeaderCell("Fecha Venta");

        for (VentasProveedor venta : ventas) {
            table.addCell(venta.getProductosProveedor().getNombre());
            table.addCell(venta.getCantidad().toString());
            table.addCell(venta.getFechaVenta().toString());
        }

        document.add(table);
        document.close();

        return baos;
    }
}