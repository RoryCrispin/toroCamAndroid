/*
 * Rory Crispin --rorycrispin.co.uk -- rozzles.com
 * 
 * Distributed under the Creative Commons 
 * Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)
 * License, full conditions can be found here: 
 * http://creativecommons.org/licenses/by-sa/3.0/
 *   
 *   This is free software, and you are welcome to redistribute it
 *   under certain conditions;
 *   
 *   Go crazy,
 *   Rozz xx 
 * 
 */
package com.rozzles.camera;

<<<<<<< HEAD
import com.rozzles.camera.BlueComms.LocalBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
=======
import android.os.Bundle;
import android.app.Activity;
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BlueControl extends Activity {
	Button sendButton;
<<<<<<< HEAD
	boolean mBounded;
	BlueComms mServer;
=======
	BlueComms blu = new BlueComms();
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blue_control);
		sendButton = (Button) findViewById(R.id.SendButton);
	}
<<<<<<< HEAD
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
	public void sendClick(View v) {
		final EditText consoleBox = (EditText) findViewById(R.id.messageField);
		mServer.sendData(String.valueOf(consoleBox.getText()));
=======

	public void sendClick(View v) {
		final EditText consoleBox = (EditText) findViewById(R.id.messageField);
		blu.sendMsg(String.valueOf(consoleBox.getText()), 5);
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blue_control, menu);
		return true;
	}
}
