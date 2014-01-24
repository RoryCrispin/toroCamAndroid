package com.rozzles.torocam;

import com.rozzles.torocam.R;
import com.rozzles.torocam.BlueComms.LocalBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
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


public class HDRLapse extends Activity {
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
	}

	int delay;
	int spin = 1;
	int secs = 0;
	int mins = 0; 
	int hurs = 0;
	public int shotsvar;
	
	boolean mBounded;
	BlueComms mServer;
	
	float sOneVal;
	float sTwoVal;
	float sThreeVal;
	
	
	SeekBar sOneSeek = null;
	SeekBar sTwoSeek = null;
	SeekBar sThreeSeek = null;
	SeekBar sShotsSeek = null;
	SeekBar sDelaySeek = null;
	
	TextView sOneLabel = null;
	TextView sTwoLabel = null;
	TextView sThreeLabel = null;
	TextView sShotsLabel = null;
	TextView sDelayLabel = null;
	CheckBox hdrCheck = null;
	
	CheckBox lapseBox = null;
	
	Spinner spinner = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hdrlapse);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/robotoLI.otf");
		TextView tv = (TextView) findViewById(R.id.dialog_title);
		tv.setTypeface(tf);

		
		sOneSeek  = (SeekBar) findViewById(R.id.sOneSeek);
		sTwoSeek  = (SeekBar) findViewById(R.id.sTwoSeek);
		sThreeSeek  = (SeekBar) findViewById(R.id.sThreeSeek);
		sShotsSeek  = (SeekBar) findViewById(R.id.sShotSeek);
		sDelaySeek  = (SeekBar) findViewById(R.id.sDelSeek);
		
		sOneLabel  = (TextView) findViewById(R.id.sOneLabel);
		sTwoLabel  = (TextView) findViewById(R.id.sTwoLabel);
		sThreeLabel  = (TextView) findViewById(R.id.sThreeLabel);
		sShotsLabel  = (TextView) findViewById(R.id.sShotLabel);
		sDelayLabel  = (TextView) findViewById(R.id.sDelLabel);
		spinner = (Spinner) findViewById(R.id.sDelSpinner);
		hdrCheck = (CheckBox) findViewById(R.id.hdrCheck);
		Intent mIntent = new Intent(this, BlueComms.class);
		bindService(mIntent, mConnection, BIND_AUTO_CREATE);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this,

				R.array.timeArray, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				java.lang.System.out.println(String.valueOf(arg3));
				if (arg3 == 0) {
					spin = 1;
				} else if (arg3 == 1) {
					spin = 60;
				} else if (arg3 == 2) {
					spin = 3600;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		
		sOneSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				sOneVal = (float) arg1 / 100;
				sOneLabel.setText(String.valueOf(sOneVal + "s"));

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
		sTwoSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				sTwoVal = (float) arg1 / 100;
				sTwoLabel.setText(String.valueOf(sTwoVal + "s"));

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
		sThreeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				sThreeVal = (float) arg1 / 100;
				sThreeLabel.setText(String.valueOf(sThreeVal + "s"));
				

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
		sShotsSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				sShotsLabel.setText(String.valueOf(arg1 + "Shots"));

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
		sDelaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				sDelayLabel.setText(String.valueOf(arg1 + ""));
				
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
		
		hdrCheck.setOnCheckedChangeListener(new OnCheckedChangeListener()	{

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1 == false)	{
					sShotsSeek.setEnabled(false);
					sDelaySeek.setEnabled(false);
					spinner.setEnabled(false);
				}	else	{
					sShotsSeek.setEnabled(true);
					sDelaySeek.setEnabled(true);
					spinner.setEnabled(true);
				}
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

	


	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = new Intent(getApplicationContext(), FlatHome.class);
		startActivityForResult(myIntent, 0);
		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
		return true;
	}
	
	
	public void captureClick (View v){
		
		if (hdrCheck.isChecked() == false) {
			shotsvar = 1;
		} else {
			shotsvar = sShotsSeek.getProgress();
		}
		delayParse();
		mServer.sendData("5," + secs +"," + mins + "," + hurs + ","  + (shotsvar) + "," + (sOneSeek.getProgress()*10) + "," + (sTwoSeek.getProgress()*10) + "," + (sThreeSeek.getProgress()*10) + ",500,0!");
		clearTimes();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hdrlapse, menu);
		return true;
	}
	public void delayParse(){
		if (spin == 1){
			secs = sDelaySeek.getProgress();
		} else if (spin == 60){
			mins = sDelaySeek.getProgress();
		} else if (spin == 3600){
			hurs = sDelaySeek.getProgress();
		} else { 
			System.out.println("Spin value empty");
		}
	}
	public void clearTimes(){
		secs = 0;
		mins = 0;
		hurs = 0;
	}

}
