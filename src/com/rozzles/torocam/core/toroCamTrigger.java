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
package com.rozzles.torocam.core;

import com.rozzles.torocam.R;
import com.rozzles.torocam.R.anim;
import com.rozzles.torocam.R.array;
import com.rozzles.torocam.R.id;
import com.rozzles.torocam.core.BlueComms.LocalBinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class toroCamTrigger extends Activity {

	 boolean mBounded;
	public boolean bulbMode;
	public BlueComms mServer;
	public static final String TOROCAM_PREFS = "AndCamPreferences";
	SharedPreferences.Editor editor;
	SharedPreferences settings;
	View v;
	Intent mIntent;
	Context c;
	private static final String TAG = "toroCam";
	
	boolean[] advFunctionsState = {false};
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

	public void navigateToClass(Context context, Class classToNavigate)
	{
		try    {
			Intent newIntent = new Intent(context, classToNavigate);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			Log.d("TOROCAM", "Somethign went wrong with changing activities, Error: " + ex);
		}
	}

	protected void onCreate(Bundle savedInstanceState) {


		Intent mIntent = new Intent(this, BlueComms.class);
		startService(mIntent);
		bindService(mIntent, mConnection, BIND_AUTO_CREATE);

		super.onCreate(savedInstanceState);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/robotoLI.otf");
		TextView tv = (TextView) findViewById(R.id.headerTitle);
		try {
			tv.setTypeface(tf);
		} catch(Exception ex)
		{
			System.out.println(ex);
		}

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
			sendCapture_Volume();
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
	
	//Some activities need to perform different functions when the volume buttons 
	//are used instead of the on screen buttons and will override this. Others won't
	public void sendCapture_Volume(){
		sendCapture();
	}
	public void CaptureClick(View v) {
		sendCapture();
	}
	
	public boolean advancedMode(){
		SharedPreferences prefs = getSharedPreferences(TOROCAM_PREFS, 0);
		if(!(prefs.getBoolean("advFunctions", false))){
			return false;
		} else {
			return true;
		}
	}
	public void sendToroCamMessage(String message){
		mServer.sendData(message);
	}
	//This creates the popup options dialog
	public void optionsClicked(final View v) {

		advFunctionsState[0] = mServer.advFunctions();
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle("Options");
		c = this;

		helpBuilder.setNegativeButton("Redo Setup", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				editor.putBoolean("skipSetup", false);
				editor.putBoolean("completedSetup", false);
				editor.commit();
				navigateToClass(v.getContext(), SetupOne.class);
			}
		});
		
		helpBuilder.setPositiveButton("Reconnect", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mServer.Connect();
				mServer.Connect();
			}
		});
		
		helpBuilder.setMultiChoiceItems(R.array.optionsCheckboxes, advFunctionsState,
				new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) {
					//0 is the array value of the first check, Advanced Functions
					if(which == 0){ 

						SharedPreferences.Editor editor = settings.edit();
						editor.putBoolean("advFunctions", true);
						editor.commit();
					}

				} else {
					if(which == 0){ 

						SharedPreferences.Editor editor = settings.edit();
						editor.putBoolean("advFunctions", false);
						editor.commit();

					}
				}
			}
		});

		helpBuilder.setNeutralButton("Camera Mode", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				AlertDialog.Builder helpdBuilder = new AlertDialog.Builder(c);
				helpdBuilder.setTitle("IR Camera Mode");
				helpdBuilder.setItems(R.array.cameraArray, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0: System.out.println("NIKON CAMERA MODE");
						break;
						case 1: System.out.println("CANON CAMERA MODE");
						sendToroCamMessage("9,2,0!");
						break;
						case 2: System.out.println("OLYMPUS CAMERA MODE");
						mServer.readStream();
						break;	

						}
					}});


				helpdBuilder.show();


			}
		});


		// Remember, create doesn't show the dialog
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();

	}
	
	public void backClick(final View v) {
		onBackPressed();
	}


}
