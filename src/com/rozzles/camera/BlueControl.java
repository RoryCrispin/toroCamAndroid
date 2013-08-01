package com.rozzles.camera;

import java.util.Set;

import com.rozzles.camera.BlueComms.LocalBinder;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BlueControl extends ListActivity {
	MainActivity main = new MainActivity();
	boolean mBounded;
	BlueComms mServer;
	public String mac;
	String name;
	ArrayAdapter<String> btArrayAdapter;
	String[] macAddressArray;  
	String[] nameArray;
	public static final String PREFS_NAME = "AndCamPreferences";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Toast.makeText(BlueControl.this, "Pair your device through android settings then select it here", 0).show();
		super.onCreate(savedInstanceState);
		btArrayAdapter 
		= new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);

		Intent mIntent = new Intent(this, BlueComms.class);
	     bindService(mIntent, mConnection, BIND_AUTO_CREATE);
		
	     BluetoothAdapter bluetoothAdapter 
		= BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices 
		= bluetoothAdapter.getBondedDevices();
		macAddressArray = new String[10];
		nameArray 		= new String[10];
		if (pairedDevices.size() > 0) {
			int i=0;
			for (BluetoothDevice device : pairedDevices) {
				i=i+1;
				name = device.getName();
				mac = device.getAddress();
				btArrayAdapter.add(name + "\n" + mac);
				macAddressArray[i] = mac;
				nameArray[i] = name;
			}
		} 
		setListAdapter(btArrayAdapter);
		
		

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
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		System.out.println(String.valueOf(position + id));
		
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		System.out.println("MACARRAY found: " + macAddressArray[ ((int) id)+ 1] + " " + nameArray[ ((int) id)+ 1]);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("macaddress", macAddressArray[ ((int) id)+ 1]);
		editor.putString("devicename", nameArray[ ((int) id)+ 1]);
		editor.commit();
		mServer.recvMac(macAddressArray[ ((int) id)+ 1]);
		unbindService(mConnection);
		finish();
	}

}