package com.rozzles.camera;

import java.util.Set;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BlueControl extends ListActivity {
public String mac;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  super.onCreate(savedInstanceState);

  ArrayAdapter<String> btArrayAdapter 
    = new ArrayAdapter<String>(this,
             android.R.layout.simple_list_item_1);

  BluetoothAdapter bluetoothAdapter 
   = BluetoothAdapter.getDefaultAdapter();
  Set<BluetoothDevice> pairedDevices 
   = bluetoothAdapter.getBondedDevices();

  if (pairedDevices.size() > 0) {
      for (BluetoothDevice device : pairedDevices) {
       String deviceBTName = device.getName();
       mac = device.getAddress();
       btArrayAdapter.add(deviceBTName + "\n" 
         + mac);
      }
  }
  setListAdapter(btArrayAdapter);

 }

 @Override
 protected void onListItemClick(ListView l, View v, int position, long id) {
  // TODO Auto-generated method stub
  super.onListItemClick(l, v, position, id);
  System.out.println(mac);
     Intent intent = new Intent();
     setResult(RESULT_OK, intent);
     finish();
 }

}