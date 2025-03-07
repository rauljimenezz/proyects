package com.example.proyect.service;

import org.springframework.stereotype.Service;

import com.example.proyect.entity.Entrada;
import com.example.proyect.repository.EntradaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;

    public EntradaService(EntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    public List<Entrada> obtenerTodas() {
        return entradaRepository.findAll();
    }
    
    public Optional<Entrada> obtenerPorId(Long id) {
        return entradaRepository.findById(id);
    }
    
    public List<Entrada> obtenerPorUsuarioId(Long usuarioId) {
        return entradaRepository.findByUsuarioId(usuarioId);
    }

    public Entrada guardarEntrada(Entrada entrada) {
        return entradaRepository.save(entrada);
    }
    
    public void eliminarEntrada(Long id) {
        entradaRepository.deleteById(id);
    }
}