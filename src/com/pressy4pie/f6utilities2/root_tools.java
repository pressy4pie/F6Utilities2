package com.pressy4pie.f6utilities2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;
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
	
	public static String readProp(String prop) {//1
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
		// TODO Auto-generated catch block
		e.printStackTrace();
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
	
	public static boolean isSet()
	{
		String read1 = "";
		String read2 = "";
		
		String setowner = "setprop ro.pressy4pie.true 0";
		String setvip = "setprop ro.vip.true 0";
		
		read1 = root_tools.readProp("ro.pressy4pie.true");
		read2 = root_tools.readProp("ro.vip.true");
		
		if(isInteger(read1) && isInteger(read2))
		{
			return true;
		}
		
		else {
			//there was no value, so set em
			root_tools.execute(setowner);
			root_tools.execute(setvip);
			return false;
			
		}
	}
	
	public static boolean isPressy4pie()
	{
		String read = "";
		//check the prop, and return true or false
		read = root_tools.readProp("ro.pressy4pie.true");
		int check = 0;
		if(isSet()) {
		check = Integer.parseInt(read); }
		
		else return false;
		
		if(check == 1)
		{
			return true;
		}
		
		
		else {
			return false;
		}
		
	}
	
	public static boolean isVIP()
	{
		String read = "";
		//check the prop, and return true or false
		read = root_tools.readProp("ro.vip4pie.true");
		int check = 0;
		
		if( isSet() == true) {
		check = Integer.parseInt(read); }
		
		else return false;
		
		if(check == 1)
		{
			return true;
		}
		
		
		else {
			return false;
		}
		
	}
	
}
	
	

