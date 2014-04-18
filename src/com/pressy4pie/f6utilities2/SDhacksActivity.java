package com.pressy4pie.f6utilities2;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.pressy4pie.f6utilities2.root_tools;

public class SDhacksActivity extends Activity {
	String tagname = "SDhacks";
	ProgressDialog barProgressDialog;
	Handler updateBarHandler;
	boolean finish = false;
	String dir = Environment.getExternalStorageDirectory() + "/F6Utilities2/recovery";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sdhacks);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sdhacks, menu);
		return true;
	}
	
	public void install() {
		//check for partitioned sdcard
		boolean check = root_tools.fileExists("/dev/block/mmcblk1p2");
		if(check == true) {
			//if the card appears to be fine
			String kernelInstall = "busybox dd if=" + dir + "/pressy4pie-sdhack-boot.lok" + " of=/dev/block/platform/msm_sdcc.1/by-name/boot";
			root_tools.execute(kernelInstall);
			Log.i(tagname, "The sdcard hack boot image was installed.");
			
			String recoveryInstall = "busybox dd if=" + dir + "/pressy4pie-cwm-unofficial-sd-data.lok" + " of=/dev/block/platform/msm_sdcc.1/by-name/recovery";
			root_tools.execute(recoveryInstall);
			Log.i(tagname, "The Custom CWM was also installed");
			finish = true;
		}
		
		else {
			//the card isnt formatted correctly
			Log.i(tagname, "The SD card does not appear to be formatted correctly.");
			finish = false;
		}
	}
	
    //the method to start the root.
	public void start(View view) {
		final ProgressDialog RingProgressDialog = ProgressDialog.show(SDhacksActivity.this, "Please Wait", "Installing SD Card Hack", true);
		RingProgressDialog.setCancelable(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					install();
				} catch (Exception e) {
					Log.e(tagname, "something went wrong");
				}
				RingProgressDialog.dismiss();
		}
	}).start();	
    }
	
	
}
