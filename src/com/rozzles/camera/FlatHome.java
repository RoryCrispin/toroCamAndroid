package com.rozzles.camera;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlatHome extends Activity {

	LinearLayout linButtons = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flat_home);
		Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/robotoLI.otf");
		TextView tv = (TextView) findViewById(R.id.appTitle);
        tv.setTypeface(tf);
        
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
	
	public void simpleShootClick(View v) {
		Intent myIntent = new Intent(v.getContext(), ShutterRelease.class);
		startActivityForResult(myIntent, 0);

	}
	public void hdrClick(View v){
		
        linButtons = (LinearLayout) findViewById(R.id.simpleShootStruct);

        linButtons.setVisibility(View.GONE);
	}
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
