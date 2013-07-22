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

<<<<<<< HEAD
import com.rozzles.camera.BlueComms.LocalBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
=======
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class SoundTrigger extends Activity {
	public float delay;
	public float mod;
	public int bulbBinary;

<<<<<<< HEAD
	boolean mBounded;
	BlueComms mServer;
=======
	BlueComms sendMsg = new BlueComms();
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light_trigger);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		SeekBar delaySeek = (SeekBar) findViewById(R.id.LightDelay);
		SeekBar modSeek = (SeekBar) findViewById(R.id.multiplierSeek);
		final TextView delayView = (TextView) findViewById(R.id.timeDelayVal);
		final TextView modView = (TextView) findViewById(R.id.multiplierVal);
<<<<<<< HEAD
		 
		Intent mIntent = new Intent(this, BlueComms.class);
	     bindService(mIntent, mConnection, BIND_AUTO_CREATE);

		delaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
=======

		delaySeek
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10

					@Override
					public void onProgressChanged(SeekBar arg0, int arg1,
							boolean arg2) {
						delay = (float) arg1 / 100;
						delayView.setText(String.valueOf(delay + " seconds"));
					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
					}

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
					}
				});
		modSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				mod = (float) arg1 / 100;
				modView.setText(String.valueOf(mod + "x"));
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
		});
	}
<<<<<<< HEAD
	ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName name) {
			mBounded = false;
			mServer = null;
		}
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBounded = true;
			LocalBinder mLocalBinder = (LocalBinder)service;
			mServer = mLocalBinder.getServerInstance();
		}
	};
	
=======

>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.light_trigger, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
<<<<<<< HEAD
=======
		sendMsg.killBT();
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		Intent myIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		startActivityForResult(myIntent, 0);
		return true;
	}

	public void CaptureClick(View v) {
		final CheckBox bulb = (CheckBox) findViewById(R.id.bulbCheck);
		if (bulb.isChecked() == true) {
			bulbBinary = 1;
		} else {
			bulbBinary = 0;
		}
<<<<<<< HEAD
		mServer.sendData("3," + Math.round((200-(mod*100))) + ",1000," + Math.round(delay) + "," + bulbBinary
				+ ",0,0,0,0,0!");
	}
	public void Recal(View v) {
		mServer.sendData("9,0,0,0,0,0,0,0,0,0!");
=======
		sendMsg.sendMsg("5," + delay + "," + mod + "," + bulbBinary
				+ ",0,0,0,0,0,0,!", 4);
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
	}

}
