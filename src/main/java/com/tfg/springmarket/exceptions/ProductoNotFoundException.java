package com.tfg.springmarket.exceptions;

public class ProductoNotFoundException extends RuntimeException {

    public ProductoNotFoundException() {
        super();
    }

    public ProductoNotFoundException(String message) {
        super(message);
    }

}
