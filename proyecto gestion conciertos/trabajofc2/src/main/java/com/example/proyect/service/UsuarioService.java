package com.example.proyect.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.proyect.dto.UsuarioDTO;
import com.example.proyect.entity.Rol;
import com.example.proyect.entity.Usuario;
import com.example.proyect.repository.RolRepository;
import com.example.proyect.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContraseña(passwordEncoder.encode(usuarioDTO.getContraseña()));
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());

        Set<Rol> roles = new HashSet<>(rolRepository.findAllById(usuarioDTO.getRoles()));
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }
    
    public Usuario actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            
            // Actualizar campos básicos
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setApellidos(usuarioDTO.getApellidos());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
            
            // Actualizar contraseña solo si se proporciona una nueva
            if (usuarioDTO.getContraseña() != null && !usuarioDTO.getContraseña().isEmpty()) {
                usuario.setContraseña(passwordEncoder.encode(usuarioDTO.getContraseña()));
            }
            
            // Actualizar roles si se proporcionan
            if (usuarioDTO.getRoles() != null && !usuarioDTO.getRoles().isEmpty()) {
                Set<Rol> roles = new HashSet<>(rolRepository.findAllById(usuarioDTO.getRoles()));
                usuario.setRoles(roles);
            }
            
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    public Optional<Usuario> validarCredenciales(String email, String contraseña) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isPresent() && passwordEncoder.matches(contraseña, usuario.get().getContraseña())) {
            return usuario;
        }

        return Optional.empty();
    }
}