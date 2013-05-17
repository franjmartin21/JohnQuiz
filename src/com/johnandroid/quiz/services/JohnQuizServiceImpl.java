package com.johnandroid.quiz.services;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.johnandroid.quiz.data.JohnDB;
import com.johnandroid.quiz.domain.Pregunta;

/**
 * Implementacion de la interfaz JohnQuizService que sirve de acceso a la capa de datos
 * 
 * Esta clase carga todos los datos en el constructor, y devuelve el numero de preguntas
 * requerido, resolviendo al azar que preguntas devolver.
 * 
 * Para probar la funcionalidad de esta clase he creado en el proyecto JohnQuizTest la 
 * clase JohnQuizServiceImplTest
 * 
 * @author Fran
 *
 */
public class JohnQuizServiceImpl implements JohnQuizService{
	
	private JohnDB johnDB;
	private List<Pregunta> preguntas;
	
	/**
	 * Todas las preguntas se cargan desde el propio construtor, como no tengo ni p... de performance
	 * en Android, quizás esto pueda suponer un problema de memoria a futuro si hay demasiadas preguntas
	 * cargadas en el Array
	 * @param context
	 */
	public JohnQuizServiceImpl(Context context){
		johnDB = new JohnDB(context);
		preguntas = johnDB.getPreguntas();
	}
	
	@Override
	public List<Pregunta> getPreguntas(int numPreguntas) {
		List <Pregunta> preguntasReturn = new ArrayList<Pregunta>();
		for(int i = 0; i < numPreguntas; i++){
			preguntasReturn.add(preguntas.get(getRandomId(preguntas.size())));
		}
		return preguntasReturn;
	}
	
	/**
	 * Devuelve el numero de la pregunta a devolver, quizás a futuro haya que asegurar
	 * que siempre una pregunta distinta es añadida al pool de preguntas que se devolverá
	 * @param maxNumber
	 * @return
	 */
	private int getRandomId(int maxNumber){
		return (int)Math.floor((Math.random() * maxNumber));
	}

}
