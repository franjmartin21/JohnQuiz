package com.johnandroid.quiz.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class JohnDB {
	private SQLiteDatabase db;
	private final Context context;
	private final JohnDBhelper dbHelper;
	
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
		Cursor c = db.query(Constants.TABLE_NAME, null, null, null, null, null, null);
		return c;
	}
	
}
