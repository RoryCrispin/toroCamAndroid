/*
 * Rory Crispin --rorycrispin.co.uk -- rozzles.com
 * 
 * Distributed under the Creative Commons 
 * Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)
 * License, full conditions can be found here: 
 * http://creativecommons.org/licenses/by-sa/3.0/
 *   
 *   This is free software, and you are welcome to redistribute it
 *   under certain conditions;
 *   
 *   Go crazy,
 *   Rozz xx 
 *
 */
package com.rozzles.camera;

import java.io.OutputStream;
import com.rozzles.camera.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class ShutterRelease extends Activity {
	MainActivity mid = new MainActivity();

	BlueComms blu = new BlueComms();
	public int prog = 0;
	// public boolean blu
	OutputStream out = blu.outStream;
	BluetoothSocket bt = blu.btSocket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shutter_release);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		SeekBar seekBar = (SeekBar) findViewById(R.id.TimeDelaySeek);
		final TextView seekBarValue = (TextView) findViewById(R.id.delayIntView);

		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				seekBarValue.setText(String.valueOf(progress + " seconds"));
				prog = progress;
			};

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		blu.killBT();
		Intent myIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		startActivityForResult(myIntent, 0);
		return true;
	}

	public void CaptureClick(View view) {
		blu.sendMsg("1," + prog + ",0,0,0,0,0,0,0,0,!", 1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shutter_release, menu);
		return true;
	}

	public void wrKill(View v) {
		blu.killBT();
	}

	public void wrH(View v) {
		blu.sendMsg("H", 1);
	}

	public void wrL(View v) {
		blu.sendMsg("L", 1);
	}

}
