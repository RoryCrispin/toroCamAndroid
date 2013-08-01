package com.rozzles.camera;
import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SetupTwo extends Activity {

	ListView listView = null;
	ArrayAdapter<String> list;

	boolean mBounded;

	BlueComms mServer;

	public String mac;
	String name;
	String[] macAddressArray;  
	String[] nameArray;

	public static final String PREFS_NAME = "AndCamPreferences";

	public void onCreate(Bundle savedInstanceState){
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_two);

		listView = (ListView) findViewById(R.id.pairedList);

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
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("macaddress", macAddressArray[ ((int) id)+ 1]);
				editor.putString("devicename", nameArray[ ((int) id)+ 1]);
				editor.commit();
				//mServer.recvMac(macAddressArray[ ((int) id)+ 1]);
			}
		});
	}

}