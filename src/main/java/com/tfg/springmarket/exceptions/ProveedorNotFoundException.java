package com.tfg.springmarket.exceptions;

public class ProveedorNotFoundException extends RuntimeException {

    public ProveedorNotFoundException() {
        super();
    }

    public ProveedorNotFoundException(String message) {
        super(message);
    }

}
