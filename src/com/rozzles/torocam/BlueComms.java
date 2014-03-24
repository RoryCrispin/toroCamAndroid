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
package com.rozzles.torocam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BlueComms extends Service{
	
	public BluetoothAdapter mBluetoothAdapter = null; 
	public BluetoothSocket btSocket = null;
	public OutputStream outStream = null;
	
	public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//TODO useless delete this and debug
	private InputStream inStream = null; //TODO Add input stream listener
	Handler handler = new Handler();
	
	byte delimiter = 10;
	byte[] readBuffer = new byte[1024];
	
	boolean stopWorker = false;
	boolean isConnected;
	
	int readBufferPosition = 0;
	int connectedActv = 0;
	
	String address;
	public static final String TAG = "CameraRemote"; //Debug stuff
	IBinder mBinder = new LocalBinder();
	SetupThree setupThree = new SetupThree();
	
	public static final String TOROCAM_PREFS = "AndCamPreferences";
	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 * Caled when the service is created, probably useless TODO debug this 
	 */
	public void onCreate(){
		restoreMac();
		
	}
	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	/*
	 * This is used to bind classes to the service so they can communicate
	 */
	public class LocalBinder extends Binder {
		public BlueComms getServerInstance() {
			return BlueComms.this;
		}
	}
	public void readStream(){
		try {
			while (inStream.available() > 0) {
			
				System.out.println(inStream.available());
				
				BufferedReader r = new BufferedReader(new InputStreamReader(inStream));
				StringBuilder total = new StringBuilder();
				String line;
				while ((line = r.readLine()) != null) {
				    total.append(line);
				System.out.println(total.toString());
				}
			}
			/*
			 Thread thread = new Thread()
{
    @Override
    public void run() {
        try {
            while(true) {
                sleep(1000);
                handler.post(r);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
};
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 * This retrieves the mac address from the shared preference set in the set-up utility
	 */
	public void restoreMac() {
		SharedPreferences prefs = getSharedPreferences(TOROCAM_PREFS, 0);
		address = prefs.getString("macaddress", "0");
		System.out.println("PULLING PREF :" + address);
	}
	/*
	 * This isn't bluetooth, but BlueComms is a convenient class that all the activities
	 * are linked to so I'm borrowing it to grab the state of a shared pref 'advFucntions'
	 * Sorry, but I'm not sorry. 
	 */
	public boolean advFunctions(){
		SharedPreferences prefs = getSharedPreferences(TOROCAM_PREFS, 0);
		 return(prefs.getBoolean("advFunctions", false));
	}
	
	/*
	 *This is called by the Setup utility to send the Mac address selected
	 *to BlueComms and save it in the shared preference  
	 */
	public void recvMac(String macAddress){
		address = macAddress;
		SharedPreferences settings = getSharedPreferences(TOROCAM_PREFS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("macaddress", address);
		System.out.println("PHSHING PREF: " + address);
		editor.commit();
	}
	

	/*
	 *This method is used as an assurance to check that the bluetooth
	 *hardware exists and is turned on, it also sets the mBluetoothAdapter
	 *which is essential to the connection process, CheckBT() must be called
	 *before connecting
	 */
	private void CheckBt() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (!mBluetoothAdapter.isEnabled()) {
			System.out.println("Bluetooth is disabled");
		}

		if (mBluetoothAdapter == null) {
			System.out.println("Device does not have bluetooth");
		}
	}
	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 * 
	 *This is called when the service is started, it connects to the device and calls START_STICKY
	 *which keeps the service running. Avoid changing this stuff if you can.
	 */
	public int onStartCommand(Intent intent, int flags, int startId){
		//Connect();
		return START_STICKY;//Keeps the service running 
	}
	
	public boolean Connect(){
		restoreMac();
		CheckBt(); //Check bluetooth exists and is enabled & set mBluetoothAdapter
		try {
			BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
			
		Log.d(TAG, "Connecting to ... " + device);
		mBluetoothAdapter.cancelDiscovery();
		
			btSocket = device.createRfcommSocketToServiceRecord(MY_UUID); //This line doesn't do anything really, I should delete it but I'm scared
			btSocket.connect(); //Connect to the device
			outStream = btSocket.getOutputStream();
			inStream = btSocket.getInputStream();//Initialize the output stream so data can be sent to the device later
			Toast.makeText(getApplicationContext(), "Connetion Made!", Toast.LENGTH_SHORT).show();
		
			isConnected = true;
			
			return true;
			//TODO make a persistent notification while the service is open
			
		
		} catch (IOException e) {
			try {
				btSocket.close();
			} catch (IOException e2) {
				Log.d(TAG, "Unable to end the connection");
			}
			
			Log.d(TAG, "Socket creation failed");
			return false;
		}
	}
	/*
	 * This method is used to send data to the bluetooth device device 
	 */
	public void sendData(String data) {
		try {
			outStream = btSocket.getOutputStream();
		} catch (IOException e) {
			Log.d(TAG, "Bug BEFORE Sending stuff", e);
		}
		System.out.println("**DATA SENT: " + data + "**");
		byte[] msgBuffer = data.getBytes();

		try {
			outStream.write(msgBuffer);
		} catch (IOException e) {
			Log.d(TAG, "Bug while sending stuff", e);
		}
	}

	/*
	 *This method is used to kill the Bluetooth connection to the device,
	 *Sometimes it is called even though the device is already disconnected
	 *The if statement ensures it doesn't cause any crashes when using the app
	 *without a connected device
	 */
	public void killBT() {
		if (isConnected){
			try {
				btSocket.close();
				isConnected = false;
			} catch (IOException e) {
				System.out.println("Error: " + String.valueOf(e));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 * This is called when the service is destroyed, it's purpose is to kill the bluetooth connection
	 * and clean up afterwards
	 */
	public void onDestroy(){
		SharedPreferences settings = getSharedPreferences(TOROCAM_PREFS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("macaddress", address);//TODO I'm not sure if this is deprecated yet
		System.out.println("PHSHING PREF: " + address);
		editor.commit();
		killBT();
	}
}
