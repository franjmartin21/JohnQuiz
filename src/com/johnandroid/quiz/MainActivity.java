package com.johnandroid.quiz;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * No se lo que cambiar, de momento cambiar� el comentario,
 * pero la siguiente ya subir� algo interesante,
 * quiero probar a publicar...
 * @author Univan
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
