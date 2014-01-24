/*
* Rory Crispin --rorycrispin.co.uk -- rozzles.com
*
* Distributed under the Creative Commons
* Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)
* License, full conditions can be found here:
* http://creativecommons.org/licenses/by-sa/3.0/
*
* This is free software, and you are welcome to redistribute it
* under certain conditions;
*
* Go crazy,
* Rozz xx
*
*/
package com.rozzles.torocam;

import com.rozzles.torocam.R;
import com.rozzles.torocam.BlueComms.LocalBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class ServoTimelapse extends Activity {
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
	}
	
	boolean mBounded;
	BlueComms mServer;
	
	SeekBar startXseek = null;
	SeekBar startYseek = null;
	SeekBar endXseek = null;
	SeekBar endYseek = null;
	SeekBar delaySeek = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_servo_timelapse);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/robotoLI.otf");
		TextView tv = (TextView) findViewById(R.id.s1Text);
		tv.setTypeface(tf);
		//Init ui elements
		final TextView startXvalue = (TextView) findViewById(R.id.startXvalue);
		final TextView startYvalue = (TextView) findViewById(R.id.startYvalue);
		final TextView endXvalue = (TextView) findViewById(R.id.endXvalue);
		final TextView endYvalue = (TextView) findViewById(R.id.endYvalue);
		final TextView delayValue = (TextView) findViewById(R.id.delayValueServo);
		
		startXseek = (SeekBar) findViewById(R.id.startXseek);
		startYseek = (SeekBar) findViewById(R.id.startYseek);
		endXseek = (SeekBar) findViewById(R.id.endXseek);
		endYseek = (SeekBar) findViewById(R.id.endYseek);
		delaySeek = (SeekBar) findViewById(R.id.delaySeek);
		
		Intent mIntent = new Intent(this, BlueComms.class);
		bindService(mIntent, mConnection, BIND_AUTO_CREATE);
		
		delaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				delayValue.setText(arg1 + " s");
				
			}
		});
		
		startXseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				startXvalue.setText(arg1 + "�");
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}});
		
		startYseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				startYvalue.setText(arg1 + "�");
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}});
			
		endXseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				endXvalue.setText(arg1 + "�");
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}});
		endYseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				endYvalue.setText(arg1 + "�");
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}});
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
	
	public void onToggleClicked(View view) {
		boolean on = ((ToggleButton) view).isChecked();
		if (on) {
			
			mServer.sendData("6," + startXseek.getProgress() + "," + startYseek.getProgress() + "," + endXseek.getProgress() + "," + endYseek.getProgress() + "," + delaySeek.getProgress() + ","  + "0,0,0,0!");
			
		} else {
			mServer.sendData("0,0,0,0,0,0,0,0,0,0!");
		}
	}
	
	public void onOptionsClicked(View view){
		
	}
	
	

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.servo_timelapse, menu);
		return true;
	}

}
