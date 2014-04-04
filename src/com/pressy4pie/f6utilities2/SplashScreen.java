package com.pressy4pie.f6utilities2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
//import android.view.Menu;
import android.widget.ProgressBar;

public class SplashScreen extends Activity {
	private ProgressBar progressBar;
	 private int progressStatus = 0;
	 private Handler handler = new Handler();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		//the dir to be created
        File dir = new File ("/data/data/com.pressy4pie.f6utilities2/recovery");
        dir.mkdirs();
        
        new Thread(new Runnable() {
            public void run() {
               while (progressStatus < 100) {
                  progressStatus += 1;
           // Update the progress bar and display the 
                                //current value in the text view
           handler.post(new Runnable() {
           public void run() {
              progressBar.setProgress(progressStatus);
              //textView.setText(progressStatus+"/"+progressBar.getMax());
           }
               });
               try {
                  // Sleep for 200 milliseconds. 
                                //Just to display the progress slowly
                  Thread.sleep(200);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
         }
         }).start();
        
        
        new PrefetchData().execute();
        //copy the assets needed.
        //CopyAssets();
	}

	private class PrefetchData extends AsyncTask<Void, Void, Void> {
		 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // before making http calls         
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {

            CopyAssets();
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and lauch main activity
            Intent i = new Intent(SplashScreen.this, MainActivity.class);

            startActivity(i);
 
            // close this activity
            finish();
        }
 
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
    

}
