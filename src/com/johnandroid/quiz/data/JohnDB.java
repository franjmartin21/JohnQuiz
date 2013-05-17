package com.johnandroid.quiz.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.johnandroid.quiz.domain.Pregunta;
import com.johnandroid.quiz.domain.Respuesta;

public class JohnDB {
	private SQLiteDatabase db;
	private final Context context;
	private final JohnDBhelper dbHelper;
	
	// String con la query que se va a lanzar para devolver todas las preguntas de la base de datos
	private static final String SELECT_ALL = 
			"SELECT P.ID PID, P.TEXTOPREGUNTA TEXTOPREGUNTA, R.ID RID, R.TEXTORESPUESTA TEXTORESPUESTA, R.CORRECTAYN CORRECTAYN" +
			" FROM PREGUNTAS P, RESPUESTAS R" +
			" WHERE P.ID = R.PREGUNTAID" +
			" ORDER BY P.ID, R.ID";
	
	public JohnDB (Context c){
		context = c;
		//dbHelper = new JohnDBhelper(context, Constants.DB_NAME, null, Constants.DB_VERSION);
		dbHelper = new JohnDBhelper(context, null, null, Constants.DB_VERSION);
		db=dbHelper.getWritableDatabase();
	}
	
	public void close(){
		db.close();
	}
	
	public void open() throws SQLiteException{
		try{
			db=dbHelper.getWritableDatabase();
		} catch(SQLiteException e){
			Log.v("Open database exception caught", e.getMessage());
		}
	}
	
	/**
	 * Ejecuta query para obtener todas las preguntas de la base de datos y mapea el resultado
	 * en un Array de objetos Pregunta
	 * @return List <Pregunta>
	 */
	public List <Pregunta> getPreguntas(){
		List <Pregunta> preguntas= null;
		Cursor c = null;
		try{
			db=dbHelper.getReadableDatabase();
			c = db.rawQuery(SELECT_ALL, null);
			preguntas = getObjectsFromCursor(c);
		} finally{
			c.close();
			close();
		}
		return preguntas;
	}

	/**
	 * Clase 
	 * @param c Cursor que va a ser mapeado en objetos Pregunta. Este método confia en que el cursor 
	 * esta ordenado en primer lugar por el id de la pregunta
	 * @return List <Pregunta>
	 */
	private List<Pregunta> getObjectsFromCursor(Cursor c){
		if(c == null) 
			return null;
		List <Pregunta> preguntas = new ArrayList<Pregunta>();
		int preguntaKeyAux = -1;
		Pregunta pregunta = null;
		Respuesta respuesta = null;
		while(c.moveToNext()){
			int preguntaKey = c.getInt(c.getColumnIndex("PID"));
			if(preguntaKeyAux != preguntaKey){
				preguntaKeyAux = preguntaKey;
				pregunta = new Pregunta();
				respuesta = new Respuesta();
				pregunta.setId(preguntaKey);
				pregunta.setTextoPregunta(c.getString(c.getColumnIndex("TEXTOPREGUNTA")));
				preguntas.add(pregunta);
			}
			respuesta = new Respuesta();
			respuesta.setId(c.getInt(c.getColumnIndex("RID")));
			respuesta.setTextoRespuesta(c.getString(c.getColumnIndex("TEXTORESPUESTA")));
			respuesta.setCorrecta(c.getInt(c.getColumnIndex("CORRECTAYN")) == 1 ? true : false);
			pregunta.addRespuesta(respuesta);
		}
		return preguntas;
	}
	
}
