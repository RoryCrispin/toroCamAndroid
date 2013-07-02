package com.rozzles.camera;

import com.rozzles.camera.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Timelapse extends Activity {
	public long millis;
	public int shots;
	int delay;
	public int spin = 1;
	SendMessage sendMsg = new SendMessage();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelapse);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		final SeekBar delaySeek = (SeekBar)findViewById(R.id.TimeDelaySeek); 
		final SeekBar shotsSeek = (SeekBar)findViewById(R.id.ShotsSeek); 
		Spinner spinner = (Spinner) findViewById(R.id.TimeSpinner);
		//final TextView shotsText = (TextView)findViewById(R.id.shotView);
		final TextView delayView = (TextView)findViewById(R.id.TimelapseDelayView);
		final TextView totalTime = (TextView)findViewById(R.id.totalTime);
		final TextView totalShotView = (TextView)findViewById(R.id.totalShots);
		
		final TimeParse timeparse = new TimeParse();
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				
		        R.array.timeArray, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				java.lang.System.out.println(String.valueOf(arg3));
				if(arg3 == 0) {
					spin = 1;
				} else if (arg3 == 1) {
					spin = 60;
				} else if (arg3 == 2) {
					spin = 3600;
				}
				totalTime(totalTime, timeparse, delay, shots, spin);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {			
			}
			
		});
		
	    shotsSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

	   @Override 
	   public void onProgressChanged(SeekBar shotsSeek, int progress, 
	     boolean fromUser) { 
		  totalShotView.setText(progress + " shots");
		  //shotsText.setText(String.valueOf(progress));
		  shots = progress;
		  totalTime(totalTime, timeparse, delay, shots, spin);
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
	 		  totalTime(totalTime, timeparse, delay, shots, spin);
	 	   };

	 	   @Override 
	 	   public void onStartTrackingTouch(SeekBar seekBar) { 
	 	   } 

	 	   @Override 
	 	   public void onStopTrackingTouch(SeekBar seekBar) { 
	 	   } 
	 	     });
	   
    }
public void totalTime(TextView totalTime, TimeParse timeparse, int delay, int shots, int spin){

	  millis = delay * shots * spin;
	  //totalShotView.setText(totalshots + " shots");
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
    
    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        if (on) {
            sendMsg.sendMsg("2,"+delay+","+spin+","+shots+",0,0,0,0,0,0,!");
        } else {
            sendMsg.sendMsg("3,0,0,0,0,0,0,0,0,0,!");
        }
    }
}
