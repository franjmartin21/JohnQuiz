package com.johnandroid.quiz.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * Objeto de dominio pregunta que encapsula los datos de una 
 * pregunta y sus respuestas
 * @author Fran
 * @since  0.1
 */
public class Pregunta {
	private int id;
	private String textoPregunta;
	private List<Respuesta> respuestas = new ArrayList<Respuesta>();
	
	public void addRespuesta(Respuesta respuesta){
		respuestas.add(respuesta);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTextoPregunta() {
		return textoPregunta;
	}
	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}
	public List<Respuesta> getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((respuestas == null) ? 0 : respuestas.hashCode());
		result = prime * result
				+ ((textoPregunta == null) ? 0 : textoPregunta.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pregunta other = (Pregunta) obj;
		if (id != other.id)
			return false;
		if (respuestas == null) {
			if (other.respuestas != null)
				return false;
		} else if (!respuestas.equals(other.respuestas))
			return false;
		if (textoPregunta == null) {
			if (other.textoPregunta != null)
				return false;
		} else if (!textoPregunta.equals(other.textoPregunta))
			return false;
		return true;
	}
}
