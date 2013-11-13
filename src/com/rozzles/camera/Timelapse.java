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
import com.rozzles.camera.BlueComms.LocalBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Timelapse extends Activity {
	public static final String TOROCAM_PREFS = "AndCamPreferences";
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
	}
	public long millis;
	public int shots;
	int delay;
	public int spin = 1;
	int secs = 0;
	int mins = 0; 
	int hurs = 0;
	boolean mBounded;
	BlueComms mServer;
	int move;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_timelapse);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/robotoLI.otf");
		TextView tv = (TextView) findViewById(R.id.s1Text);
		tv.setTypeface(tf);
		//ActionBar actionBar = getActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
		final SeekBar delaySeek = (SeekBar) findViewById(R.id.TimeDelaySeek);
		final SeekBar shotsSeek = (SeekBar) findViewById(R.id.ShotsSeek);
		final SeekBar functionSeek = (SeekBar) findViewById(R.id.seekFunction);
		Spinner spinner = (Spinner) findViewById(R.id.TimeSpinner);
		// final TextView shotsText = (TextView)findViewById(R.id.shotView);
		final TextView delayView = (TextView) findViewById(R.id.TimelapseDelayView);
		final TextView totalTime = (TextView) findViewById(R.id.totalTime);
		final TextView totalShotView = (TextView) findViewById(R.id.totalShots);
		final TextView textvalFunction = (TextView) findViewById(R.id.textvalFunction);
		Intent mIntent = new Intent(this, BlueComms.class);
	     bindService(mIntent, mConnection, BIND_AUTO_CREATE);
		final TimeParse timeparse = new TimeParse();
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this,

				R.array.timeArray, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

		delaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
		
		functionSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				textvalFunction.setText(progress + " ms");
				move = progress;
				
			}
		});
		
		/*
		 * Checking if adv functions is enables and hiding adv options if not.
		 * It would be tidier to have a separate void for this but it would mean
		 *  making the objects public
		 */
		
		
		SharedPreferences prefs = getSharedPreferences(TOROCAM_PREFS, 0);
		if(!(prefs.getBoolean("advFunctions", false))){
			textvalFunction.setVisibility(View.INVISIBLE);
			functionSeek.setVisibility(View.INVISIBLE);
		}

	}

	
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

	public void totalTime(TextView totalTime, TimeParse timeparse, int delay,
			int shots, int spin) {

		millis = delay * shots * spin;
		totalTime.setText(TimeParse.getDurationBreakdown(millis));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timelapse, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = new Intent(getApplicationContext(), FlatHome.class);
		startActivityForResult(myIntent, 0);
		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
		return true;
	}

	public void onToggleClicked(View view) {
		boolean on = ((ToggleButton) view).isChecked();
		if (on) {
			delayParse();
			mServer.sendData("2," + secs + "," + mins + "," + hurs + "," + shots
					+ "," + move + ",0,0,0,0!");
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
}
