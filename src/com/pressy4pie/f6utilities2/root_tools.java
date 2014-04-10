package com.pressy4pie.f6utilities2;
import java.io.BufferedReader;
import java.io.DataOutputStream;
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



//thanks to DF1E for his SimpleExplorer and this code own thur
public class root_tools {
	
	public static BufferedReader execute(String cmd) {
        BufferedReader reader = null;
        try {
                Process process = Runtime.getRuntime().exec("su");
                DataOutputStream os = new DataOutputStream(
                                process.getOutputStream());
                os.writeBytes(cmd + "\n");
                os.writeBytes("exit\n");
                reader = new BufferedReader(new InputStreamReader(
                                process.getInputStream()));
                String err = (new BufferedReader(new InputStreamReader(
                                process.getErrorStream()))).readLine();
                os.flush();

                if (process.waitFor() != 0 || (!"".equals(err) && null != err)) {
                        Log.e("Root Error", err);
                        return null;
                }

                return reader;

        } catch (IOException e) {
                e.printStackTrace();
        } catch (InterruptedException e) {
                e.printStackTrace();
        } catch (Exception e) {
                e.printStackTrace();
        }

        return null;
}
	
	
	
	public static BufferedReader executeAsSH(String shcmd) {
        BufferedReader reader = null;
        try {
                Process process = Runtime.getRuntime().exec("/system/bin/sh");
                DataOutputStream os = new DataOutputStream(
                                process.getOutputStream());
                os.writeBytes(shcmd + "\n");
                os.writeBytes("exit\n");
                reader = new BufferedReader(new InputStreamReader(
                                process.getInputStream()));
                String err = (new BufferedReader(new InputStreamReader(
                                process.getErrorStream()))).readLine();
                os.flush();

                if (process.waitFor() != 0 || (!"".equals(err) && null != err)) {
                        Log.e("SH error", err);
                        return null;
                }

                return reader;

        } catch (IOException e) {
                e.printStackTrace();
        } catch (InterruptedException e) {
                e.printStackTrace();
        } catch (Exception e) {
                e.printStackTrace();
        }

        return null;
}
	
	
	
	public static String readProp(String prop) {
		//this reads a prop as SH (not root) into a string
		Process p = null;
		String line = "";
		String propRead = "";
		
		
		try {
		p = new ProcessBuilder("/system/bin/getprop", prop).redirectErrorStream(true).start();
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

		while ((line=br.readLine()) != null){
			propRead = line;
		}
		p.destroy();
		
		} catch (IOException e) {
		Log.e("Get Prop", "Failed to read prop");
		}
		return propRead;
	
	}
	
	public void rebooter(View view){
		//i dont know if this will need an intent
		//goto the recovery installer intent
		//Intent intent = new Intent(this, rebooter.class);
		
		root_tools.execute("reboot recovery");
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
}
	
	

