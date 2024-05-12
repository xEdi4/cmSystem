package com.tfg.springmarket.exceptions;

public class EstablecimientoNotFoundException extends RuntimeException {

    public EstablecimientoNotFoundException() {
        super();
    }

    public EstablecimientoNotFoundException(String message) {
        super(message);
    }

}
