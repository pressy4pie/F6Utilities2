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

		//create dirs to be copied to
        File dir = new File ("/data/data/com.pressy4pie.f6utilities2/saferoot");
        dir.mkdirs();
        //copy the root files
        CopyAssets();
        
		root_tools.executeAsSH("chmod 0755 /data/data/com.pressy4pie.f6utilities2/saferoot/getroot_finish.sh");
		root_tools.executeAsSH("chmod 0755 /data/data/com.pressy4pie.f6utilities2/saferoot/getroot_begin.sh");
		root_tools.executeAsSH("chmod 0755 /data/data/com.pressy4pie.f6utilities2/saferoot/getroot");
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rooter, menu);
		return true;
	}
	
	//method to copy assets from "saferoot"
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
    

    //all the work for rooting
    public void getRoot(){
		//set up directories && chmod
		root_tools.executeAsSH("/data/data/com.pressy4pie.f6utilities2/saferoot/getroot_begin.sh");
		Log.i("getroot", "getroot_begin.sh executed...");
		Log.i("getroot", "Starting actual root now...");
		
		//the actual get root
		root_tools.executeAsSH("/data/data/com.pressy4pie.f6utilities2/saferoot/getroot");
		Log.i("getroot", "getroot executed...");
		
		//clean up
		root_tools.executeAsSH("/data/data/com.pressy4pie.f6utilities2/saferoot/getroot_finish.sh");
		Log.i("getroot", "getroot_finish.sh executed...");
		
	}
	
    //the method to start the root.
	public void start(View view) {
		final ProgressDialog RingProgressDialog = ProgressDialog.show(RooterActivity.this, "Please Wait", "Rooting", true);
		RingProgressDialog.setCancelable(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//this is the runnable stuff for the progress bar
					getRoot();
				} catch (Exception e) {
					Log.e("root", "something went wrong");
				}
				RingProgressDialog.dismiss();
			
		}
	}).start();	
	}

}


