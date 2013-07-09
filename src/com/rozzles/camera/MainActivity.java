package com.rozzles.camera;


import com.rozzles.camera.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {
	BlueComms blu = new BlueComms();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void shutterButtonClick(View v) {
		blu.killBT();
		Intent myIntent = new Intent(v.getContext(), ShutterRelease.class);
		startActivityForResult(myIntent, 0);
		
	}
	public void timelapseButtonClick(View v) {
		blu.killBT();
		Intent myIntent = new Intent(v.getContext(), Timelapse.class);
		startActivityForResult(myIntent, 0);
	}
	public void lightButtonClick(View v) {
		blu.killBT();
		Intent myIntent = new Intent(v.getContext(), LightTrigger.class);
		startActivityForResult(myIntent, 0);
	}
	public void soundButtonClick(View v) {
		blu.killBT();
		Intent myIntent = new Intent(v.getContext(),  SoundTrigger.class);
		startActivityForResult(myIntent, 0);
	}
	public void btOptionsButtonClick(View v) {
		blu.killBT();
		Intent myIntent = new Intent(v.getContext(), BlueControl.class);
		startActivityForResult(myIntent, 0);
	}

	public void wrKill(View v) {
		blu.killBT();
	}
	public void wrH(View v) {
		blu.sendMsg("H", 100);
	}
	public void wrL(View v) {
		blu.sendMsg("L", 100);
	}
	public void devLinkButtonClick(View v) {

		if (blu.connectDevice(100) == true ){

			Toast.makeText(getApplicationContext(), "Connected to device",0).show();
			//savedInstanceState.putBoolean("blue", blu.isConnected);
		} else {
			Toast.makeText(getApplicationContext(), "Failed to connect", 0).show();
		}
	}
}

