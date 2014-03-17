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
package com.rozzles.torocam;

import com.rozzles.torocam.R;
import com.rozzles.torocam.BlueComms.LocalBinder;

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

public class Timelapse extends toroCamTrigger {
	
	public long millis;
	public int shots;
	int delay;
	public int spin = 1;
	int secs = 0;
	int mins = 0; 
	int hurs = 0;
	int move;
	ToggleButton captureToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_timelapse);
		super.onCreate(savedInstanceState);
		final SeekBar delaySeek = (SeekBar) findViewById(R.id.TimeDelaySeek);
		final SeekBar shotsSeek = (SeekBar) findViewById(R.id.ShotsSeek);
		final SeekBar functionSeek = (SeekBar) findViewById(R.id.seekFunction);
		Spinner spinner = (Spinner) findViewById(R.id.TimeSpinner);
		final TextView shotsText = (TextView)findViewById(R.id.ShotsView);
		final TextView delayView = (TextView) findViewById(R.id.TimelapseDelayView);
		final TextView totalTime = (TextView) findViewById(R.id.totalTime);
		final TextView totalShotView = (TextView) findViewById(R.id.totalShots);
		final TextView textvalFunction = (TextView) findViewById(R.id.textvalFunction);
		final TimeParse timeparse = new TimeParse();
		captureToggle  = (ToggleButton) findViewById(R.id.toggleButton1);


		
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
						if(progress != 0)	{
							totalShotView.setText(progress + " shots");
						}else{
							totalShotView.setText("\u221e shots");
						}
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
		
		
		if(advancedMode()){
			textvalFunction.setVisibility(View.INVISIBLE);
			functionSeek.setVisibility(View.INVISIBLE);
			totalShotView.setVisibility(View.INVISIBLE);
			shotsSeek.setVisibility(View.INVISIBLE);
			shotsText.setVisibility(View.INVISIBLE);
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
		if(shots != 0)	{
			totalTime.setText(TimeParse.getDurationBreakdown(millis));
		}else{
			totalTime.setText("\u221e time");
		}
		
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
	public void sendCapture_Volume(){
		captureToggle.setChecked(!captureToggle.isChecked()); 
		sendCapture();
	}
	public void sendCapture(){
		if (captureToggle.isChecked()) {
			delayParse();
			mServer.sendData("2," + secs + "," + mins + "," + hurs + "," + shots
					+ "," + move + ",0,0,0,0!");
			clearTimes();
		} else {
			mServer.sendData("0,0,0,0,0,0,0,0,0,0!");
		}
	}
	
	public void onToggleClicked(View view) {
		sendCapture();
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
