package com.example.proyect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.proyect.dto.CompraEntradaDTO;
import com.example.proyect.entity.Concierto;
import com.example.proyect.entity.Entrada;
import com.example.proyect.entity.EstadoEntrada;
import com.example.proyect.entity.Usuario;
import com.example.proyect.service.ConciertoService;
import com.example.proyect.service.EntradaService;
import com.example.proyect.service.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/entradas")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class EntradaController {

    private final EntradaService entradaService;
    private final UsuarioService usuarioService;
    private final ConciertoService conciertoService;

    public EntradaController(EntradaService entradaService, UsuarioService usuarioService, ConciertoService conciertoService) {
        this.entradaService = entradaService;
        this.usuarioService = usuarioService;
        this.conciertoService = conciertoService;
    }

    @GetMapping
    public List<Entrada> obtenerTodas() {
        return entradaService.obtenerTodas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Entrada> entradaOpt = entradaService.obtenerPorId(id);
        if (entradaOpt.isPresent()) {
            return ResponseEntity.ok(entradaOpt.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/usuario/{userId}")
    public List<Entrada> obtenerPorUsuario(@PathVariable Long userId) {
        return entradaService.obtenerPorUsuarioId(userId);
    }

    @PostMapping("/comprar")
    public ResponseEntity<?> comprarEntrada(@RequestBody CompraEntradaDTO compraDTO) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(compraDTO.getUsuarioId());
            if (!usuarioOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "Usuario no encontrado"));
            }
            Usuario usuario = usuarioOpt.get();
            
            Optional<Concierto> conciertoOpt = conciertoService.obtenerPorId(compraDTO.getConciertoId());
            if (!conciertoOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "Concierto no encontrado"));
            }
            Concierto concierto = conciertoOpt.get();
            
            if (concierto.getCapacidad() <= 0) {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "No hay entradas disponibles para este concierto"));
            }
            
            Entrada entrada = new Entrada();
            entrada.setUsuario(usuario);
            entrada.setConcierto(concierto);
            entrada.setPrecio(compraDTO.getPrecio());
            entrada.setFechaCompra(LocalDateTime.now());
            
            entrada.setCodigoQr(UUID.randomUUID().toString());
            
            EstadoEntrada estadoActiva = new EstadoEntrada();
            estadoActiva.setId(1L);
            estadoActiva.setNombre("Activa");
            entrada.setEstado(estadoActiva);
            
            Entrada nuevaEntrada = entradaService.guardarEntrada(entrada);
            
            concierto.setCapacidad(concierto.getCapacidad() - 1);
            conciertoService.actualizarConcierto(concierto);
            
            return ResponseEntity.ok().body(Map.of(
                "mensaje", "Entrada comprada exitosamente",
                "entradaId", nuevaEntrada.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Error al comprar entrada: " + e.getMessage()));
        }
    }
    
    @PutMapping("/devolver/{id}")
    public ResponseEntity<?> devolverEntrada(@PathVariable Long id) {
        try {
            Optional<Entrada> entradaOpt = entradaService.obtenerPorId(id);
            if (!entradaOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "Entrada no encontrada"));
            }
            
            Entrada entrada = entradaOpt.get();
            Concierto concierto = entrada.getConcierto();
            
            EstadoEntrada estadoDevuelta = new EstadoEntrada();
            estadoDevuelta.setId(2L);
            estadoDevuelta.setNombre("Devuelta");
            entrada.setEstado(estadoDevuelta);
            
            entradaService.guardarEntrada(entrada);
            
            concierto.setCapacidad(concierto.getCapacidad() + 1);
            conciertoService.actualizarConcierto(concierto);
            
            return ResponseEntity.ok().body(Map.of("mensaje", "Entrada devuelta exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Error al devolver entrada: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEntrada(@PathVariable Long id) {
        try {
            entradaService.eliminarEntrada(id);
            return ResponseEntity.ok().body(Map.of("mensaje", "Entrada eliminada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Error al eliminar entrada: " + e.getMessage()));
        }
    }
}