package com.rozzles.camera;

import java.util.ArrayList;

import com.rozzles.camera.BlueComms.LocalBinder;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.preference.PreferenceManager;
import android.preference.Preference;

public class FlatHome extends Activity {
	public static final String TOROCAM_PREFS = "AndCamPreferences";
	SharedPreferences.Editor editor;

	SharedPreferences settings;
	LinearLayout linButtons = null;
	boolean[] advFunctionsState = {false};
	boolean mBounded;
	BlueComms mServer;
	boolean skipSetup;
	String[] myStringArray = {"Help","Power Off"};
	String[] checkboxarraypop = {"test","testtt"};
	Context c;


	Intent mIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		
		settings = getSharedPreferences(TOROCAM_PREFS, 0);
		editor = settings.edit();
		
		
		Intent mIntent = new Intent(this, BlueComms.class); 
		startService(mIntent);
		bindService(mIntent, mConnection, BIND_AUTO_CREATE);
		//Indie helvetica stuff explained in other classes
		setContentView(R.layout.activity_flat_home);
		
		
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/robotoLI.otf");
		TextView tv = (TextView) findViewById(R.id.s1Text);
		tv.setTypeface(tf);

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
	 * (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 * Overrides the back button press and inserts the custom slide transition
	 */
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
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

	public void simpleShootClick(View v) {
		try    {
			Intent newIntent = new Intent(v.getContext(), ShutterRelease.class);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			//I don't handle catches because I'm lazy 
		}
	}

	public void timelapseClick(View v) {
		try    {
			Intent newIntent = new Intent(v.getContext(), Timelapse.class);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			//I don't handle catches because I'm lazy 
		}
	}
	public void lightClick(View v) {
		try    {
			Intent newIntent = new Intent(v.getContext(), LightTrigger.class);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			//I don't handle catches because I'm lazy 
		}
	}
	public void	soundClick(View v) {
		try    {
			Intent newIntent = new Intent(v.getContext(), SoundTrigger.class);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			//I don't handle catches because I'm lazy 
		}
	}
	public void	shakeClick(View v) {
		try    {
			Intent newIntent = new Intent(v.getContext(), ShakeTrigger.class);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			System.out.println(ex);
			//I don't handle catches because I'm lazy 
		}
	}

	public void	dripClick(View v) {
		try    {
			Intent newIntent = new Intent(v.getContext(), DripTrig.class);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			//I don't handle catches because I'm lazy 
		}
	}
	public void	hdrClick(View v) {
		try    {
			Intent newIntent = new Intent(v.getContext(), HDRLapse.class);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			//I don't handle catches because I'm lazy 
		}
	}
	public void	srvClick(View v) {
		try    {
			Intent newIntent = new Intent(v.getContext(), ServoTimelapse.class);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			//I don't handle catches because I'm lazy 
		}
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
				try    {
					Intent newIntent = new Intent(v.getContext(), SetupOne.class);    
					startActivityForResult(newIntent, 0);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
				} catch(Exception ex) {
					//I don't handle catches because I'm lazy 
					System.out.println(ex);
					//Oh wait, yeah I do
				}
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
		try    {
			Intent newIntent = new Intent(v.getContext(), SetupOne.class);    
			startActivityForResult(newIntent, 0);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
		} catch(Exception ex) {
			//I don't handle catches because I'm lazy 
		}
	}
}
