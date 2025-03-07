package com.example.proyect.controller;

import com.example.proyect.dto.LoginDTO;
import com.example.proyect.entity.Rol;
import com.example.proyect.entity.Usuario;
import com.example.proyect.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<Usuario> usuario = usuarioService.validarCredenciales(loginDTO.getEmail(), loginDTO.getContraseña());

        if (usuario.isPresent()) {
            Usuario usuarioEncontrado = usuario.get();
            
            String rol = usuarioEncontrado.getRoles().stream()
                             .findFirst()
                             .map(Rol::getNombre)
                             .orElse("asistente");
            
            return ResponseEntity.ok(Map.of(
                "mensaje", "Inicio de sesión exitoso",
                "rol", rol,
                "id", usuarioEncontrado.getId()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of(
                "mensaje", "Credenciales incorrectas"
            ));
        }
    }

}
