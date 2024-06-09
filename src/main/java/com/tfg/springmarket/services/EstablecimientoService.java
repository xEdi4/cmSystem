package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import com.tfg.springmarket.model.repositories.ProductosEstablecimientoRepository;
import com.tfg.springmarket.model.repositories.VentasEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstablecimientoService {

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private ProductosEstablecimientoRepository productosEstablecimientoRepository;

    @Autowired
    private VentasEstablecimientoRepository ventasEstablecimientoRepository;

    public Establecimiento agregarEstablecimiento(Establecimiento establecimiento) {
        return establecimientoRepository.save(establecimiento);
    }

}
