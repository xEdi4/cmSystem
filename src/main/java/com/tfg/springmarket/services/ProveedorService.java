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

}
