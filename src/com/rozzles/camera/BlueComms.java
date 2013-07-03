package com.rozzles.camera;

import com.kinvey.android.activities.BaseConnectActivity;

public class BlueComms extends BaseConnectActivity {

	public void sendMsg(String message) {
		System.out.println(message);
	}

	@Override
	public void onNewMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	public void connectDevice() {
		requestDeviceConnection();
		
	}}