package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.exceptions.EstablecimientoNotFoundException;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablecimientoService {

    private final EstablecimientoRepository establecimientoRepository;

    @Autowired
    public EstablecimientoService(EstablecimientoRepository establecimientoRepository) {
        this.establecimientoRepository = establecimientoRepository;
    }

    public List<Establecimiento> getEstablecimientos() {
        return establecimientoRepository.findAll();
    }

    public Establecimiento getEstablecimiento(Long id) {
        return establecimientoRepository.findById(id)
                .orElseThrow(() -> new EstablecimientoNotFoundException("El establecimiento no se ha podido encontrar"));
    }

    public Establecimiento addEstablecimiento(Establecimiento establecimiento) {
        return establecimientoRepository.save(establecimiento);
    }

    public void deleteEstablecimiento(Long id) {
        establecimientoRepository.deleteById(id);
    }
}
