package com.pressy4pie.f6utilities2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class RooterActivity extends Activity {
	ProgressDialog barProgressDialog;
	Handler updateBarHandler;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rooter);
		updateBarHandler = new Handler();

		
        File dir = new File ("/data/data/com.pressy4pie.f6utilities2/saferoot");
        dir.mkdirs();
        //copy the root files
        CopyAssets();
		root_tools.executeAsSH("chmod 0755 /data/data/com.pressy4pie.f6utilities2/saferoot/getroot_finish.sh");
		root_tools.executeAsSH("chmod 0755 /data/data/com.pressy4pie.f6utilities2/saferoot/getroot_begin.sh");
		root_tools.executeAsSH("chmod 0755 /data/data/com.pressy4pie.f6utilities2/saferoot/getroot");
		//root_tools.executeAsSH("chmod 0755 /data/local/tmp");
	}
	
	public void getRoot(){
		//root_tools.executeAsSH("");
		//set up directories && chmod
		root_tools.executeAsSH("/data/data/com.pressy4pie.f6utilities2/saferoot/getroot_begin.sh");
		
		Log.i("getroot", "getroot_begin.sh executed...");
		root_tools.executeAsSH("cp /data/data/com.pressy4pie.f6utilities2/saferoot/su /data/local/tmp ");
		root_tools.executeAsSH("cp /data/data/com.pressy4pie.f6utilities2/saferoot/busybox /data/local/tmp ");
		root_tools.executeAsSH("cp /data/data/com.pressy4pie.f6utilities2/saferoot/getroot /data/local/tmp ");
		root_tools.executeAsSH("cp /data/data/com.pressy4pie.f6utilities2/saferoot/install-recovery.sh /data/local/tmp ");
		
		Log.i("getroot", "getroot and su binaries coppied");
		
		
		//the actual get root
		root_tools.executeAsSH("cd /data/local/tmp && ./getroot");
		Log.i("getroot", "getroot executed...");
		
		//clean up
		root_tools.executeAsSH("/data/data/com.pressy4pie.f6utilities2/saferoot/getroot_finish.sh");
		Log.i("getroot", "getroot_finish.sh executed...");
		
	}
	
	public void start(View view) {
		final ProgressDialog RingProgressDialog = ProgressDialog.show(RooterActivity.this, "Please Wait", "Rooting", true);
		RingProgressDialog.setCancelable(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//stuff goes here...
					//Thread.sleep(10000);
					//root_tools.executeAsSH("/data/data/com.pressy4pie.f6utilities2/saferoot/getroot.sh");
					getRoot();
				} catch (Exception e) {
					Log.e("root", "something went wrong");
				}
				RingProgressDialog.dismiss();
			
		}
	}).start();	
	}
	
	public void launchBarDialog(View view) {
		barProgressDialog = new ProgressDialog(RooterActivity.this);
		
		barProgressDialog.setTitle("Rooting...");
		barProgressDialog.setMessage("Root in Progress...");
		barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
		barProgressDialog.setProgress(0);
		barProgressDialog.setMax(20);
		barProgressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//time consuming task here
					while ( barProgressDialog.getProgress() <= barProgressDialog.getMax() ) {
						Thread.sleep(2000);
						updateBarHandler.post(new Runnable() {
						public void run() {	
								barProgressDialog.incrementProgressBy(2);	
							}
						});
						if ( barProgressDialog.getProgress() == barProgressDialog.getMax() ) {
							barProgressDialog.dismiss();
						}
					}
				} catch (Exception e) {	
				}
			}
		}).start();
	}


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rooter, menu);
		return true;
	}
	
	private void CopyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("saferoot");
        } catch (IOException e) {
            Log.e("Asset Copy", e.getMessage());
        }

        for(String filename : files) {
            System.out.println("File name => "+filename);
            InputStream in = null;
            OutputStream out = null;
            try {
              in = assetManager.open("saferoot/"+filename);   // if files resides inside the "Files" directory itself
              out = new FileOutputStream("/data/data/com.pressy4pie.f6utilities2/saferoot/" + filename);
              copyFile(in, out);
              in.close();
              in = null;
              out.flush();
              out.close();
              out = null;
            } catch(Exception e) {
                Log.e("Asset Copy", e.getMessage());
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }
    




public void startOld(View view){
	Runtime rt = Runtime.getRuntime();
		/*
		Process q = rt.exec(new String("/system/bin/sh chmod 0755 /data/data/com.pressy4pie.f6utilities2/saferoot/getroot.sh"));
		Process p = rt.exec(new String("/data/data/com.pressy4pie.f6utilities2/saferoot/getroot.sh"));
		*/
		
		root_tools.executeAsSH("chmod 0755 /data/data/com.pressy4pie.f6utilities2/saferoot/getroot.sh");
		root_tools.executeAsSH("/data/data/com.pressy4pie.f6utilities2/saferoot/getroot.sh");
}


}


