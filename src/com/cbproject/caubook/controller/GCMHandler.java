package com.cbproject.caubook.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.cbproject.caubook.activities.MyBookRegisterActivity;
import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class GCMHandler {
	private Context context;
	private String portalId;
	private String studentId;
	
	
	public GCMHandler(Context _context,String _portalId, String _stuNo) {
		context = _context;
		portalId = _portalId;
		studentId = _stuNo;
	}

	public void registerGcm() {
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);

		final String regId = GCMRegistrar.getRegistrationId(context);

		if (regId.equals("")) {
			GCMRegistrar.register(context, "819088402832");
		} else {
			Log.e("id", regId);
		}
		
		GCMSendRegId gcmSendRegId = new GCMSendRegId();
		SharedPreferences pref = context.getSharedPreferences("savedInfo", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("gcmRegID", regId);
		editor.commit();
		gcmSendRegId.execute(portalId, studentId, regId);
		
	}
	
	private class GCMSendRegId extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			sendRegId(params);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			Log.d("onPostExecute", "db register complete");			
			super.onPostExecute(result);
		}
		
		private String sendRegId(String[] id){
			URL url = null;
			String line = "";
			String lineResult = "";
			try {
				url = new URL("http://54.92.63.117:3000/registUser");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				OutputStream os = conn.getOutputStream();
				
				id[0] = URLEncoder.encode(id[0], "UTF-8");
				id[2] = URLEncoder.encode(id[2],"UTF-8");
				
				String outString = "portalID=" + id[0] + "&" + "studentID=" + id[1] + "&" + "gcmRegID=" + id[2] + "&" + "kakaoID=abc@naver.com";
				
				os.write(outString.getBytes("UTF-8"));
				os.flush();
				os.close();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				while((line = br.readLine()) != null){
					lineResult += line;
				}
				//System.out.println(lineResult);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				lineResult = URLDecoder.decode(lineResult, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lineResult;
		}
	}
}
