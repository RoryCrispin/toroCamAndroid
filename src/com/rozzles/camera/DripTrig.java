package com.rozzles.camera;

import com.rozzles.camera.BlueComms.LocalBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class DripTrig extends Activity {
	float delay;
	boolean mBounded;
	BlueComms mServer;

	RadioButton micRadio = null;
	RadioButton ldrRadio = null; 

	SeekBar sensSlide = null;
	SeekBar dlengthSlide = null;
	SeekBar dnoSlide = null;
	SeekBar dbdSlide = null;
	SeekBar flashSlide = null;

	int micSens;
	int ldrSens;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drip_trig);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		sensSlide  = (SeekBar) findViewById(R.id.sensitivitySlider);
		dlengthSlide  = (SeekBar) findViewById(R.id.dlengthSlider);
		dnoSlide  = (SeekBar) findViewById(R.id.dnoSlider);
		dbdSlide  = (SeekBar) findViewById(R.id.dbdSlider);
		flashSlide  = (SeekBar) findViewById(R.id.flashSlider);

		final TextView sensVal  = (TextView) findViewById(R.id.sensitivityTextView);
		final TextView dlengthVal  = (TextView) findViewById(R.id.dlengthTextView);
		final TextView dnoVal  = (TextView) findViewById(R.id.dnoTextView);
		final TextView dbdVal  = (TextView) findViewById(R.id.dbdTextView);
		final TextView flashVal  = (TextView) findViewById(R.id.flashTextView);

		micRadio = (RadioButton) findViewById(R.id.MICradio);
		ldrRadio = (RadioButton) findViewById(R.id.LDRradio);
		Intent mIntent = new Intent(this, BlueComms.class);
		bindService(mIntent, mConnection, BIND_AUTO_CREATE);
		sensSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				delay = (float) arg1 / 100;
				sensVal.setText(String.valueOf(delay + "x"));

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

		}); 
		dlengthSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				dlengthVal.setText(String.valueOf(progress));

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		}); 
		dnoSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				dnoVal.setText(String.valueOf(progress));

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		});
		dbdSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				dbdVal.setText(String.valueOf(progress));

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		});
		flashSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				flashVal.setText(String.valueOf(progress));

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.drip_trig, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
		startActivityForResult(myIntent, 0);
		return true;
	}
	public void captureClick(View v){
		if (micRadio.isChecked()) {
			micSens = sensSlide.getProgress();
			ldrSens = 1000;
		} else  if (ldrRadio.isChecked()) {
			micSens = 1000;
			ldrSens = sensSlide.getProgress();
		}
		mServer.sendData("4,"+ micSens +"," + ldrSens +"," + (dlengthSlide.getProgress()) + "," + 
		(dnoSlide.getProgress()) + "," + (dbdSlide.getProgress()) + "," + (flashSlide.getProgress()) + ",0,0,0!");
	}
	public void Recal(View v) {
		mServer.sendData("9,0,0,0,0,0,0,0,0,0!");
	}

}
