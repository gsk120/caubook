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

import com.cbproject.caubook.R;
import com.cbproject.caubook.activities.MyBookRegisterActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class LoginHandler {
	private String userId;
	private String userPw;
	private Context context;
	
	private WebView webview = null;
	private String cookies = "";
	
	public LoginHandler(Context _context, String _id, String _pw) {
		this.context = _context;
		this.userId = _id;
		this.userPw = _pw;
	}
	
	// �α����� �ϰ� ���� ���θ� �����ϴ� �Լ�
	public boolean doLogin() {
		
		// ���̵� ��й�ȣ�� ��������� �α��� �Ұ�
		if(userId.equals("")) {
			Toast.makeText(context, R.string.login_error_id_empty, Toast.LENGTH_SHORT).show();
			return false;
		}
		if(userPw.equals("")) {
			Toast.makeText(context, R.string.login_error_pw_empty, Toast.LENGTH_SHORT).show();
			return false;
		}
		
		// ����� �α��� ���� ������
		webview = new WebView(context);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				cookies = CookieManager.getInstance().getCookie(url);
				System.out.println(cookies);
				CookieSyncManager localCookieSyncManager = CookieSyncManager.createInstance(webview.getContext());
				localCookieSyncManager.startSync();
				if(cookies.contains("JSESSION")){
					CAULogin asyncGetLectureList = new CAULogin();
					asyncGetLectureList.execute(cookies);
				}
				view.loadUrl("javascript:window.HtmlViewer.showHTML" + "('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
				super.onPageFinished(view, url);	
			}
		});
		webview.loadUrl("http://sso.cau.ac.kr/WebSSO/AuthWeb/Logon.aspx?ssosite=cauin.cau.ac.kr&credType=BASIC&retURL=http://cautis.cau.ac.kr/TIS/comm/loginInfo/selectLoginInfo.do&userID=" + userId + "&password=" + userPw);
		
		// ��ȿ�� ���̵� ��й�ȣ�̸� �α��� ����
		return true;
	}
	
	private class CAULogin extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			return requestInformation(params[0]);
		}
		
		// doInBackground ���� �� UI ������ �۾�
		protected void onPostExecute(String result) {
			// TODO ��� �۾� ���� �ϴ� �α�Ĺ�� ����.
			Log.i("result", result);
			Intent intent = new Intent(context, MyBookRegisterActivity.class);
			((Activity)context).startActivity(intent);
			((Activity)context).finish();
		}
		
		private String requestInformation(String cookie) {
			URL url = null;
			String line = "";
			String lineResult = "";
			try {
				url = new URL("http://54.65.154.48:3000/requestInformation");
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
				cookie = URLEncoder.encode(cookie, "UTF-8");
				Log.i("dddd", cookie);
				String outString = "cookie="+cookie;
				
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
