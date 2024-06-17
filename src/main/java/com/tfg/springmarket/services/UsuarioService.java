package com.tfg.springmarket.services;

import com.tfg.springmarket.dto.UsuarioDTO;
import com.tfg.springmarket.model.entities.Rol;
import com.tfg.springmarket.model.entities.Usuario;
import com.tfg.springmarket.model.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> obtenerUsuariosPorRol(Rol rol) {
        List<Usuario> usuarios = usuarioRepository.findByRol(rol);
        return usuarios.stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public boolean cambiarContrasena(String contrasenaActual, String nuevaContrasena, Usuario usuario) {
        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(contrasenaActual, usuario.getContrasena())) {
            return false; // Contraseña actual incorrecta
        }

        // Actualizar la contraseña
        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);
        return true;
    }


    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")); // Maneja esto adecuadamente según tu lógica de negocio

        // Actualizar datos del usuario
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setCorreo(usuarioDTO.getCorreo());

        // Guardar cambios en la base de datos
        usuarioRepository.save(usuario);

        // Convertir y devolver el DTO actualizado
        return convertirADto(usuario);
    }

    public UsuarioDTO convertirADto(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setTelefono(usuario.getTelefono());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol());
        return dto;
    }

}
