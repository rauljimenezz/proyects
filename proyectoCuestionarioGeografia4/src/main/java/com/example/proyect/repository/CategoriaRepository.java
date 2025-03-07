package com.example.proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyect.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
