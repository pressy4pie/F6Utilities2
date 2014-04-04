package com.pressy4pie.f6utilities2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pressy4pie.f6utilities2.root_tools;


public class RecoveryUninstallerActivity extends Activity {
	protected boolean mbActive;
    protected ProgressBar mProgressBar;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recovery_uninstaller);
		mProgressBar = (ProgressBar)findViewById(R.id.uninstalling_progress_bar);
		mProgressBar.setVisibility(View.GONE);
		
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recovery_uninstaller, menu);
		return true;
	}
	
	
	public void uninstaller(View view){
		//where the uninstall will happen...
		//this gets treated as an error but actually is not.
		mProgressBar.setProgress(100);
		mProgressBar.setVisibility(View.VISIBLE);


		
		commit();
		
	}
	
	public void commit(){
		String cmd_uninstall = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/recovery/recovery.img.stock of=/dev/block/platform/msm_sdcc.1/by-name/recovery";
		root_tools.execute(cmd_uninstall);
		
		Context context = getApplicationContext();
		CharSequence text = "Recovery has been restored to Stock!";
		int duration = Toast.LENGTH_SHORT;
		Toast success = Toast.makeText(context, text, duration);
		success.show();
		
		
		mProgressBar.setVisibility(View.GONE);
	}

}
