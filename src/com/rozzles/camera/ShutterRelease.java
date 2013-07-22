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
import com.rozzles.camera.R;
import com.rozzles.camera.BlueComms.LocalBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
=======
import java.io.OutputStream;
import com.rozzles.camera.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
<<<<<<< HEAD
import android.widget.Toast;

public class ShutterRelease extends Activity {
	MainActivity mid = new MainActivity();
	boolean mBounded;
	BlueComms mServer;
	public int prog = 0;
=======

public class ShutterRelease extends Activity {
	MainActivity mid = new MainActivity();

	BlueComms blu = new BlueComms();
	public int prog = 0;
	// public boolean blu
	OutputStream out = blu.outStream;
	BluetoothSocket bt = blu.btSocket;
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
<<<<<<< HEAD
		
		 Intent mIntent = new Intent(this, BlueComms.class);
	     bindService(mIntent, mConnection, BIND_AUTO_CREATE);
		
		
=======
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		setContentView(R.layout.activity_shutter_release);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		SeekBar seekBar = (SeekBar) findViewById(R.id.TimeDelaySeek);
		final TextView seekBarValue = (TextView) findViewById(R.id.delayIntView);
<<<<<<< HEAD
=======

>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
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

	@Override
	protected void onStop() {
		super.onStop();
		if(mBounded) {
			unbindService(mConnection);
			mBounded = false;
		}
	};
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
=======
	public boolean onOptionsItemSelected(MenuItem item) {
		blu.killBT();
		Intent myIntent = new Intent(getApplicationContext(),
				MainActivity.class);
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		startActivityForResult(myIntent, 0);
		return true;
	}

	public void CaptureClick(View view) {
<<<<<<< HEAD
		mServer.sendData("1," + prog + ",0,0,0,0,0,0,0,0!");
=======
		blu.sendMsg("1," + prog + ",0,0,0,0,0,0,0,0,!", 1);

>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
<<<<<<< HEAD
=======
		// Inflate the menu; this adds items to the action bar if it is present.
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		getMenuInflater().inflate(R.menu.shutter_release, menu);
		return true;
	}

	public void wrKill(View v) {
<<<<<<< HEAD

	}

	public void wrH(View v) {
		mServer.sendData("H");
	}

	public void wrL(View v) {
		mServer.sendData("L");
=======
		blu.killBT();
	}

	public void wrH(View v) {
		blu.sendMsg("H", 1);
	}

	public void wrL(View v) {
		blu.sendMsg("L", 1);
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
	}

}
