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

import com.rozzles.camera.BlueComms.LocalBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ShakeTrigger extends Activity {
	
	@Override 
	public void onPause(){
		super.onPause();
		mBounded = false;
		mServer = null;
	}
	
	@Override
	public void onBackPressed() {
		//TODO leaking service 
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
	    
	}
	public float delay;
	public float mod;
	public int bulbBinary;
	CheckBox bulb;
	CheckBox chkPersistant;
	boolean mBounded;
	BlueComms mServer;
	boolean listen;
	ToggleButton tog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shake_trigger);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/robotoLI.otf");
		TextView tv = (TextView) findViewById(R.id.s1Text);
		tv.setTypeface(tf);
		SeekBar delaySeek = (SeekBar) findViewById(R.id.LightDelay);
		SeekBar modSeek = (SeekBar) findViewById(R.id.multiplierSeek);
		tog = (ToggleButton) findViewById(R.id.CaptureButton); 
		final TextView delayView = (TextView) findViewById(R.id.timeDelayVal);
		final TextView modView = (TextView) findViewById(R.id.multiplierVal);
		bulb = (CheckBox) findViewById(R.id.bulbCheck);
		chkPersistant = (CheckBox) findViewById(R.id.chkPersistant);
		Intent mIntent = new Intent(this, BlueComms.class);
	     bindService(mIntent, mConnection, BIND_AUTO_CREATE);
	     
	     mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		    mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		    mAccel = 0.00f;
		    mAccelCurrent = SensorManager.GRAVITY_EARTH;
		    mAccelLast = SensorManager.GRAVITY_EARTH;

		delaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
				mod = (float) arg1 / 10;
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
	
	private SensorManager mSensorManager;
	  private float mAccel; // acceleration apart from gravity
	  private float mAccelCurrent; // current acceleration including gravity
	  private float mAccelLast; // last acceleration including gravity

	  private final SensorEventListener mSensorListener = new SensorEventListener() {

	    public void onSensorChanged(SensorEvent se) {
	      float x = se.values[0];
	      float y = se.values[1];
	      float z = se.values[2];
	      mAccelLast = mAccelCurrent;
	      mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
	      float delta = mAccelCurrent - mAccelLast;
	      mAccel = mAccel * 0.9f + delta; // perform low-cut filter
	      //System.out.println(x + " y " + y + " z " + z);
	      //System.out.println(mAccel);
	      if(listen == true && mAccel > mod){
	    	  mServer.sendData("1,0,0,0,0,0,0,0,0,0!");
	      }
	    }

	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    }
	  };

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.light_trigger, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = new Intent(getApplicationContext(), FlatHome.class);
		startActivityForResult(myIntent, 0);
		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
		return true;
	}

	public void CaptureClick(View v) {
		listen = tog.isChecked();
		
		
		//mServer.sendData("3," + Math.round((200-(mod*100))) + ",1000," + Math.round(delay*1000) + "," + "0" + "," + bulbBinary + ",0,0,0,0!");
	}
}
