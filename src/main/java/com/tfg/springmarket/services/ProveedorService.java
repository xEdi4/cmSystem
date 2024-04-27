package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Proveedor;
import com.tfg.springmarket.exceptions.ProveedorNotFoundException;
import com.tfg.springmarket.model.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
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
