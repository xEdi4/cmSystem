package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.entities.Producto;
import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.exceptions.ProveedorNotFoundException;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import com.tfg.springmarket.model.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final EstablecimientoRepository establecimientoRepository;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository, EstablecimientoRepository establecimientoRepository) {
        this.proveedorRepository = proveedorRepository;
        this.establecimientoRepository = establecimientoRepository;
    }

    public List<Proveedor> getProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor getProveedor(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() ->
                        new ProveedorNotFoundException("El proveedor no se ha podido encontrar"));
    }

    public Proveedor addProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void deleteProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    public Producto addProducto(Long proveedorId, Producto producto) {
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado"));

        producto.setProveedor(proveedor);
        Producto savedProduct = proveedorRepository.save(producto); // Guarda el producto asociado al proveedor

        // Agregar el producto al establecimiento
        Establecimiento establecimiento = proveedor.getEstablecimiento();
        if (establecimiento != null) {
            producto.setEstablecimiento(establecimiento);
            establecimiento.getProductos().add(producto);
            establecimientoRepository.save(establecimiento);
        }

        return savedProduct;
    }

}
