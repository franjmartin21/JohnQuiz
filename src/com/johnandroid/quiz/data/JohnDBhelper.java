package com.johnandroid.quiz.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.johnandroid.quiz.R;

/**
 * Esta clase realiza la inicialización de la base de datos, creando las tablas y añadiendo
 * toda la información necesaria en ellas.
 * 
 * La clase JohnDBTest del proyecto JohnAndroidTest, ha sido creada para comprobar la carga de
 * preguntas y respuestas en la base de datos y los métodos de consulta de base de datos de la clase
 * JohnDB.
 * @author Fran
 *
 */
public class JohnDBhelper extends SQLiteOpenHelper{
	private static final String LOG_TAG = "JohnDBhelper";
	
	// Sql para crear la tabla preguntas
	private static final String CREATE_TABLE_PREGUNTAS ="create table "+ Constants.TABLE_PREGUNTAS + "(" +
			Constants.KEYID_PREGUNTAS + " integer primary key autoincrement, "+
			Constants.TEXTO_PREGUNTAS + " text not null)";
	
	// Sql para crear la tabla de respuestas, a pesar de la foreign key he comprobado que sqlite no garantiza
	// integridad referencial
	private static final String CREATE_TABLE_RESPUESTAS ="create table "+ Constants.TABLE_RESPUESTAS + "(" +
			Constants.KEYID_RESPUESTAS + " integer primary key autoincrement, "+
			Constants.TEXTO_RESPUESTAS + " text not null, " +
			Constants.ES_CORRECTA_RESPUESTAS + " integer not null," +
			Constants.PREGUNTAID_REPUESTAS +" integer not null, " +
					"FOREIGN KEY (" + Constants.PREGUNTAID_REPUESTAS + ") references "+ 
					Constants.TABLE_PREGUNTAS +"("+Constants.KEYID_PREGUNTAS+"))";
	
	//Strings que van a servir como SQLiteStatement (como un PreparedStatement) para insertar toda la informacion
	//de preguntas y respuestas en la base de datos
	private static String INSERT_PREGUNTAS = "insert into "+ Constants.TABLE_PREGUNTAS + "(" + Constants.KEYID_PREGUNTAS + "," + Constants.TEXTO_PREGUNTAS + ") values ( ?, ?)";
	private static String INSERT_RESPUESTAS = "insert into "+ Constants.TABLE_RESPUESTAS + "(" + Constants.KEYID_RESPUESTAS + "," + Constants.TEXTO_RESPUESTAS + "," + Constants.ES_CORRECTA_RESPUESTAS+","+Constants.PREGUNTAID_REPUESTAS+") values ( ?, ?, ?, ?)";
	
	private final Context context;
	
	public JohnDBhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.context =context; 
	}

	/**
	 * Este método se ejecuta al instalar la aplicacion por primera vez.
	 * Va a crear las tablas, y insertar todos los datos
	 */
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
	
	/**
	 * Este método carga las dos SQLiteStatements, e inserta todas las preguntas y respuestas
	 * provenientes de los ficheros "property" de la carpeta /res/raw 
	 * @param db
	 */
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

	/**
	 * Obtiene los datos a insertar en una clase Properties0
	 * @return Properties, con los datos a insertar
	 */
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
