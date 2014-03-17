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
import android.os.Handler;
import android.os.IBinder;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlatHome extends toroCamTrigger {
	

	
	LinearLayout linButtons = null;
	boolean[] advFunctionsState = {false};


	boolean skipSetup;
	String[] myStringArray = {"Help","Power Off"};
	Context c;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_flat_home);
		super.onCreate(savedInstanceState);


		/*
		 * This checks to see if the user has already run the set up utility
		 * if they have not -> (!checkSetup()) <- it forwards them to the setup 
		 * utility
		 */
		if(!checkSetup()){
			try    {
				Intent newIntent = new Intent(this, SetupOne.class);    
				startActivityForResult(newIntent, 0);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
			} catch(Exception ex) {
				//I don't handle catches because I'm lazy 
			}
		} else {
			//Runs if setup has been completed
			hideConnect(); 
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flat_home, menu);
		return true;
	}

	/*
	 * This method is called to check the shared preference to see if the user
	 * has already run setup, if they have it returns true, otherwise false
	 */
	public boolean checkSetup(){

		if (settings.getBoolean("completedSetup", false)){
			return true;
		} else {
			if (settings.getBoolean("skipSetup", true)){
				skipSetup = true;
				return true;
			} else {
				return false;
			}
		}

	}
	/*
	 * Set of simple methods to forward the user to the appropriate 
	 * activities for different functions
	 */
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
	public void simpleShootClick(View v) {
		navigateToClass(v.getContext(), ShutterRelease.class);
	}

	public void timelapseClick(View v) {
		navigateToClass(v.getContext(), Timelapse.class);
	}
	public void lightClick(View v) {
		navigateToClass(v.getContext(), LightTrigger.class);
	}
	public void	soundClick(View v) {
		navigateToClass(v.getContext(), SoundTrigger.class);
	}
	public void	shakeClick(View v) {
		navigateToClass(v.getContext(), ShakeTrigger.class);
	}

	public void	dripClick(View v) {
		navigateToClass(v.getContext(), DripTrig.class);
	}
	public void	hdrClick(View v) {
		navigateToClass(v.getContext(), HDRLapse.class);
	}
	public void	srvClick(View v) {
		navigateToClass(v.getContext(), ServoTimelapse.class);
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
						mServer.sendData("9,2,0!");
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



	/*
	 * This is part of the system that hides the connecting button when the
	 * connection has been established
	 */
	public void hideConnect(){





		if(!skipSetup){
			TextView connectText = (TextView) findViewById(R.id.connectText);
			connectText.setText("Connecting...");
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {

					if (!mServer.isConnected){

						linButtons = (LinearLayout) findViewById(R.id.connectObj);
						linButtons.setVisibility(View.VISIBLE);
						if(mServer.Connect()){
							try    {           
								linButtons.setVisibility(View.GONE);       
							} catch(Exception ex) {
							}
						} else {
							TextView connectText = (TextView) findViewById(R.id.connectText);
							connectText.setText("Couldn't connect, retry?");
						}

					} else {
					}
				}
			}, 500);

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
			//mServer.Connect();
		}
	};
	public void connectClick(View v){
		hideConnect();
	}
	//Called to switch activities to the first setup
	public void startSetup(View v){
		navigateToClass(v.getContext(), SetupOne.class);
	}
}
