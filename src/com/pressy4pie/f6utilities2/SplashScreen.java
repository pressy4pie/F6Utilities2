package com.pressy4pie.f6utilities2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
//import android.view.Menu;
import android.widget.ProgressBar;


public class SplashScreen extends Activity {
	String outdir = Environment.getExternalStorageDirectory() + "/F6Utilities2";
	String outdirSaferoot = "/data/data/com.pressy4pie.f6utilities2";
	String recovery = Environment.getExternalStorageDirectory() + "/F6Utilities2" + "/recovery.zip";
	String saferoot = Environment.getExternalStorageDirectory() + "/F6Utilities2" + "/saferoot.zip";
	String SDhacks = Environment.getExternalStorageDirectory() + "/F6Utilities2" + "/SDhacks.zip";
	
	String md5File = Environment.getExternalStorageDirectory() + "/F6Utilities2" + "/md5.txt";
	
	
	String tagname = "download";
	private ProgressBar progressBar;
	 private int progressStatus = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		//the dir to be created
        File sdpath = new File (Environment.getExternalStorageDirectory() + "/F6Utilities2");
        sdpath.mkdirs();
        
        File datapath = new File ("/data/data/com.pressy4pie.f6utilities2/saferoot");
        datapath.mkdirs();
        
        //start the download task
        new PrefetchData().execute();
	}

	private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();  
            if(root_tools.fileExists(md5File)){
            	root_tools.execute("busybox rm " + md5File);
            }

        }
 
        @Override
        protected Void doInBackground(Void... arg0) {    	
            downloadFiles("http://www.oudhitsquad.com/pressy4pie/F6/Assets/md5.txt", "md5.txt");
            
           String recoveryUpdate_md5 = root_tools.readLine(md5File, 1);
           String saferootUpdate_md5 = root_tools.readLine(md5File, 2);

           Log.d("recoveryUpdate_md5: ", recoveryUpdate_md5);
           Log.d("saferootUpdate_md5: ", saferootUpdate_md5);
            
            //String recoveryUpdate_md5 = "bb25826589694a45e13b75561b2d5647";
            //String saferootUpdate_md5 = "6a6b725846577e9955db8608c957216b";
        	
        	if(root_tools.fileExists(recovery)) {
        		Log.i(tagname, "recovery.zip was found.");
                String checkrecovery = root_tools.checkSum(recovery);
                Log.d("checksum", "recovery checksum: " + "'" + checkrecovery + "'");
                Log.d("checksum", "checking against: " + "'" + recoveryUpdate_md5 + "'");
                
                //check for updates
                if (!checkrecovery.equals(recoveryUpdate_md5)) {
                	Log.i(tagname, "an update was found. Downloading new recovery.zip");
                	root_tools.execute("busybox rm " + recovery);
                	download("recovery");
                }
        	}
        	
        	//download recovery.zip if it doesnt exist
        	else {
        		download("recovery");
        	}
        	
        	if(root_tools.fileExists(saferoot)) {
        		Log.i(tagname, "saferoot.zip was found.");
                String checksaferoot = root_tools.checkSum(saferoot);
                Log.d("checksum", "saferoot checksum: " + checksaferoot);
                Log.d("checksum", "checking against: " + saferootUpdate_md5 );
                
                //check for updates
                if (!checksaferoot.equals(saferootUpdate_md5)) {
                	Log.i(tagname, "An update was found. Downloading new saferoot.zip");
                	root_tools.execute("busybox rm " + saferoot);
                	download("saferoot");
                }
        	}
        	//downlaod saferoot.zip if it doesnt exist
        	else {
                download("saferoot");
        	}
        	

        	if(root_tools.fileExists(SDhacks)) {
        		Log.i(tagname, "SDhacks.zip was found.");
        		//no updates for this. idgaf about it anymore
        	}
        	
        	//downlaod SDhacks.zip if it doesnt exist
        	else {
        		download("SDhacks");
        	}
        	


            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            




            
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            // close this activity
            finish();
        }
 
    }
	
	public void updateProgress(int downloadsize, int totalsize, String filename) {
		//update the progress bar
		progressStatus = ((downloadsize*100)/ totalsize);
		
		if(progressStatus % 10 == 0) {
		progressBar.setProgress(progressStatus);
		//Log.i(filename, "Downloading: " + progressStatus );
		}
	}
    
	public void downloadFiles(String file2get, String filename){
		try {
		//set the download URL, a url that points to a file on the internet
        //this is the file to be downloaded
        URL url = new URL(file2get);

        //create the new connection
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        //set up some things on the connection
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);

        //and connect!
        urlConnection.connect();

        //set the path where we want to save the file
        //in this case, going to save it on the root directory of the
        //sd card.
        File SDCardDir = Environment.getExternalStorageDirectory();
        //create a new file, specifying the path, and the filename
        //which we want to save the file as.
        File file = new File(SDCardDir + "/F6Utilities2/" + filename);

        //this will be used to write the downloaded data into the file we created
        FileOutputStream fileOutput = new FileOutputStream(file);

        //this will be used in reading the data from the internet
        InputStream inputStream = urlConnection.getInputStream();

        //this is the total size of the file
        int totalSize = urlConnection.getContentLength();
        //variable to store total downloaded bytes
        int downloadedSize = 0;

        //create a buffer...
        byte[] buffer = new byte[1024];
        int bufferLength = 0; //used to store a temporary size of the buffer

        //now, read through the input buffer and write the contents to the file
        Log.i(tagname, "Commencing Download of " + filename);
        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                //add the data in the buffer to the file in the file output stream (the file on the sd card
                fileOutput.write(buffer, 0, bufferLength);
                //add up the size so we know how much is downloaded
                downloadedSize += bufferLength;
                updateProgress(downloadedSize, totalSize, filename);
        }
        Log.i(tagname, "Download of " + filename + " has completed");
        //close the output stream when done
        fileOutput.close();
		} catch (MalformedURLException e) {
	        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
	}
	
	private void download(String choice) {
		if (choice == "recovery"){
			//download the assets
    		Log.i(tagname, "recovery.zip not found. downloading.\n");
    		downloadFiles("http://www.oudhitsquad.com/pressy4pie/F6/Assets/Recovery/recovery.zip", "recovery.zip");
		}
		
		else if (choice == "saferoot"){
			Log.i(tagname, "saferoot.zip not found. downloading.\n");
            downloadFiles("http://www.oudhitsquad.com/pressy4pie/F6/Assets/saferoot/saferoot.zip", "saferoot.zip");
		}
		
		else if (choice == "SDhacks"){
			Log.i(tagname, "SDhacks.zip not found. downloading.\n");
    		downloadFiles("http://www.oudhitsquad.com/pressy4pie/F6/Assets/SDhacks/SDhacks.zip", "SDhacks.zip");
		}
		
		else {
			downloadFiles("http://www.oudhitsquad.com/pressy4pie/F6/Assets/covrphoto.jpg", "stupidpic.jpg");
		}
		
	}
	
	
}