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
import java.util.ArrayList;
import java.util.Set;

import com.rozzles.torocam.R;
import com.rozzles.torocam.BlueComms.LocalBinder;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class SetupTwo extends Activity {

	ListView listView = null;
	ArrayAdapter<String> list;

	boolean mBounded;
	BlueComms mServer;

	public String mac;
	String name;
	String[] macAddressArray;  
	String[] nameArray;
	
	Dialog dialog = null;
	

	public static final String TOROCAM_PREFS = "AndCamPreferences";

	public void onCreate(Bundle savedInstanceState){
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_two);
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connect_dev);
		
		Intent mIntent = new Intent(this, BlueComms.class); 
		startService(mIntent);
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

		listView = (ListView) findViewById(R.id.pairedList);
		//listView.setTypeface(robotoL);

		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

		final ArrayList<String> list = new ArrayList<String>();
		macAddressArray = new String[10];
		nameArray 		= new String[10];
		if (pairedDevices.size() > 0) {
			int i=0;
			for (BluetoothDevice device : pairedDevices) {

				name = device.getName();
				mac = device.getAddress();
				list.add(name + "\n" + mac);
				macAddressArray[i] = mac;
				nameArray[i] = name;
				i=i+1;
			}
		} 
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				System.out.println(String.valueOf(nameArray[position]));
				SharedPreferences settings = getSharedPreferences(TOROCAM_PREFS, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("macaddress", macAddressArray[ ((int) id)]);
				editor.putString("devicename", nameArray[ ((int) id)]);
				editor.commit();
				//mServer.recvMac(macAddressArray[ ((int) id)+ 1]);
				
				try    {           
			         Intent newIntent = new Intent(view.getContext(), SetupThree.class);    
			         startActivityForResult(newIntent, 0);
			         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);        
			     } catch(Exception ex) {
			    }
			    
				
	
				
				
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
 			//mServer.Connect();
 		}
 	};
 	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right);
	}

}
