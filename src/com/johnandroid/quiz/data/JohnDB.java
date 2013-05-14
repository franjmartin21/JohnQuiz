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
	
	public Cursor getPreguntas(){
		Cursor c = db.query(Constants.TABLE_PREGUNTAS, null, null, null, null, null, null);
		return c;
	}
	
	public List <Pregunta> getPreguntas(int numPreguntas){
		List <Pregunta> preguntas= null;
		Cursor c = null;
		try{
		c = db.rawQuery(SELECT_ALL, null);
		preguntas = getObjectsFromCursor(c);
		/*for(int i=0; i<numPreguntas;i++){
			c.move(getRandomNumber());
			
		}*/
		} finally{
			c.close();
		}
		return preguntas;
	}

	private List<Pregunta> getObjectsFromCursor(Cursor c){
		if(c == null) 
			return null;
		List <Pregunta> preguntas = new ArrayList<Pregunta>();
		int preguntaKeyAux = -1;
		Pregunta pregunta = null;
		Respuesta respuesta = null;
		while(c.moveToNext()){
			int preguntaKey = c.getInt(c.getColumnIndex("PID"));
			if(preguntaKeyAux == preguntaKey){
				respuesta = new Respuesta();
				respuesta.setId(c.getInt(c.getColumnIndex("PID")));
				respuesta.setTextoRespuesta(c.getString(c.getColumnIndex("TEXTORESPUESTA")));
				respuesta.setCorrecta(c.getInt(c.getColumnIndex("CORRECTAYN")) == 1 ? true : false);
				pregunta.addRespuesta(respuesta);
			}else{
				preguntaKeyAux = preguntaKey;
				pregunta = new Pregunta();
				respuesta = new Respuesta();
				pregunta.setId(preguntaKey);
				pregunta.setTextoPregunta(c.getString(c.getColumnIndex("TEXTOPREGUNTA")));
				preguntas.add(pregunta);
				respuesta.setId(c.getInt(c.getColumnIndex("PID")));
				respuesta.setTextoRespuesta(c.getString(c.getColumnIndex("TEXTORESPUESTA")));
				respuesta.setCorrecta(c.getInt(c.getColumnIndex("CORRECTAYN")) == 1 ? true : false);
				pregunta.addRespuesta(respuesta);
			}
		}
		return preguntas;
	}
	
}
