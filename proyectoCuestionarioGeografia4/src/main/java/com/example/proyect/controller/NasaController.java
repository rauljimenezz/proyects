package com.example.proyect.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController

@RequestMapping("/nasa")

public class NasaController {

	@Value("${nasa.api.key}")
	private String nasaApiKey;

	@GetMapping("/apod")
	public ResponseEntity<?> getAstronomyPictureOfTheDay() {
		String url = "https://api.nasa.gov/planetary/apod?api_key=" + nasaApiKey;
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return ResponseEntity.ok(response.getBody());
		} catch (Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener datos de la NASA");
		}

	}
}