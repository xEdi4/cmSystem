package com.tfg.springmarket.controllers;

import com.tfg.springmarket.dto.CambiarContrasenaRequest;
import com.tfg.springmarket.dto.UsuarioDTO;
import com.tfg.springmarket.model.entities.Rol;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Esto permite solicitudes CORS desde cualquier origen
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/proveedores")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuariosConRolProveedor() {
        List<UsuarioDTO> proveedores = usuarioService.obtenerUsuariosPorRol(Rol.PROVEEDOR);
        return ResponseEntity.ok(proveedores);
    }

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

    @PutMapping("/miCuenta/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            if (usuario.getId().equals(id)) {
                UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioDTO);
                return ResponseEntity.ok(usuarioActualizado);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // El usuario no tiene permiso para actualizar este ID
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/cambiarContrasena")
    public ResponseEntity<String> cambiarContrasena(@RequestBody CambiarContrasenaRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();

            boolean cambioExitoso = usuarioService.cambiarContrasena(request.getContrasenaActual(), request.getNuevaContrasena(), usuario);
            if (cambioExitoso) {
                return ResponseEntity.ok("Contraseña cambiada correctamente");
            } else {
                return ResponseEntity.badRequest().body("No se pudo cambiar la contraseña. Verifica los datos ingresados.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
