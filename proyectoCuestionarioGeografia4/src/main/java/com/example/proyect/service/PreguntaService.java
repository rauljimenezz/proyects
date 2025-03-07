package com.example.proyect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.proyect.entity.Categoria;
import com.example.proyect.entity.Opcion;
import com.example.proyect.entity.Pregunta;
import com.example.proyect.entity.QuestionType;
import com.example.proyect.repository.CategoriaRepository;
import com.example.proyect.repository.PreguntaRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public List<Pregunta> getAllQuestions() {
        return preguntaRepository.findAll();
    }
    
    public Pregunta getQuestionById(Long id) {
        return preguntaRepository.findById(id).orElse(null);
    }

    public Page<Pregunta> getQuestionsByTypePaginated(QuestionType type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return preguntaRepository.findByType(type, pageable);
    }

    public Page<Pregunta> getQuestionsByCategoria(Long categoriaId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return preguntaRepository.findByCategoriaId(categoriaId, pageable);
    }

    public int calculateScore(Map<String, String> answers) {
        int score = 0;
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            Long questionId = Long.parseLong(entry.getKey().replace("pregunta_", ""));
            Pregunta pregunta = getQuestionById(questionId);

            if (pregunta != null) {
                if (pregunta.getType() == QuestionType.VERDADERO_FALSO) {
                    boolean respuesta = Boolean.parseBoolean(entry.getValue());
                    if (respuesta == pregunta.getOpciones().get(0).isEsCorrecta()) {
                        score++;
                    }
                } else if (pregunta.getType() == QuestionType.OPCION_UNICA) {
                    Long opcionSeleccionada = Long.parseLong(entry.getValue());
                    for (Opcion opcion : pregunta.getOpciones()) {
                        if (opcion.getId().equals(opcionSeleccionada) && opcion.isEsCorrecta()) {
                            score++;
                            break;
                        }
                    }
                } else if (pregunta.getType() == QuestionType.OPCION_MULTIPLE) {
                    String[] respuestasSeleccionadas = entry.getValue().split(",");
                    List<Long> respuestasUsuario = Arrays.stream(respuestasSeleccionadas)
                        .map(Long::parseLong)
                        .toList();
                    List<Long> opcionesCorrectas = pregunta.getOpciones().stream()
                        .filter(Opcion::isEsCorrecta)
                        .map(Opcion::getId)
                        .toList();
                    if (respuestasUsuario.containsAll(opcionesCorrectas) && opcionesCorrectas.containsAll(respuestasUsuario)) {
                        score++;
                    }
                }
            }
        }
        return score;
    }
    
    public void createQuestion(Map<String, String> params) {
        Pregunta pregunta = new Pregunta();
        pregunta.setEnunciado(params.get("enunciado"));
        pregunta.setType(QuestionType.valueOf(params.get("type")));

        try {
            Long categoriaId = Long.parseLong(params.get("categoria"));
            Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
            pregunta.setCategoria(categoria);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al asignar categoría: " + e.getMessage());
        }

        List<Opcion> opciones = new ArrayList<>();
        String[] opcionesArray = params.get("options").split(",");
        String[] correctasArray = params.get("correct").split(",");

        for (int i = 0; i < opcionesArray.length; i++) {
            Opcion opcion = new Opcion();
            opcion.setOpciones(opcionesArray[i].trim());
            opcion.setEsCorrecta(Arrays.asList(correctasArray).contains(String.valueOf(i)));
            opcion.setPregunta(pregunta);
            opciones.add(opcion);
        }

        pregunta.setOpciones(opciones);
        preguntaRepository.save(pregunta);
    }
    
    @Transactional
    public void editQuestion(Long id, Map<String, String> params) {
        Pregunta pregunta = getQuestionById(id);
        if (pregunta == null) return;

        pregunta.setEnunciado(params.get("enunciado"));
        pregunta.setType(QuestionType.valueOf(params.get("type")));

        Long categoriaId = Long.parseLong(params.get("categoria"));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        pregunta.setCategoria(categoria);

        if (params.containsKey("options") && params.containsKey("correct")) {
            String[] opcionesArray = params.get("options").split(",");
            String[] correctasArray = params.get("correct").split(",");
            List<String> correctIndices = Arrays.asList(correctasArray);

            List<Opcion> opcionesActuales = new ArrayList<>(pregunta.getOpciones());
            opcionesActuales.clear();

            for (int i = 0; i < opcionesArray.length; i++) {
                Opcion opcion = new Opcion();
                opcion.setOpciones(opcionesArray[i].trim());
                opcion.setEsCorrecta(correctIndices.contains(String.valueOf(i)));
                opcion.setPregunta(pregunta);
                opcionesActuales.add(opcion);
            }

            pregunta.getOpciones().clear();
            pregunta.getOpciones().addAll(opcionesActuales);
        }

        preguntaRepository.save(pregunta);
    }


    public void deleteQuestion(Long id) {
        preguntaRepository.deleteById(id);
    }
    
    public void importQuestionsFromCSV(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine();
            
            Categoria defaultCategoria = categoriaRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay categorías disponibles"));
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 4) continue;
                
                Pregunta pregunta = new Pregunta();
                pregunta.setEnunciado(data[0].trim());
                pregunta.setType(QuestionType.valueOf(data[1].trim()));
                pregunta.setEsMultiple(QuestionType.OPCION_MULTIPLE.toString().equals(data[1].trim()));
                pregunta.setCategoria(defaultCategoria);

                String[] opcionesArray = data[2].trim().split(";");
                String[] correctasArray = data[3].trim().split(",");
                List<Integer> indicesCorrectos = new ArrayList<>();
                for (String indice : correctasArray) {
                    indicesCorrectos.add(Integer.parseInt(indice.trim()));
                }

                List<Opcion> opciones = new ArrayList<>();
                for (int i = 0; i < opcionesArray.length; i++) {
                    Opcion opcion = new Opcion();
                    opcion.setOpciones(opcionesArray[i].trim());
                    opcion.setEsCorrecta(indicesCorrectos.contains(i));
                    opcion.setPregunta(pregunta);
                    opciones.add(opcion);
                }

                pregunta.setOpciones(opciones);
                preguntaRepository.save(pregunta);
            }
        }
    }

    public Page<Pregunta> getQuestionsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return preguntaRepository.findAll(pageable);
    }
}
