package com.pressy4pie.f6utilities2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class RooterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rooter);
		
        File dir = new File ("/data/data/com.pressy4pie.f6utilities2/saferoot");
        dir.mkdirs();
        
        CopyAssets();
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
            files = assetManager.list("Files");
        } catch (IOException e) {
            Log.e("Asset Copy", e.getMessage());
        }

        for(String filename : files) {
            System.out.println("File name => "+filename);
            InputStream in = null;
            OutputStream out = null;
            try {
              in = assetManager.open("Files/"+filename);   // if files resides inside the "Files" directory itself
              out = new FileOutputStream("/data/data/com.pressy4pie.f6utilities2/recovery/" + filename);
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
    




public void start(View view){
	Runtime rt = Runtime.getRuntime();
	try {
		Process q = rt.exec(new String("chmod 0755 /data/data/com.pressy4pie.f6utilities2/saferoot/getroot.sh"));
		Process p = rt.exec(new String("/data/data/com.pressy4pie.f6utilities2/saferoot/getroot.sh"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}


