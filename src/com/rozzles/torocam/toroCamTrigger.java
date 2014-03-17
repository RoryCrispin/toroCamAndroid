package com.rozzles.torocam;

import com.rozzles.torocam.BlueComms.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class toroCamTrigger extends Activity {

	boolean mBounded;
	boolean bulbMode;
	BlueComms mServer;
	public static final String TOROCAM_PREFS = "AndCamPreferences";
	SharedPreferences.Editor editor;
	SharedPreferences settings;
	View v;
	Intent mIntent;
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 * Overrides the back button press and inserts the custom slide transition
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
	}
	protected void onCreate(Bundle savedInstanceState) {
	
		
		Intent mIntent = new Intent(this, BlueComms.class);
		startService(mIntent);
		bindService(mIntent, mConnection, BIND_AUTO_CREATE);
		
		super.onCreate(savedInstanceState);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/robotoLI.otf");
		TextView tv = (TextView) findViewById(R.id.s1Text);
		tv.setTypeface(tf);

		settings = getSharedPreferences(TOROCAM_PREFS, 0);
		editor = settings.edit();
		
		
		
		v = getWindow().getDecorView().findViewById(android.R.id.content);

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
	protected void onStop() {
		super.onStop();
		if(mBounded) {
			unbindService(mConnection);
			mBounded = false;
		}
	};


	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) { 
			sendCapture();
			return true;
		} else if( keyCode == KeyEvent.KEYCODE_MENU) {
			optionsClicked(v);
			return true; 
		} else {
			return super.onKeyDown(keyCode, event); 
		}
	}
	public void sendCapture(){
		//Overridden by subclass	
	}
	public boolean advancedMode(){
		SharedPreferences prefs = getSharedPreferences(TOROCAM_PREFS, 0);
		if(!(prefs.getBoolean("advFunctions", false))){
			return false;
		} else {
			return true;
		}
	}
	public void optionsClicked(View v){
		
	}
	

}
