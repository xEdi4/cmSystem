package com.tfg.springmarket.model.entities;

import lombok.Getter;

@Getter
public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

}
