INSERT INTO categoria (nombre) VALUES ('Países');
INSERT INTO categoria (nombre) VALUES ('Capitales');
INSERT INTO categoria (nombre) VALUES ('Continentes');

UPDATE pregunta SET categoria_id = 2 WHERE id = 1;
UPDATE pregunta SET categoria_id = 3 WHERE id = 2;
UPDATE pregunta SET categoria_id = 3 WHERE id = 3;
UPDATE pregunta SET categoria_id = 2 WHERE id = 4;
UPDATE pregunta SET categoria_id = 1 WHERE id = 5;
UPDATE pregunta SET categoria_id = 3 WHERE id = 6;
UPDATE pregunta SET categoria_id = 1 WHERE id = 7;
UPDATE pregunta SET categoria_id = 1 WHERE id = 8;

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('Madrid es la capital de España', 'VERDADERO_FALSO', false, 2);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('El río Nilo es el más largo del mundo', 'VERDADERO_FALSO', false, 3);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('Australia es el continente más pequeño', 'VERDADERO_FALSO', false, 3);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿Cuál es la capital de Canadá?', 'OPCION_UNICA', false, 2);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿Cuál es el país más grande del mundo por territorio?', 'OPCION_UNICA', false, 1);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿En qué continente se encuentra el monte Everest?', 'OPCION_UNICA', false, 3);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿Qué países pertenecen a África?', 'OPCION_MULTIPLE', true, 1);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿Cuáles de estos países forman parte de la Unión Europea?', 'OPCION_MULTIPLE', true, 2);


INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Verdadero', true, 1);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Falso', false, 1);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Verdadero', false, 2);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Falso', true, 2);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Verdadero', true, 3);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Falso', false, 3);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Ottawa', true, 4);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Toronto', false, 4);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Vancouver', false, 4);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Montreal', false, 4);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Rusia', true, 5);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('China', false, 5);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Estados Unidos', false, 5);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Canadá', false, 5);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Asia', true, 6);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Europa', false, 6);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('África', false, 6);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('América', false, 6);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Uganda', true, 7);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Arabia Saudita', false, 7);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Argelia', true, 7);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Chile', false, 7);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Francia', true, 8);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('España', true, 8);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('Noruega', false, 8);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) 
VALUES ('China', false, 8);


INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('Brasil es el país más grande de Sudamérica', 'VERDADERO_FALSO', false, 1);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿Cuál es el país más poblado del mundo?', 'OPCION_UNICA', false, 1);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿Cuáles de estos países pertenecen a América del Sur?', 'OPCION_MULTIPLE', true, 1);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('México es parte de América del Sur', 'VERDADERO_FALSO', false, 1);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Verdadero', true, 9);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Falso', false, 9);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('China', true, 10);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('India', false, 10);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Estados Unidos', false, 10);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Brasil', false, 10);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Argentina', true, 11);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Perú', true, 11);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('España', false, 11);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Colombia', true, 11);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Verdadero', false, 12);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Falso', true, 12);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿Cuál es la capital de Australia?', 'OPCION_UNICA', false, 2);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('París es la capital de Italia', 'VERDADERO_FALSO', false, 2);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿Cuál de estas ciudades es la capital de un país europeo?', 'OPCION_MULTIPLE', true, 2);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('Tokio es la capital de China', 'VERDADERO_FALSO', false, 2);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Canberra', true, 13);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Sídney', false, 13);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Melbourne', false, 13);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Brisbane', false, 13);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Verdadero', false, 14);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Falso', true, 14);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Lisboa', true, 15);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Berlín', true, 15);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Los Ángeles', false, 15);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Buenos Aires', false, 15);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Verdadero', false, 16);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Falso', true, 16);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿En qué continente está Egipto?', 'OPCION_UNICA', false, 3);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('El continente más frío es África', 'VERDADERO_FALSO', false, 3);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('¿Cuáles de estos países están en Asia?', 'OPCION_MULTIPLE', true, 3);

INSERT INTO pregunta (enunciado, type, es_multiple, categoria_id) 
VALUES ('Oceanía tiene más países que Europa', 'VERDADERO_FALSO', false, 3);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('África', true, 17);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Asia', false, 17);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Europa', false, 17);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('América', false, 17);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Verdadero', false, 18);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Falso', true, 18);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Japón', true, 19);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('India', true, 19);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Canadá', false, 19);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Irán', true, 19);

INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Verdadero', false, 20);
INSERT INTO opcion (opciones, es_correcta, pregunta_id) VALUES ('Falso', true, 20);
