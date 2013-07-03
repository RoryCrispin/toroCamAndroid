package com.rozzles.camera;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class LightTrigger extends Activity {
public float delay;
public float mod;
public int bulbBinary;

BlueComms sendMsg = new BlueComms();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light_trigger);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		SeekBar delaySeek = (SeekBar)findViewById(R.id.LightDelay); 
		SeekBar modSeek = (SeekBar)findViewById(R.id.multiplierSeek); 
		final TextView delayView = (TextView)findViewById(R.id.timeDelayVal);
		final TextView modView = (TextView)findViewById(R.id.multiplierVal);
		
		delaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				delay = (float) arg1/100; 
				delayView.setText(String.valueOf(delay + " seconds"));
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {	
			} });
		modSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				mod = (float) arg1/100; 
				modView.setText(String.valueOf(mod + "x"));
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {	
			} });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.light_trigger, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
	    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
	    startActivityForResult(myIntent, 0);
	    return true;
	}
	public void CaptureClick(View v) {
		final CheckBox bulb = (CheckBox)findViewById(R.id.bulbCheck);
		if (bulb.isChecked() == true) {
			bulbBinary = 1;
		} else {
		bulbBinary = 0;}
		sendMsg.sendMsg("4," + delay + "," + mod + "," + bulbBinary + ",0,0,0,0,0,0,!");
	}

}
