/*
 * Rory Crispin --rorycrispin.co.uk -- rozzles.com
 * 
 * Distributed under theAttribution-NonCommercial-ShareAlike 4.0 International
 * License, full conditions can be found here: 
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *   
 *   This is free software, and you are welcome to redistribute it
 *   under certain conditions;
 *   
 *   Go crazy,
 *   Rozz xx 
 * 
 */
package com.rozzles.torocam;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.rozzles.torocam.core.toroCamTrigger;

public class HDRLapse extends toroCamTrigger {

	int delay;
	int spin = 1;
	int secs = 0;
	int mins = 0;
	int hurs = 0;
	public int shotsvar;

	float sOneVal;
	float sTwoVal;
	float sThreeVal;

	SeekBar sOneSeek = null;
	SeekBar sTwoSeek = null;
	SeekBar sThreeSeek = null;
	SeekBar sShotsSeek = null;
	SeekBar sDelaySeek = null;

	TextView sOneLabel = null;
	TextView sTwoLabel = null;
	TextView sThreeLabel = null;
	TextView sShotsLabel = null;
	TextView sDelayLabel = null;
	CheckBox hdrCheck = null;

	CheckBox lapseBox = null;

	Spinner spinner = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hdrlapse);
		super.onCreate(savedInstanceState);

		sOneSeek = (SeekBar) findViewById(R.id.sOneSeek);
		sTwoSeek = (SeekBar) findViewById(R.id.sTwoSeek);
		sThreeSeek = (SeekBar) findViewById(R.id.sThreeSeek);
		sShotsSeek = (SeekBar) findViewById(R.id.sShotSeek);
		sDelaySeek = (SeekBar) findViewById(R.id.sDelSeek);

		sOneLabel = (TextView) findViewById(R.id.sOneLabel);
		sTwoLabel = (TextView) findViewById(R.id.sTwoLabel);
		sThreeLabel = (TextView) findViewById(R.id.sThreeLabel);
		sShotsLabel = (TextView) findViewById(R.id.sShotLabel);
		sDelayLabel = (TextView) findViewById(R.id.sDelLabel);
		spinner = (Spinner) findViewById(R.id.sDelSpinner);
		hdrCheck = (CheckBox) findViewById(R.id.hdrCheck);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this,

				R.array.timeArray, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				java.lang.System.out.println(String.valueOf(arg3));
				if (arg3 == 0) {
					spin = 1;
				} else if (arg3 == 1) {
					spin = 60;
				} else if (arg3 == 2) {
					spin = 3600;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		sOneSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				sOneVal = (float) arg1 / 100;
				sOneLabel.setText(String.valueOf(sOneVal + "s"));

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

		});
		sTwoSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				sTwoVal = (float) arg1 / 100;
				sTwoLabel.setText(String.valueOf(sTwoVal + "s"));

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

		});
		sThreeSeek
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar arg0, int arg1,
							boolean arg2) {
						sThreeVal = (float) arg1 / 100;
						sThreeLabel.setText(String.valueOf(sThreeVal + "s"));

					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

				});
		sShotsSeek
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar arg0, int arg1,
							boolean arg2) {
						sShotsLabel.setText(String.valueOf(arg1 + "Shots"));

					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

				});
		sDelaySeek
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar arg0, int arg1,
							boolean arg2) {
						sDelayLabel.setText(String.valueOf(arg1 + ""));

					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

				});

		hdrCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1 == false) {
					sShotsSeek.setEnabled(false);
					sDelaySeek.setEnabled(false);
					spinner.setEnabled(false);
				} else {
					sShotsSeek.setEnabled(true);
					sDelaySeek.setEnabled(true);
					spinner.setEnabled(true);
				}
				// TODO Auto-generated method stub

			}

		});

	}

	@Override
	public void sendCapture() {
		if (hdrCheck.isChecked() == false) {
			shotsvar = 1;
		} else {
			shotsvar = sShotsSeek.getProgress();
		}
		delayParse();
		sendToroCamMessage("5," + secs + "," + mins + "," + hurs + ","
				+ (shotsvar) + "," + (sOneSeek.getProgress() * 10) + ","
				+ (sTwoSeek.getProgress() * 10) + ","
				+ (sThreeSeek.getProgress() * 10) + ",500,0!");
		clearTimes();
	}

	public void delayParse() {
		if (spin == 1) {
			secs = sDelaySeek.getProgress();
		} else if (spin == 60) {
			mins = sDelaySeek.getProgress();
		} else if (spin == 3600) {
			hurs = sDelaySeek.getProgress();
		} else {
			System.out.println("Spin value empty");
		}
	}

	public void clearTimes() {
		secs = 0;
		mins = 0;
		hurs = 0;
	}

}
