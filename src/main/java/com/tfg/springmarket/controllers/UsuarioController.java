package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.UsuarioDTO;
import com.tfg.springmarket.model.entities.Rol;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/miCuenta")
    public ResponseEntity<UsuarioDTO> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            UsuarioDTO usuarioDTO = usuarioService.convertirADto(usuario);
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/proveedores")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuariosConRolProveedor() {
        List<UsuarioDTO> proveedores = usuarioService.obtenerUsuariosPorRol(Rol.PROVEEDOR);
        return ResponseEntity.ok(proveedores);
    }

}
