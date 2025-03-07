package com.example.proyect.repository;

import com.example.proyect.entity.Pregunta;
import com.example.proyect.entity.QuestionType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    Page<Pregunta> findByType(QuestionType type, Pageable pageable);
    
    Page<Pregunta> findByCategoriaId(Long categoriaId, Pageable pageable);
}
