package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EstablecimientoService {

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    public Establecimiento agregarEstablecimiento(Establecimiento establecimiento) {
        return establecimientoRepository.save(establecimiento);
    }


}
