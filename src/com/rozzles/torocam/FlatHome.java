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



import com.rozzles.torocam.R;
import com.rozzles.torocam.BlueComms.LocalBinder;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
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



	boolean skipSetup;
	String[] myStringArray = {"Help","Power Off"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

	/*
	 * This method is called to check the shared preference to see if the user
	 * has already run setup, if they have it returns true, otherwise false
	 */
	public boolean checkSetup(){
		SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor=saved_values.edit();

		SharedPreferences settings = getSharedPreferences(TOROCAM_PREFS, 0);


		if (settings.getBoolean("completedSetup", false)){
			//User has already completed setup
			return true;
		} else if (settings.getBoolean("skipSetup", false)){
			//User has skipped setup
			skipSetup = true;
			return true;
		} else {
			//Setup not run
			return false;
		}	
	}

	/*
	 * Set of simple methods to forward the user to the appropriate 
	 * activities for different functions
	 */

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

	public void connectClick(View v){
		hideConnect();
	}
	//Called to switch activities to the first setup
	public void startSetup(View v){
		navigateToClass(v.getContext(), SetupOne.class);
	}
}
