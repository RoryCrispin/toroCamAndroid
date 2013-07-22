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

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

<<<<<<< HEAD
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class BlueComms extends Service{
=======
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class BlueComms {
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10

	public boolean isConnected;
	public static final String TAG = "CameraRemote";
	public BluetoothAdapter mBluetoothAdapter = null;
	public BluetoothSocket btSocket = null;
	public OutputStream outStream = null;
<<<<<<< HEAD
	//public static String address = "00:13:04:07:08:28"; //TODO Un hardcode this
	public static String address = "20:13:05:06:31:35";
	public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
=======
	public static String address = "00:13:04:07:08:28"; //TODO Un hardcode this
	public static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
	// private InputStream inStream = null; //TODO Add input stream listener
	Handler handler = new Handler();
	byte delimiter = 10;
	boolean stopWorker = false;
	int readBufferPosition = 0;
	byte[] readBuffer = new byte[1024];
	int connectedActv = 0;
<<<<<<< HEAD
	IBinder mBinder = new LocalBinder();
	public void onCreate(){

	}
	public class LocalBinder extends Binder {
		public BlueComms getServerInstance() {
			return BlueComms.this;
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	public void onDestroy(){
		if (isConnected){
			try {
				btSocket.close();
				isConnected = false;
			} catch (IOException e) {
				System.out.println("Error: " + String.valueOf(e));
			}
		} else {
			System.out.println("Kill command issued with closed connection");
		}	
	}
	public int onStartCommand(Intent intent, int flags, int startId){

		System.out.println("***Trace: CheckBt()");
		CheckBt();
		System.out.println("***Trace: Checked");
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		Log.d(TAG, "Connecting to ... " + device);
		mBluetoothAdapter.cancelDiscovery();
		try {
			btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
			btSocket.connect();
			outStream = btSocket.getOutputStream();
			Log.d(TAG, "Connection made.");
			isConnected = true;
			return START_STICKY;

		} catch (IOException e) {
			try {
				btSocket.close();
			} catch (IOException e2) {
				Log.d(TAG, "Unable to end the connection");
				return 0;
			}
			Log.d(TAG, "Socket creation failed");
		}

		return START_STICKY;
	}
	public void sendData(String data) {
		CheckBt();
=======

	public void sendMsg(String data, int recvAct) {
		System.out.println(String.valueOf(isConnected + " " + connectedActv
				+ " " + recvAct + " " + data));
		if (isConnected == true) {
			if (recvAct == connectedActv) {
				sendData(data, recvAct);
			} else {
				killBT();
				sendMsg(data, recvAct);
			}
		} else {
			connectDevice(recvAct);
			sendData(data, recvAct);
		}
	}

	public void sendData(String data, int recvAct) {
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		try {
			outStream = btSocket.getOutputStream();
		} catch (IOException e) {
			Log.d(TAG, "Bug BEFORE Sending stuff", e);
		}

		String message = data;
<<<<<<< HEAD
		System.out.println("**DATA SENT: " + message + "**");
=======
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		byte[] msgBuffer = message.getBytes();

		try {
			outStream.write(msgBuffer);
		} catch (IOException e) {
			Log.d(TAG, "Bug while sending stuff", e);
		}
	}

	private void CheckBt() {
<<<<<<< HEAD

=======
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (!mBluetoothAdapter.isEnabled()) {
			System.out.println("Bt dsbld");
		}

		if (mBluetoothAdapter == null) {
			System.out.println("Bt null");
		}
	}

<<<<<<< HEAD
	public void killBT() {
		if (isConnected){
=======
	public boolean connectDevice(int recvAct) {
		CheckBt();
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		Log.d(TAG, "Connecting to ... " + device);
		mBluetoothAdapter.cancelDiscovery();
		try {
			btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
			btSocket.connect();
			outStream = btSocket.getOutputStream();
			Log.d(TAG, "Connection made.");
			isConnected = true;
			connectedActv = recvAct;
			return true;

		} catch (IOException e) {
			try {
				btSocket.close();
			} catch (IOException e2) {
				Log.d(TAG, "Unable to end the connection");
				return false;
			}
			Log.d(TAG, "Socket creation failed");
		}
		return false;

	}

	public void killBT() {
		if (isConnected = true){
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
			try {
				btSocket.close();
				isConnected = false;
			} catch (IOException e) {
				System.out.println("Error: " + String.valueOf(e));
			}
<<<<<<< HEAD
		} else {
			System.out.println("Kill command issued with closed connection");
		}
	}


=======
		}
	}
>>>>>>> 85e50f9ac3bc51bb59d8d09786791212971d2e10
}