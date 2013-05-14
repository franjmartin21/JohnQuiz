package com.johnandroid.quiz.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

import com.johnandroid.quiz.R;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class JohnDBhelper extends SQLiteOpenHelper{
	private static final String LOG_TAG = "JohnDBhelper";
	
	private static final String CREATE_TABLE_PREGUNTAS ="create table "+ Constants.TABLE_PREGUNTAS + "(" +
			Constants.KEYID_PREGUNTAS + " integer primary key autoincrement, "+
			Constants.TEXTO_PREGUNTAS + " text not null)";
	
	private static final String CREATE_TABLE_RESPUESTAS ="create table "+ Constants.TABLE_RESPUESTAS + "(" +
			Constants.KEYID_RESPUESTAS + " integer primary key autoincrement, "+
			Constants.TEXTO_RESPUESTAS + " text not null, " +
			Constants.ES_CORRECTA_RESPUESTAS + " integer not null," +
			Constants.PREGUNTAID_REPUESTAS +" integer not null, " +
					"FOREIGN KEY (" + Constants.PREGUNTAID_REPUESTAS + ") references "+ 
					Constants.TABLE_PREGUNTAS +"("+Constants.KEYID_PREGUNTAS+"))";
	
	private static String INSERT_PREGUNTAS = "insert into "+ Constants.TABLE_PREGUNTAS + "(" + Constants.KEYID_PREGUNTAS + "," + Constants.TEXTO_PREGUNTAS + ") values ( ?, ?)";
	private static String INSERT_RESPUESTAS = "insert into "+ Constants.TABLE_RESPUESTAS + "(" + Constants.KEYID_RESPUESTAS + "," + Constants.TEXTO_RESPUESTAS + "," + Constants.ES_CORRECTA_RESPUESTAS+","+Constants.PREGUNTAID_REPUESTAS+") values ( ?, ?, ?, ?)";
	
	private final Context context;
	
	public JohnDBhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.context =context; 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v("JohnDBhelper onCreate", "Creating all the tables");
		try{
			db.execSQL(CREATE_TABLE_PREGUNTAS);
			db.execSQL(CREATE_TABLE_RESPUESTAS);
			insertData(db);
		} catch(SQLiteException e){
			Log.v("Create table exception", e.getMessage());
		}
	}
	
	private void insertData(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Properties props = getDataToInsert();
		SQLiteStatement stmtPreg = db.compileStatement(INSERT_PREGUNTAS);
		SQLiteStatement stmtResp = db.compileStatement(INSERT_RESPUESTAS);
		Enumeration<Object> enumer = props.keys();
		while(enumer.hasMoreElements()){
			String key = (String)enumer.nextElement();
			Log.d("KEY ",key);
			String []keyTokens = key.split("\\.");
			String value = (String) props.get(key);
			String [] valueTokens = value.split("\\|");
			if(key.contains("respuesta")){     
				stmtResp.bindLong(1, Long.valueOf(keyTokens[1].trim()+keyTokens[3].trim()));
				stmtResp.bindString(2, valueTokens[0]);
				stmtResp.bindLong(3, Long.valueOf(valueTokens[1].trim()));
				stmtResp.bindLong(4, Long.valueOf(keyTokens[1].trim()));
				stmtResp.executeInsert();
			} else {
				stmtPreg.bindLong(1, Long.valueOf(keyTokens[1].trim()));
				stmtPreg.bindString(2, valueTokens[0]);
				stmtPreg.executeInsert();
			}
		}
	}

	private Properties getDataToInsert(){
		Properties properties = null;
		Resources resources = context.getResources();
		InputStream is = resources.openRawResource(R.raw.deportes);
		try{
			properties = new Properties();
			properties.load(is);
			
		}catch (IOException e){
			Log.e("LOG_TAG", e.getMessage());
		}
		return properties;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		db.execSQL("drop table if exists "+Constants.TABLE_PREGUNTAS);
		onCreate(db);
	}

}
