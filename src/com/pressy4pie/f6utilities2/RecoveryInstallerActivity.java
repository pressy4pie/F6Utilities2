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


public class RecoveryInstallerActivity extends Activity {
	ProgressDialog barProgressDialog;
	Handler updateBarHandler;
	String tagname = "Recovery Install";
	String dir = Environment.getExternalStorageDirectory() + "/F6Utilities2/recovery";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recovery_installer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recovery_installer, menu);
		return true;

		
	}
	
	void install(){
		//we have to make sure the right aboot is used first...
		String cmd_aboot = "busybox dd if=" + dir + "/aboot.img_e" + " of=/dev/block/platform/msm_sdcc.1/by-name/aboot";
		String cmd_recovery = "busybox dd if="+ dir + "/pressy4pie-cwm-unofficial-stock-data.lok" + " of=/dev/block/platform/msm_sdcc.1/by-name/recovery";
		
		root_tools.execute(cmd_aboot);
		root_tools.execute(cmd_recovery);
		
		Log.i(tagname, "CWM was Installed");
	}
	
    //The onclick that gets launched
	public void start(View view) {
		final ProgressDialog RingProgressDialog = ProgressDialog.show(RecoveryInstallerActivity.this, "Please Wait", "Installing CWM", true);
		RingProgressDialog.setCancelable(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//this is the runnable stuff for the progress bar
					install();
				} catch (Exception e) {
					Log.e(tagname, "something went wrong");
				}
				RingProgressDialog.dismiss();
		}
	}).start();	
	}
}
