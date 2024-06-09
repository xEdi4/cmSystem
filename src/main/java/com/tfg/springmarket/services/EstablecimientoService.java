package com.tfg.springmarket.services;

import com.tfg.springmarket.model.entities.Establecimiento;
import com.tfg.springmarket.model.repositories.EstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstablecimientoService {

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    public Establecimiento agregarEstablecimiento(Establecimiento establecimiento) {
        return establecimientoRepository.save(establecimiento);
    }

    public List<Establecimiento> obtenerTodosLosEstablecimientos() {
        return establecimientoRepository.findAll();
    }

    public Establecimiento obtenerEstablecimientoPorId(Long id) {
        Optional<Establecimiento> establecimientoOptional = establecimientoRepository.findById(id);
        return establecimientoOptional.orElse(null);
    }

    public Establecimiento actualizarEstablecimiento(Long id, Establecimiento establecimiento) {
        // Verificar si el establecimiento existe
        Establecimiento establecimientoExistente = obtenerEstablecimientoPorId(id);
        if (establecimientoExistente != null) {
            establecimiento.setId(id); // Asegurarse de establecer el ID para la actualización
            return establecimientoRepository.save(establecimiento);
        } else {
            return null; // Manejar el caso donde el establecimiento no existe
        }
    }

}
