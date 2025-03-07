package com.example.proyect.controller;

import com.example.proyect.dto.ConciertoDTO;
import com.example.proyect.entity.Concierto;
import com.example.proyect.entity.EstadoConcierto;
import com.example.proyect.entity.Usuario;
import com.example.proyect.service.ConciertoService;
import com.example.proyect.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/conciertos")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ConciertoController {

    private final ConciertoService conciertoService;
    private final UsuarioService usuarioService;

    public ConciertoController(ConciertoService conciertoService, UsuarioService usuarioService) {
        this.conciertoService = conciertoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("concierto", new Concierto());
        return "crear-concierto";
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearConcierto(@RequestBody ConciertoDTO conciertoDTO, @RequestParam Long organizadorId) {
        Optional<Usuario> organizador = usuarioService.obtenerPorId(organizadorId);
        
        if (organizador.isPresent() && organizador.get().getRoles().stream().anyMatch(r -> r.getNombre().equalsIgnoreCase("organizador"))) {
            Concierto concierto = new Concierto();
            concierto.setNombre(conciertoDTO.getNombre());
            concierto.setDescripcion(conciertoDTO.getDescripcion());
            concierto.setLugar(conciertoDTO.getLugar());
            concierto.setCapacidad(conciertoDTO.getCapacidad());
            concierto.setFecha(conciertoDTO.getFecha());
            concierto.setPrecioEntrada(conciertoDTO.getPrecioEntrada());
            concierto.setOrganizador(organizador.get());
            
            EstadoConcierto estadoActivo = new EstadoConcierto();
            estadoActivo.setId(1L);
            concierto.setEstado(estadoActivo);
            
            Concierto nuevoConcierto = conciertoService.crearConcierto(concierto);
            return ResponseEntity.ok().body(Map.of("mensaje", "Concierto creado exitosamente", "conciertoId", nuevoConcierto.getId()));
        } else {
            return ResponseEntity.status(403).body("Solo los organizadores pueden crear conciertos.");
        }
    }

    @GetMapping
    public List<Concierto> obtenerTodos() {
        return conciertoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ConciertoDTO obtenerPorId(@PathVariable Long id) {
        Optional<Concierto> conciertoOptional = conciertoService.obtenerPorId(id);
        if (conciertoOptional.isPresent()) {
            return conciertoService.convertirADTO(conciertoOptional.get());
        } else {
            throw new RuntimeException("Concierto no encontrado");
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarConcierto(@PathVariable Long id, @RequestBody ConciertoDTO conciertoDTO, @RequestParam Long organizadorId) {
        Optional<Concierto> conciertoExistente = conciertoService.obtenerPorId(id);
        Optional<Usuario> organizador = usuarioService.obtenerPorId(organizadorId);
        
        if (conciertoExistente.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("mensaje", "Concierto no encontrado"));
        }
        
        if (organizador.isEmpty() || organizador.get().getRoles().stream().noneMatch(r -> r.getNombre().equalsIgnoreCase("organizador"))) {
            return ResponseEntity.status(403).body(Map.of("mensaje", "Solo los organizadores pueden actualizar conciertos"));
        }
        
        Concierto concierto = conciertoExistente.get();
        concierto.setNombre(conciertoDTO.getNombre());
        concierto.setDescripcion(conciertoDTO.getDescripcion());
        concierto.setLugar(conciertoDTO.getLugar());
        concierto.setCapacidad(conciertoDTO.getCapacidad());
        concierto.setFecha(conciertoDTO.getFecha());
        concierto.setPrecioEntrada(conciertoDTO.getPrecioEntrada());
        concierto.setOrganizador(organizador.get());
        
        Concierto conciertoActualizado = conciertoService.actualizarConcierto(concierto);
        return ResponseEntity.ok().body(Map.of(
            "mensaje", "Concierto actualizado exitosamente", 
            "conciertoId", conciertoActualizado.getId()
        ));
    }

    @DeleteMapping("/{id}")
    public void eliminarConcierto(@PathVariable Long id) {
        conciertoService.eliminarConcierto(id);
    }
}