package com.pressy4pie.f6utilities2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.pressy4pie.f6utilities2.root_tools;

public class SDhacksActivity extends Activity {
	String tagname = "SDhacks";
	ProgressDialog barProgressDialog;
	Handler updateBarHandler;
	boolean finish = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sdhacks);	
        File dir = new File ("/data/data/com.pressy4pie.f6utilities2/sdhack");
        dir.mkdirs();
        CopyAssets();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sdhacks, menu);
		return true;
	}
	
	public boolean checkPart(){
		File f = new File("/dev/block/mmcblk1p2");
		if(f.exists()){
			return true;
		}
		else return false;
	}
	
	public void install() {
			//user is perssy4pie
		
		//check for partitioned sdcard
		boolean check = checkPart();
		if(check == true) {
			finish = true;
			//if the card appears to be fine
			String kernelInstall = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/sdhack/pressy4pie-sdhack-boot.lok of=/dev/block/platform/msm_sdcc.1/by-name/boot";
			root_tools.execute(kernelInstall);
			Log.i(tagname, "The sdcard hack boot image was installed.");
			
			String recoveryInstall = "busybox dd if=/data/data/com.pressy4pie.f6utilities2/sdhack/pressy4pie-cwm-unofficial-sd-data.lok of=/dev/block/platform/msm_sdcc.1/by-name/recovery";
			root_tools.execute(recoveryInstall);
			Log.i(tagname, "The CWM was also installed");

			
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
					//this is the runnable stuff for the progress bar
					install();
					//respond();
				} catch (Exception e) {
					Log.e(tagname, "something went wrong");
				}
				RingProgressDialog.dismiss();
		}
	}).start();	
		
	}
	
	
	private void CopyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("SDhacks");
        } catch (IOException e) {
            Log.e("Asset Copy", e.getMessage());
        }

        for(String filename : files) {
            System.out.println("File name => "+filename);
            InputStream in = null;
            OutputStream out = null;
            try {
              in = assetManager.open("SDhacks/"+filename);   // if files resides inside the "Files" directory itself
              out = new FileOutputStream("/data/data/com.pressy4pie.f6utilities2/sdhack/" + filename);
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
	
	
}
