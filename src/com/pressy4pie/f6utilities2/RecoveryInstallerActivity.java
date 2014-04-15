package com.pressy4pie.f6utilities2;

import java.io.File;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import com.pressy4pie.f6utilities2.root_tools;


public class RecoveryInstallerActivity extends Activity {
	ProgressDialog barProgressDialog;
	Handler updateBarHandler;
	String tagname = "Recovery Install";

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
		String cmd_aboot = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/recovery/aboot.img_e of=/dev/block/platform/msm_sdcc.1/by-name/aboot";
		String cmd_install = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/recovery/pressy4pie-cwm-unofficial-stock-data.lok of=/dev/block/platform/msm_sdcc.1/by-name/recovery";
		
		root_tools.execute(cmd_aboot);
		root_tools.execute(cmd_install);
		
		//this toast will not show for some reason
		/*
		Context context = getApplicationContext();
		CharSequence text = "CWM has been installed!";
		int duration = Toast.LENGTH_SHORT;
		Toast success = Toast.makeText(context, text, duration);
		success.show();
		*/	
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
