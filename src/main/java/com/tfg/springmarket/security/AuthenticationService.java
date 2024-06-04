package com.tfg.springmarket.security;

import com.tfg.springmarket.model.entities.AuthenticationResponse;
import com.tfg.springmarket.model.entities.Token;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.model.repositories.TokenRepository;
import com.tfg.springmarket.model.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthenticationResponse register(Usuario request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellidos(request.getApellidos());
        usuario.setUsuario(request.getUsuario());
        usuario.setContraseña(passwordEncoder.encode(request.getContraseña()));

        usuario.setRol(request.getRol());

        usuario = usuarioRepository.save(usuario);

        String jwt = jwtService.generateToken(usuario);

        // save the generated jwt
        saveUserToken(jwt, usuario);

        return new AuthenticationResponse(jwt);
    }

    public AuthenticationResponse authenticate(Usuario request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Usuario user = usuarioRepository.findByUsuario(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        saveUserToken(token, user);

        return new AuthenticationResponse(token);
    }

    private void saveUserToken(String jwt, Usuario usuario) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUsuario(usuario);
        tokenRepository.save(token);
    }

}
