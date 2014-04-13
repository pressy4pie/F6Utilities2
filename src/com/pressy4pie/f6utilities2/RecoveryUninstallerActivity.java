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


public class RecoveryUninstallerActivity extends Activity {
	ProgressDialog barProgressDialog;
	Handler updateBarHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recovery_uninstaller);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recovery_uninstaller, menu);
		return true;

		
	}
	
	void install(){
		//we have to make sure the right aboot is used first...
		String cmd_aboot = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/recovery/aboot.img_e of=/dev/block/platform/msm_sdcc.1/by-name/aboot";
		
		//where the install will happen...
		//im not using loki_flash because dd works fine and it eliminates aboot issues
		
		String cmd_install = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/recovery/recovery.img.stock of=/dev/block/platform/msm_sdcc.1/by-name/recovery";
		
		root_tools.execute(cmd_aboot);
		root_tools.execute(cmd_install);
		
		Context context = getApplicationContext();
		CharSequence text = "Stock Recovery Restored!";
		int duration = Toast.LENGTH_SHORT;
		Toast success = Toast.makeText(context, text, duration);
		success.show();
		
	}
	
    //the method to start the restore.
	public void uninstaller(View view) {
		final ProgressDialog RingProgressDialog = ProgressDialog.show(RecoveryUninstallerActivity.this, "Please Wait", "Restoring Recovery", true);
		RingProgressDialog.setCancelable(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//this is the runnable stuff for the progress bar
					install();
				} catch (Exception e) {
					Log.e("Recovery Uninstaller", "something went wrong");
				}
				RingProgressDialog.dismiss();
			
		}
	}).start();	
	}

}
