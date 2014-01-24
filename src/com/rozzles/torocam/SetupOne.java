package com.rozzles.torocam;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.rozzles.torocam.R;

public class SetupOne extends Activity {

	public static final String TOROCAM_PREFS = "AndCamPreferences";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Hides the action bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_one);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup_one, menu);
		return true;
	}
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
	}
	//Method called when the user selects the first button
	public void haveSmartBox(View v){
		
	}
	//Skips to the main window 
	public void skipClick(View v){
		//Simple intent to change activities to the Home screen
		   SharedPreferences settings = getSharedPreferences(TOROCAM_PREFS, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("skipSetup", true);
			editor.commit();
			Intent myIntent = new Intent(v.getContext(), FlatHome.class);
			startActivityForResult(myIntent, 0);
	}
	/*
	 *Button click -Continue, creates an intent to switch to the second setup activity and
	 *inserts the custom transitions from res/anim
	 */
	public void continueClick(View v ){
		try    {           
	         Intent newIntent = new Intent(v.getContext(), SetupTwo.class);    
	         startActivityForResult(newIntent, 0);
	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
	     } catch(Exception ex) {
	     }
	}

}
