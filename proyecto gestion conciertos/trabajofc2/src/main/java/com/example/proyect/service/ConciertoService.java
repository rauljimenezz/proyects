package com.example.proyect.service;

import com.example.proyect.dto.ConciertoDTO;
import com.example.proyect.entity.Concierto;
import com.example.proyect.entity.EstadoConcierto;
import com.example.proyect.repository.ConciertoRepository;
import com.example.proyect.repository.EstadoConciertoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConciertoService {

	private final ConciertoRepository conciertoRepository;
    private final EstadoConciertoRepository estadoConciertoRepository;

    public ConciertoService(ConciertoRepository conciertoRepository, EstadoConciertoRepository estadoConciertoRepository) {
        this.conciertoRepository = conciertoRepository;
        this.estadoConciertoRepository = estadoConciertoRepository;
    }

    public List<Concierto> obtenerTodos() {
        return conciertoRepository.findAll();
    }

    public Optional<Concierto> obtenerPorId(Long id) {
        return conciertoRepository.findWithDetailsById(id);
    }

    public Concierto crearConcierto(Concierto concierto) {
        EstadoConcierto estadoActivo = estadoConciertoRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Estado 'activo' no encontrado"));
        concierto.setEstado(estadoActivo);
        
        return conciertoRepository.save(concierto);
    }
    
    public Concierto actualizarConcierto(Concierto concierto) {
        return conciertoRepository.save(concierto);
    }

    public void eliminarConcierto(Long id) {
        conciertoRepository.deleteById(id);
    }

    public ConciertoDTO convertirADTO(Concierto concierto) {
        ConciertoDTO dto = new ConciertoDTO();
        dto.setId(concierto.getId());
        dto.setNombre(concierto.getNombre());
        dto.setDescripcion(concierto.getDescripcion());
        dto.setLugar(concierto.getLugar());
        dto.setCapacidad(concierto.getCapacidad());
        dto.setFecha(concierto.getFecha());
        dto.setPrecioEntrada(concierto.getPrecioEntrada());

        if (concierto.getOrganizador() != null) {
            dto.setOrganizadorNombre(concierto.getOrganizador().getNombre());
        }

        if (concierto.getEstado() != null) {
            dto.setEstadoNombre(concierto.getEstado().getNombre());
        }

        return dto;
    }
}