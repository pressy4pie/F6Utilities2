package com.pressy4pie.f6utilities2;
//import java.io.File;
/*

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
*/
import java.io.File;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
//import android.content.Context;
import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.pm.PackageManager.NameNotFoundException;
//import android.content.res.AssetManager;
//import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import com.pressy4pie.f6utilities2.root_tools;

public class MainActivity extends Activity {

	//on app creation
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	
	public void recoveryInstaller(View view){
		//goto the recovery installer intent
		Intent intent = new Intent(this, RecoveryInstallerActivity.class);
		startActivity(intent);
	}
	
	public void recoveryUninstaller(View view){
		//goto the recovery uninstaller intent
		Intent intent = new Intent(this, RecoveryUninstallerActivity.class);
		startActivity(intent);
	}
	
	
	
	public void sdhacks(View view){
		//the sdhack installer. This neeeds quite a bit of work
		Intent intent = new Intent(this, SDhacksActivity.class);
		startActivity(intent);
			
	}
	
	public void startroot(View view){
		Intent intent = new Intent(this, RooterActivity.class);
		startActivity(intent);
	}
	
	public void rebooter(View view){
		root_tools.execute("reboot recovery");
	}
	
	

}
