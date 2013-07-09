package com.rozzles.camera;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class BlueControl extends Activity {
	Button sendButton;
	BlueComms blu = new BlueComms();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blue_control);
		sendButton = (Button) findViewById(R.id.SendButton);
	}
	public void tgClick(View v) {
		System.out.println("H");
		blu.sendMsg("H", 5);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blue_control, menu);
		return true;
	}	
}
