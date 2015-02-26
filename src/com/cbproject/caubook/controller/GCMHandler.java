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

import com.google.android.gcm.GCMRegistrar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GCMHandler {
	private Context context;
	private String portalId;
	
	public GCMHandler(Context _context,String _portalId) {
		context = _context;
		portalId = _portalId;
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
		gcmSendRegId.execute(portalId,regId);
		
	}
	
	private class GCMSendRegId extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			sendRegId(params);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			Log.d("onPostExecute", "������ ���ID ����Ϸ�");
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
				id[1] = URLEncoder.encode(id[1],"UTF-8");
				
				String outString = "portalID=" + id[0] + "&" + "studentID=20091533" + "&" + "gcmRegID=" + id[1] + "&" + "kakaoID=abc@naver.com";
				
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
