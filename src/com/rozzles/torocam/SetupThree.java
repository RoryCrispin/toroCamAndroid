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
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SetupThree extends Activity {

	boolean mBounded;
	BlueComms mServer;
	
	Button button;
	
	Animation animFadein;
	Animation animFadeout;
	
	ImageView connectImg;
	ImageView errorImg;
	LinearLayout linButtons;
	
	Intent homeIntent;
	
	public static final String TOROCAM_PREFS = "AndCamPreferences";
	
	Boolean completedSetup;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_three);
		button = (Button) findViewById(R.id.dialogTryAgainButton);
		
	     //Starts the BlueComms service to connect to the deice
			
			//Init the fonts from the assets folder
			Typeface robotoLI = Typeface.createFromAsset(getAssets(),
	                "fonts/robotoLI.otf");
			Typeface robotoL = Typeface.createFromAsset(getAssets(),
	                "fonts/robotoLI.otf");
			//Init the two text views for to set the fonts to ultralight, so hipster
			TextView appTitle = (TextView) findViewById(R.id.s1Text);
			TextView wlcmTo = (TextView) findViewById(R.id.dialog_text);
			//Set the fonts to roboto
	        appTitle.setTypeface(robotoLI);
	        wlcmTo.setTypeface(robotoL);
	        
	        linButtons = (LinearLayout) findViewById(R.id.LinButtons);
	        errorImg = (ImageView) findViewById(R.id.errorImg);
	        connectImg = (ImageView) findViewById(R.id.connectView);
	        
	        animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
	        animFadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
	        
	        homeIntent = new Intent(this, FlatHome.class); 
	}
	
	protected void onStart(){
		super.onStart();
		Intent mIntent = new Intent(this, BlueComms.class); 
		bindService(mIntent, mConnection, BIND_AUTO_CREATE); 
		//mServer.Connect();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup_three, menu);
		return true;
	}
	@Override 
	public void onWindowFocusChanged (boolean hasFocus){
		if(hasFocus) {
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			  @Override
			  public void run() {
			   if(mServer.Connect()){
				   //Called when the device connects ok
				   completedSetup = true;
				   SharedPreferences settings = getSharedPreferences(TOROCAM_PREFS, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("completedSetup", true);
					editor.commit();
					
				   try    {           
				         startActivityForResult(homeIntent, 0);
				         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
				     } catch(Exception ex) {
				    }
			   } else {
				   //Called if the device cannot connect
				   //Fades out the connect image and fades in the error image and buttons
				  connectImg.startAnimation(animFadeout);
				  linButtons.startAnimation(animFadein);
				  errorImg.startAnimation(animFadein);
				  errorImg.setVisibility(View.VISIBLE);
				 linButtons.setVisibility(View.VISIBLE);
			   }
			  }
			}, 100);
		}
	}
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
	}
	public void flatHome(View view){
		try    {           
	         Intent newIntent = new Intent(view.getContext(), FlatHome.class);    
	         startActivityForResult(newIntent, 0);
	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
	     } catch(Exception ex) {
	     }
	}
	public void troubleshootClick(View v){
		System.out.println("Help");
		//TODO Help docs
	}
	public void retryClick(View v){
		try    {           
	         Intent newIntent = new Intent(v.getContext(), SetupTwo.class);    
	         startActivityForResult(newIntent, 0);
	         overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);       
	     } catch(Exception ex) {
	    	 //I don't handle catches because I'm lazy 
	     }
		
	}
}
