package com.johnandroid.quiz.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class JohnDBhelper extends SQLiteOpenHelper{
	
	private static final String CREATE_TABLE ="create table "+ Constants.TABLE_NAME + "(" +
			Constants.KEY_ID + " integer primary key autoincrement, "+
			Constants.TEXTO_PREGUNTA + " not null)";
	
	private String [] INSERTS = {
			"insert into preguntas (id,textopregunta) values (1,\"¿Que equipo gano la liga 2012/2013?\")",
			"insert into preguntas (id,textopregunta) values (2,\"¿Esta es la segunda pregunta?\")"
			};
	
	public JohnDBhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v("JohnDBhelper onCreate", "Creating all the tables");
		try{
			db.execSQL(CREATE_TABLE);
			for(String insert : INSERTS){
				Log.v("insert",insert);
				db.execSQL(insert);
			}
		} catch(SQLiteException e){
			Log.v("Create table exception", e.getMessage());
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		db.execSQL("drop table if exists "+Constants.TABLE_NAME);
		onCreate(db);
	}

}
