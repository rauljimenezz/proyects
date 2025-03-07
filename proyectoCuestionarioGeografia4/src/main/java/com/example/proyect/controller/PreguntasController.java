package com.example.proyect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.proyect.entity.Opcion;
import com.example.proyect.entity.Pregunta;
import com.example.proyect.entity.QuestionType;
import com.example.proyect.repository.CategoriaRepository;
import com.example.proyect.service.PreguntaService;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/preguntas")
public class PreguntasController {
    @Autowired
    private PreguntaService questionService;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @GetMapping("/{type}")
    public String listarPreguntas(
        @PathVariable QuestionType type,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(value = "categoria", required = false) Long categoriaId,
        Model model
    ) {
        Page<Pregunta> preguntasPage;
        if (categoriaId != null) {
            preguntasPage = questionService.getQuestionsByCategoria(categoriaId, page, size);
        } else {
            preguntasPage = questionService.getQuestionsByTypePaginated(type, page, size);
        }

        model.addAttribute("preguntas", preguntasPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", preguntasPage.getTotalPages());
        model.addAttribute("questionType", type);
        model.addAttribute("typeTitle", getTypeTitle(type.name()));
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "preguntas";
    }

    @PostMapping("/create")
    public String crearPregunta(@RequestParam Map<String, String> params) {
        questionService.createQuestion(params);
        return "redirect:/preguntas/" + params.get("type");
    }

    @GetMapping("/edit/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Pregunta pregunta = questionService.getQuestionById(id);
        if (pregunta == null) {
            return "redirect:/preguntas";
        }

        String optionsString = pregunta.getOpciones().stream()
            .map(Opcion::getOpciones)
            .collect(Collectors.joining(","));
        
        String correctString = IntStream.range(0, pregunta.getOpciones().size())
            .filter(i -> pregunta.getOpciones().get(i).isEsCorrecta())
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(","));

        model.addAttribute("pregunta", pregunta);
        model.addAttribute("questionType", pregunta.getType());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("optionsString", optionsString);
        model.addAttribute("correctString", correctString);
        return "editar-pregunta";
    }
    
    @PostMapping("/edit/{id}")
    public String editarPregunta(@PathVariable Long id, @RequestParam Map<String, String> params) {
        questionService.editQuestion(id, params);
        Pregunta pregunta = questionService.getQuestionById(id);
        return "redirect:/preguntas/" + pregunta.getType();
    }
            
    @GetMapping("/delete/{id}")
    public String eliminarPreguntas(@PathVariable Long id) {
        Pregunta pregunta = questionService.getQuestionById(id);
        String tipo = pregunta.getType().toString();
        questionService.deleteQuestion(id);
        return "redirect:/preguntas/" + tipo;
    }

    private String getTypeTitle(String type) {
        switch(type) {
            case "VERDADERO_FALSO": return "Preguntas de verdadero/falso";
            case "OPCION_UNICA": return "Preguntas de opcion unica";
            case "OPCION_MULTIPLE": return "Preguntas de opcion multiple";
            default: return "Preguntas";
        }
    }
}
