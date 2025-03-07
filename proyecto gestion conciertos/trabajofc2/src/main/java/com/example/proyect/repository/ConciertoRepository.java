package com.example.proyect.repository;

import com.example.proyect.entity.Concierto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConciertoRepository extends JpaRepository<Concierto, Long> {

    @EntityGraph(attributePaths = {"organizador", "estado"})
    Optional<Concierto> findWithDetailsById(Long id);
}