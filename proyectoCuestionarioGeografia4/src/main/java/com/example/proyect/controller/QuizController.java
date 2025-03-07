package com.example.proyect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.proyect.entity.Pregunta;
import com.example.proyect.service.PreguntaService;

import java.util.Map;

@Controller
public class QuizController {
    
    @Autowired
    private PreguntaService preguntaService;
    
    @GetMapping("/quiz")
    public String mostrarCuestionario(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "7") int size,
        Model model
    ) {
        Page<Pregunta> preguntasPage = preguntaService.getQuestionsPaginated(page, size);
        
        model.addAttribute("questions", preguntasPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", preguntasPage.getTotalPages());
        model.addAttribute("totalQuestions", preguntasPage.getTotalElements());
        return "cuestionario";
    }
    
    @PostMapping("/submit")
    public String enviarCuestionario(@RequestParam Map<String, String> answers, Model model) {
        int score = preguntaService.calculateScore(answers);
        model.addAttribute("score", score);
        return "resultado";
    }
}