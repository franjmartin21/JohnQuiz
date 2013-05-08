package com.johnandroid.quiz.services;

import java.util.List;

import com.johnandroid.quiz.domain.Pregunta;

/**
 * Interfaz que sirve de fachada de conexion para la capa de persistencia
 * @author Fran
 * @since  0.1
 */
public interface JohnQuizService {
	public List<Pregunta> getPreguntas(int numPreguntas);
}
