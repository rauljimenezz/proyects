package com.example.proyect.repository;

import com.example.proyect.entity.EstadoConcierto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoConciertoRepository extends JpaRepository<EstadoConcierto, Long> {
    EstadoConcierto findByNombre(String nombre);
}