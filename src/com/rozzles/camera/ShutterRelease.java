package com.rozzles.camera;

import com.rozzles.camera.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class ShutterRelease extends Activity {

public int prog = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shutter_release);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		SeekBar seekBar = (SeekBar)findViewById(R.id.TimeDelaySeek); 
		final TextView seekBarValue = (TextView)findViewById(R.id.delayIntView);
		
	    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

	   @Override 
	   public void onProgressChanged(SeekBar seekBar, int progress, 
	     boolean fromUser) { 
	    // TODO Auto-generated method stub 
		   				
	    seekBarValue.setText(String.valueOf(progress+ " seconds"));
	    prog = progress;
	   };

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

	public boolean onOptionsItemSelected(MenuItem item){
	    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
	    startActivityForResult(myIntent, 0);
	    return true;
	}
	
	public void CaptureClick(View view) {
		BlueComms sendMsg = new BlueComms();
		sendMsg.sendMsg("1," + prog + ",0,0,0,0,0,0,0,0");
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shutter_release, menu);
		return true;
	}

}
