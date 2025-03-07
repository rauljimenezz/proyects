package com.example.proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyect.entity.Entrada;

import java.util.List;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    long countByConciertoId(Long conciertoId);
    List<Entrada> findByUsuarioId(Long usuarioId);
}
