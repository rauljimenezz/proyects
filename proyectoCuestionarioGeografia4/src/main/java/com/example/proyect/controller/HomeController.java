package com.example.proyect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.proyect.service.PreguntaService;

@Controller
public class HomeController {
	
	@Autowired
    private PreguntaService preguntaService;

	@GetMapping({"", " ", "/", "/home"})
	public String home(Model model) {
		model.addAttribute("titulo", "Cuestionario");
		model.addAttribute("titular", "Bienvenido a este cuestionario de geografia");
		return "home";
	}
	
	@PostMapping("/upload")
    public String handleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Por favor seleccione un archivo para subir.");
            return "redirect:/home";
        }

        try {
            preguntaService.importQuestionsFromCSV(file);
            redirectAttributes.addFlashAttribute("message", "Archivo subido correctamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar el archivo: " + e.getMessage());
        }

        return "redirect:/home";
    }
}
