package com.tfg.springmarket.security.services;

import com.tfg.springmarket.model.entities.Token;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.model.repositories.TokenRepository;
import com.tfg.springmarket.model.repositories.UsuarioRepository;
import com.tfg.springmarket.security.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Autowired
    public AuthenticationService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, TokenRepository tokenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<?> register(Usuario request) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setTelefono(request.getTelefono());
            usuario.setCorreo(request.getCorreo());
            usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
            usuario.setRol(request.getRol());

            usuario = usuarioRepository.save(usuario);

            String jwt = jwtService.generateToken(usuario);
            saveUserToken(jwt, usuario);

            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (DataIntegrityViolationException e) {
            String errorMessage = "Ya existe una cuenta con este correo electrónico o teléfono.";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }

    public AuthenticationResponse authenticate(Usuario request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Usuario user = usuarioRepository.findByCorreo(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        revokeAllTokenByUser(user);

        saveUserToken(token, user);

        return new AuthenticationResponse(token);
    }

    private void revokeAllTokenByUser(Usuario user) {
        List<Token> validTokenListByUser = tokenRepository.findAllTokensByUsuario(user.getId());

        if (!validTokenListByUser.isEmpty()) {
            validTokenListByUser.forEach(t -> {
                t.setLoggedOut(true);
            });
        }

        tokenRepository.saveAll(validTokenListByUser);
    }

    private void saveUserToken(String jwt, Usuario usuario) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUsuario(usuario);
        tokenRepository.save(token);
    }

}