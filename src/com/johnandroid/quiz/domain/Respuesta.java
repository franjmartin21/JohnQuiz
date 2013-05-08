package com.johnandroid.quiz.domain;

/**
 * Objeto de dominio respuesta que contiene su id el texto de la
 * respuesta y un campo boolean indicando si es una respuesta correcta
 * @author Fran
 * @since  0.1
 */
public class Respuesta {
	private int id;
	private String textoRespuesta;
	private boolean correcta;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTextoRespuesta() {
		return textoRespuesta;
	}
	public void setTextoRespuesta(String textoRespuesta) {
		this.textoRespuesta = textoRespuesta;
	}
	public boolean isCorrecta() {
		return correcta;
	}
	public void setCorrecta(boolean correcta) {
		this.correcta = correcta;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (correcta ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result
				+ ((textoRespuesta == null) ? 0 : textoRespuesta.hashCode());
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
		Respuesta other = (Respuesta) obj;
		if (correcta != other.correcta)
			return false;
		if (id != other.id)
			return false;
		if (textoRespuesta == null) {
			if (other.textoRespuesta != null)
				return false;
		} else if (!textoRespuesta.equals(other.textoRespuesta))
			return false;
		return true;
	}
	
	
}
