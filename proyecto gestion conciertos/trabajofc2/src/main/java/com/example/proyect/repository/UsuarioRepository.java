package com.example.proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyect.entity.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}