package com.tfg.springmarket.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.model.entities.VentasEstablecimiento;
import com.tfg.springmarket.model.entities.VentasProveedor;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

public class PDFGenerator {

    public static ByteArrayOutputStream generarReporteEstablecimiento(Establecimiento establecimiento,List<VentasEstablecimiento> ventas, List<VentasProveedor> compras, LocalDate fechaInicio, LocalDate fechaFin) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Título en negrita
        Paragraph title = new Paragraph("Reporte de Ventas del Establecimiento")
                .setBold()
                .setFontSize(16);
        document.add(title);

        Paragraph infoCabecera = new Paragraph()
                .add("Establecimiento: " + establecimiento.getNombre() + "\n")
                .add("Teléfono: " + establecimiento.getTelefono() + "\n")
                .add("Email: " +  establecimiento.getEmail() )
                .setFontSize(10)
                .setMarginBottom(10);
        document.add(infoCabecera);


        // Fecha del reporte
        Paragraph fecha = new Paragraph("Fecha del Reporte: " + LocalDate.now())
                .setMarginTop(10)
                .setMarginBottom(20);
        document.add(fecha);

        // Detalle de Gastos
        document.add(new Paragraph("Detalle de Gastos"));

        Table tableGastos = new Table(new float[]{3, 3, 3});
        tableGastos.addHeaderCell("Nombre Producto");
        tableGastos.addHeaderCell("Cantidad Comprada");
        tableGastos.addHeaderCell("Precio de Coste");

        for (VentasProveedor compra : compras) {
            tableGastos.addCell(compra.getProductosProveedor().getNombre());
            tableGastos.addCell(compra.getCantidad().toString());
            tableGastos.addCell(String.format("%.2f", compra.getPrecioVenta())); // assuming precioVenta is the cost price here
        }

        document.add(tableGastos);

        // Espacio entre secciones
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Detalle de Ventas"));
        // Reporte de Ventas del Establecimiento
        Table tableVentas = new Table(new float[]{3, 3});
        tableVentas.addHeaderCell("Nombre Producto");
        tableVentas.addHeaderCell("Precio de Venta");

        double totalIngresos = 0;
        double totalGastos = 0;

        for (VentasEstablecimiento venta : ventas) {
            tableVentas.addCell(venta.getProductosEstablecimiento().getNombre());
            tableVentas.addCell(String.format("%.2f", venta.getPrecioVenta()));

            totalIngresos += venta.getPrecioVenta() * venta.getCantidad();
            totalGastos += venta.getPrecioCoste() * venta.getCantidad();
        }

        document.add(tableVentas);

        double totalBeneficios = totalIngresos - totalGastos;

        // Totales de ingresos, gastos y beneficios
        Paragraph totalesEstablecimiento = new Paragraph()
                .add("Total Ingresos: " + String.format("%.2f", totalIngresos) + "\n")
                .add("Total Gastos: " + String.format("%.2f", totalGastos) + "\n")
                .add("Total Beneficios: " + String.format("%.2f", totalBeneficios))
                .setMarginTop(20);
        document.add(totalesEstablecimiento);

        document.close();

        return baos;
    }

    public static ByteArrayOutputStream generarReporteProveedor(Proveedor proveedor,List<VentasProveedor> ventas, LocalDate fechaInicio, LocalDate fechaFin) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        // Título en negrita
        Paragraph title = new Paragraph("Reporte de Ventas del Proveedor")
                .setBold()
                .setFontSize(16);
        document.add(title);

        Paragraph infoCabecera = new Paragraph()
                .add("Nombre: " + proveedor.getNombre() + "\n")
                .add("Teléfono:" + proveedor.getTelefono() + "\n")
                .add("Email: " + proveedor.getEmail())
                .setFontSize(10)
                .setMarginBottom(10);
        document.add(infoCabecera);

        // Fecha del reporte
        Paragraph fecha = new Paragraph("Fecha del Reporte: " + LocalDate.now())
                .setMarginTop(10)
                .setMarginBottom(20);
        document.add(fecha);

        Table table = new Table(new float[]{3, 3, 3});
        table.addHeaderCell("Nombre Producto");
        table.addHeaderCell("Cantidad Vendida");
        table.addHeaderCell("Fecha Venta");

        double totalIngresosProveedor = 0;

        for (VentasProveedor venta : ventas) {
            table.addCell(venta.getProductosProveedor().getNombre());
            table.addCell(venta.getCantidad().toString());
            table.addCell(venta.getFechaVenta().toString());

            totalIngresosProveedor += venta.getPrecioVenta() * venta.getCantidad();
        }

        document.add(table);

        // Total de ingresos del proveedor
        Paragraph totalIngresos = new Paragraph("Total Ingresos: " + String.format("%.2f", totalIngresosProveedor))
                .setMarginTop(20);
        document.add(totalIngresos);

        document.close();

        return baos;
    }
}