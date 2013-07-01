package com.rozzles.cam;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Timelapse extends Activity {
	public long millis;
	public int shots;
	int delay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelapse);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		final SeekBar delaySeek = (SeekBar)findViewById(R.id.TimeDelaySeek); 
		final SeekBar shotsSeek = (SeekBar)findViewById(R.id.ShotsSeek); 
		Spinner spinner = (Spinner) findViewById(R.id.TimeSpinner);
		final TextView shotsText = (TextView)findViewById(R.id.shotView);
		final TextView delayView = (TextView)findViewById(R.id.TimelapseDelayView);
		final TextView totalTime = (TextView)findViewById(R.id.totalTime);
		SendMessage sendMsg = new SendMessage();
		final TimeParse timeparse = new TimeParse();
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				
		        R.array.timeArray, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
	    shotsSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

	   @Override 
	   public void onProgressChanged(SeekBar shotsSeek, int progress, 
	     boolean fromUser) { 
		  shotsText.setText(String.valueOf(progress));
		  shots = progress;
		  totalTime(totalTime, timeparse, delay, shots);
	   };

	   @Override 
	   public void onStartTrackingTouch(SeekBar seekBar) { 
	   } 

	   @Override 
	   public void onStopTrackingTouch(SeekBar seekBar) { 
	   } 
	     });
	    
	    delaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

	 	   @Override 
	 	   public void onProgressChanged(SeekBar delaySeek, int progress, 
	 	     boolean fromUser) { 
	 		   delayView.setText(String.valueOf(progress));
	 		   delay = progress;
	 		  totalTime(totalTime, timeparse, delay, shots);
	 	   };

	 	   @Override 
	 	   public void onStartTrackingTouch(SeekBar seekBar) { 
	 	   } 

	 	   @Override 
	 	   public void onStopTrackingTouch(SeekBar seekBar) { 
	 	   } 
	 	     });
	   
    }
public void totalTime(TextView totalTime, TimeParse timeparse, int delay, int shots){

	  millis = delay * shots;
	  //System.out.println(millis);
	  totalTime.setText(TimeParse.getDurationBreakdown(millis));
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timelapse, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
	    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
	    startActivityForResult(myIntent, 0);
	    return true;
	}
    
}
