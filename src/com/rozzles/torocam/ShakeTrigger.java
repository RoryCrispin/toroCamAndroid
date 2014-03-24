/*
 * Rory Crispin --rorycrispin.co.uk -- rozzles.com
 * 
 * Distributed under theAttribution-NonCommercial-ShareAlike 4.0 International
 * License, full conditions can be found here: 
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *   
 *   This is free software, and you are welcome to redistribute it
 *   under certain conditions;
 *   
 *   Go crazy,
 *   Rozz xx 
 * 
 */
package com.rozzles.torocam;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.rozzles.torocam.core.toroCamTrigger;

public class ShakeTrigger extends toroCamTrigger {

	@Override
	public void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mSensorListener);
	}

	public float delay;
	public float mod;
	public int bulbBinary;
	CheckBox bulb;
	CheckBox chkPersistant;
	boolean listen;
	ToggleButton tog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shake_trigger);
		super.onCreate(savedInstanceState);

		SeekBar delaySeek = (SeekBar) findViewById(R.id.LightDelay);
		SeekBar modSeek = (SeekBar) findViewById(R.id.multiplierSeek);
		tog = (ToggleButton) findViewById(R.id.CaptureButton);
		final TextView delayView = (TextView) findViewById(R.id.timeDelayVal);
		final TextView modView = (TextView) findViewById(R.id.multiplierVal);
		bulb = (CheckBox) findViewById(R.id.bulbCheck);
		chkPersistant = (CheckBox) findViewById(R.id.chkPersistant);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		mAccel = 0.00f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;

		delaySeek
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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

	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity

	private final SensorEventListener mSensorListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel * 0.9f + delta; // perform low-cut filter
			// System.out.println(x + " y " + y + " z " + z);
			// System.out.println(mAccel);
			if (listen == true && mAccel > mod) {
				sendToroCamMessage("1,0,0,0,0,0,0,0,0,0!");
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	public void sendCapture() {
		listen = tog.isChecked();
	}

	@Override
	public void sendCapture_Volume() {
		tog.setChecked(!tog.isChecked());
		listen = tog.isChecked();
	}
}
