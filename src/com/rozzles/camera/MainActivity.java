package com.rozzles.camera;

import com.rozzles.camera.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	//connect con = new connect();
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
		Intent myIntent = new Intent(v.getContext(), ShutterRelease.class);
		startActivityForResult(myIntent, 0);
	}
	public void timelapseButtonClick(View v) {
		Intent myIntent = new Intent(v.getContext(), Timelapse.class);
		startActivityForResult(myIntent, 0);
	}
	public void lightButtonClick(View v) {
		Intent myIntent = new Intent(v.getContext(), LightTrigger.class);
		startActivityForResult(myIntent, 0);
	}
	public void soundButtonClick(View v) {
		Intent myIntent = new Intent(v.getContext(), SoundTrigger.class);
		startActivityForResult(myIntent, 0);
	}
	public void devLinkButtonClick(View v) {
	System.out.println("con1");
	blu.connectDevice();
    System.out.println("con2");
	}
	
		
	}

