package com.tfg.cmsystem.services;

import com.tfg.cmsystem.entities.Proveedor;
import com.tfg.cmsystem.exceptions.ProveedorNotFoundException;
import com.tfg.cmsystem.repositories.ProveedorRepository;
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

    //No
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
