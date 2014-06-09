
package com.rozzles.torocam;

import java.util.List;

import com.rozzles.torocam.core.ResizableCameraPreview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import com.rozzles.torocam.core.CameraPreview;

public class CameraView extends Activity implements AdapterView.OnItemSelectedListener {
	private ResizableCameraPreview mPreview;
	private ArrayAdapter<String> mAdapter;
	private RelativeLayout mLayout;
	private int mCameraId = 0;
	float[] touchLocation = new float[2];
	boolean frontCamera = false;
	ImageView focusRing;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide status-bar
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Hide title-bar, must be before setContentView
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.cam_view);
		
		focusRing = (ImageView) findViewById(R.id.focusRing);
		
		// Spinner for preview sizes
		Spinner spinnerSize = (Spinner) findViewById(R.id.spinner_size);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSize.setAdapter(mAdapter);
		spinnerSize.setOnItemSelectedListener(this);

		mLayout = (RelativeLayout) findViewById(R.id.layout);

		mLayout.setOnTouchListener(new OnTouchListener() {      
			@Override
			public boolean onTouch(View v, MotionEvent touchEvent) {
				mPreview.autoFocus();
				touchLocation[0] = touchEvent.getX();
				touchLocation[1] = touchEvent.getY();
				createAutoFocusIndicator(touchLocation);
				return true;
			}

		});
		ImageButton switchCamera = (ImageButton) findViewById(R.id.switchCameraButton);
		switchCamera.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View arg0) {

				mPreview.stop();
				mLayout.removeView(mPreview);
				if (!frontCamera){
					mCameraId = 1;
					frontCamera = true;
				} else {
					mCameraId = 0;
					frontCamera = false;
				}

				createCameraPreview();


			}

		});
	}


	@SuppressLint("NewApi")
	private void createAutoFocusIndicator(float[] touchLocation) {
		try {
			focusRing.setX(touchLocation[0]);
			focusRing.setY(touchLocation[1]);
			focusRing.setVisibility(View.VISIBLE);
			
			
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			    @Override
			    public void run() {
			    	focusRing.setVisibility(View.INVISIBLE);
			    	
			    }
			}, 500);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}    


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Log.w("CameraPreviewTestActivity", "onItemSelected invoked");
		Log.w("CameraPreviewTestActivity", "position: " + position);
		Log.w("CameraPreviewTestActivity", "parent.getId(): " + parent.getId());
		switch (parent.getId()) {
		case R.id.spinner_size:
			Rect rect = new Rect();
			mLayout.getDrawingRect(rect);

			if (0 == position) { // "Auto" selected
				mPreview.surfaceChanged(null, 0, rect.width(), rect.height());
			} else {
				mPreview.setPreviewSize(position - 1, rect.width(), rect.height());
			}
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// do nothing
	}

	@Override
	protected void onResume() {
		super.onResume();
		createCameraPreview();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mPreview.stop();
		mLayout.removeView(mPreview);
		mPreview = null;
	}



	private void createCameraPreview() {
		// Set the second argument by your choice.
		// Usually, 0 for back-facing camera, 1 for front-facing camera.
		// If the OS is pre-gingerbreak, this does not have any effect.
		mPreview = new ResizableCameraPreview(this, mCameraId, CameraPreview.LayoutMode.FitToParent, false);
		LayoutParams previewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mLayout.addView(mPreview, 0, previewLayoutParams);

		mAdapter.clear();
		mAdapter.add("Auto");
		List<Camera.Size> sizes = mPreview.getSupportedPreivewSizes();
		for (Camera.Size size : sizes) {
			mAdapter.add(size.width + " x " + size.height);
		}
	}

	public void captureClick(View v){
		sendCapture();
	}
	public void sendCapture(){
		mPreview.takePhoto();
	}

}
