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
import com.rozzles.camera.R;

import android.os.Bundle;
import android.os.IBinder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {
	boolean mBounded;
	BlueComms mServer;
	TextView QuickConnectButton;
	public static final String PREFS_NAME = "AndCamPreferences";
	String devName;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/robotoLI.otf");
        TextView tv = (TextView) findViewById(R.id.shutterbutton);
        tv.setTypeface(tf);
	}
	
	public void onResume(){
		super.onResume();
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
		devName = prefs.getString("devicename", null);
		QuickConnectButton = (TextView) findViewById(R.id.devLink);
		if (devName != null){
			QuickConnectButton.setText("Quick Connect: " + devName);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void shutterButtonClick(View v) {
		//Intent myIntent = new Intent(v.getContext(), FlatHome.class);
		//startActivityForResult(myIntent, 0);
	     try    {           
	         Intent newIntent = new Intent(v.getContext(), FlatHome.class);    
	         startActivityForResult(newIntent, 0);
	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
	     } catch(Exception ex) {
	     }
	}

	public void timelapseButtonClick(View v) {
		Intent myIntent = new Intent(v.getContext(), Timelapse.class);
		startActivityForResult(myIntent, 0);
	}

	public void lightButtonClick(View v) {
		Intent myIntent = new Intent(v.getContext(), LightTrigger.class);
		startActivityForResult(myIntent, 0);
	}

	public void soundButtonClick(View v) {
		Intent myIntent = new Intent(v.getContext(), SoundTrigger.class);
		startActivityForResult(myIntent, 0);
	}

	public void btOptionsButtonClick(View v) {
		Intent myIntent = new Intent(v.getContext(), BlueControl.class);
		startActivityForResult(myIntent, 0);
	}

	public void devLinkButtonClick(View v) {
		 Intent mIntent = new Intent(this, BlueComms.class);
		 startService(mIntent);
	        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
	}
	public void dripTrigButtonClick(View v) {
		Intent myIntent = new Intent(v.getContext(), DripTrig.class);
		startActivityForResult(myIntent, 0);
	}
	public void hdrButtonClick(View v) {
		Intent myIntent = new Intent(v.getContext(), HDRLapse.class);
		startActivityForResult(myIntent, 0);
	}
	ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(MainActivity.this, "Service is disconnected", 1000).show();
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
	public void midmss(){
		mServer.sendData("L");
	}
}
