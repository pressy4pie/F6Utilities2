package com.pressy4pie.f6utilities2;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import com.pressy4pie.f6utilities2.root_tools;

public class SDhacksActivity extends Activity {
	String tagname = "SDhacks";
	boolean vip = root_tools.isVIP();
	boolean connor = root_tools.isPressy4pie();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sdhacks);	
		/*
		if( init() == false )
		{
			Log.e(tagname, "Something failed, Show this log to PressY4pie");
		}
		
		else 
		{
			//init passed
			Log.i(tagname, "Init succeeded. You shall pass.");
		}
		*/	
	}
	
	private boolean checkPartition() {
		return false;
	}
	
	private boolean checkAppsSD() {
		return false;
	}
	
	private boolean init()
	{
		//init the sd stuff. this is still buggy.
		//check for second partition.
		boolean parts;
		boolean appsSD;
		
		parts = checkPartition();
		appsSD = checkAppsSD();
		
		if (parts == true && appsSD == true) {
			return true;
		}
		
		else return false;
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sdhacks, menu);
		return true;
	}
	
	public void start(View view) {
		if(connor){
			//user is perssy4pie
			String kernelInstallConnor = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/recovery/pressy4pie.boot.lok of=/dev/block/platform/msm_sdcc.1/by-name/boot";
			root_tools.execute(kernelInstallConnor);
			Log.i(tagname, "The AWESOME kernel was installed.");
		}
		else if(vip){
			//user is a vip
			String kernelInstallVip = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/recovery/vip.boot.lok of=/dev/block/platform/msm_sdcc.1/by-name/boot";
			root_tools.execute(kernelInstallVip);
			Log.i(tagname, "The vip kernel was installed.");
		}
		
		else {
			String kernelInstallUser = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/recovery/user.boot.lok of=/dev/block/platform/msm_sdcc.1/by-name/boot";
			root_tools.execute(kernelInstallUser);
			Log.i(tagname, "The lame kernel was installed.");
		}
	}
	
	
}
