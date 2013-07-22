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

import com.rozzles.camera.R;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Timelapse extends Activity {
	public long millis;
	public int shots;
	int delay;
	public int spin = 1;
<<<<<<< HEAD
	int secs = 0;
	int mins = 0; 
	int hurs = 0;
	boolean mBounded;
	BlueComms mServer;
=======
	BlueComms sendMsg = new BlueComms();
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timelapse);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		final SeekBar delaySeek = (SeekBar) findViewById(R.id.TimeDelaySeek);
		final SeekBar shotsSeek = (SeekBar) findViewById(R.id.ShotsSeek);
		Spinner spinner = (Spinner) findViewById(R.id.TimeSpinner);
		// final TextView shotsText = (TextView)findViewById(R.id.shotView);
		final TextView delayView = (TextView) findViewById(R.id.TimelapseDelayView);
		final TextView totalTime = (TextView) findViewById(R.id.totalTime);
		final TextView totalShotView = (TextView) findViewById(R.id.totalShots);
<<<<<<< HEAD
		Intent mIntent = new Intent(this, BlueComms.class);
	     bindService(mIntent, mConnection, BIND_AUTO_CREATE);
=======

>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		final TimeParse timeparse = new TimeParse();
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this,

				R.array.timeArray, android.R.layout.simple_spinner_item);
<<<<<<< HEAD

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
=======
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				java.lang.System.out.println(String.valueOf(arg3));
				if (arg3 == 0) {
					spin = 1;
				} else if (arg3 == 1) {
					spin = 60;
				} else if (arg3 == 2) {
					spin = 3600;
				}
				totalTime(totalTime, timeparse, delay, shots, spin);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		shotsSeek
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar shotsSeek,
							int progress, boolean fromUser) {
						totalShotView.setText(progress + " shots");
						// shotsText.setText(String.valueOf(progress));
						shots = progress;
						totalTime(totalTime, timeparse, delay, shots, spin);
					};

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});

<<<<<<< HEAD
		delaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
=======
		delaySeek
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
					@Override
					public void onProgressChanged(SeekBar delaySeek,
							int progress, boolean fromUser) {
						delayView.setText(String.valueOf(progress));
						delay = progress;
						totalTime(totalTime, timeparse, delay, shots, spin);
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
=======
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10

	public void totalTime(TextView totalTime, TimeParse timeparse, int delay,
			int shots, int spin) {

		millis = delay * shots * spin;
<<<<<<< HEAD
=======
		// totalShotView.setText(totalshots + " shots");
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		totalTime.setText(TimeParse.getDurationBreakdown(millis));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
<<<<<<< HEAD
=======
		// Inflate the menu; this adds items to the action bar if it is present.
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		getMenuInflater().inflate(R.menu.timelapse, menu);
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

	public void onToggleClicked(View view) {
		boolean on = ((ToggleButton) view).isChecked();
		if (on) {
<<<<<<< HEAD
			delayParse();
			mServer.sendData("2," + secs + "," + mins + "," + hurs + "," + shots
					+ ",0,0,0,0,0!");
			clearTimes();
		} else {
			mServer.sendData("0,0,0,0,0,0,0,0,0,0!");
		}
	}
	public void delayParse(){
		if (spin == 1){
			secs = delay;
		} else if (spin == 60){
			mins = delay;
		} else if (spin == 3600){
			hurs = delay;
		} else { 
			System.out.println("Spin value empty");
		}
	}
	public void clearTimes(){
		secs = 0;
		mins = 0;
		hurs = 0;
	}
=======
			sendMsg.sendMsg("2," + delay + "," + spin + "," + shots
					+ ",0,0,0,0,0,0,!", 2);
		} else {
			sendMsg.sendMsg("3,0,0,0,0,0,0,0,0,0,!", 2);
		}
	}
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
}
