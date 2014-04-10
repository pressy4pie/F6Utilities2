package com.pressy4pie.f6utilities2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.pressy4pie.f6utilities2.root_tools;

public class SDhacksActivity extends Activity {
	String tagname = "SDhacks";

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
	
	public void start(View view) {
			//user is perssy4pie
			String kernelInstall = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/recovery/pressy4pie.boot.lok of=/dev/block/platform/msm_sdcc.1/by-name/boot";
			root_tools.execute(kernelInstall);
			Log.i(tagname, "The sdcard hack boot image was installed.");
			
			Context context = getApplicationContext();
			CharSequence text = "The sdcard hack boot image was installed.";
			int duration = Toast.LENGTH_SHORT;
			Toast success = Toast.makeText(context, text, duration);
			success.show();
		
	}
	
	
}
