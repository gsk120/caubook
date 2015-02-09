package com.cbproject.caubook.login;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.cbproject.caubook.R;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NodeServerConnectionSample extends ActionBarActivity {

	WebView webview = null;
	TextView subjectShowTextView = null;
	EditText portalID = null;
	EditText portalPW = null;
	Button portalLogin = null;
	String cookies = null;
	String id = null;
	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_node_server_connection_sample);
		webview = (WebView) findViewById(R.id.webView1);
		subjectShowTextView = (TextView) findViewById(R.id.subjectShowTextView);
		portalID = (EditText) findViewById(R.id.portalID);
		portalPW = (EditText) findViewById(R.id.portalPW);
		portalLogin = (Button) findViewById(R.id.portalLogin);
		portalID.setText("hemanruru");
		portalPW.setText("me506070");
		portalLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tryLogin();
			}
		});
		
	}

	public void tryLogin(){
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				cookies = CookieManager.getInstance().getCookie(url);
				System.out.println(cookies);
				CookieSyncManager localCookieSyncManager = CookieSyncManager.createInstance(webview.getContext());
				CookieManager localCookieManager = CookieManager.getInstance();
				localCookieSyncManager.startSync();
				if(cookies.contains("JSESSION")){
					AsyncGetLectureList asyncGetLectureList = new AsyncGetLectureList();
					asyncGetLectureList.execute(cookies);
//					getXMLWithHttpPost();
				}
				view.loadUrl("javascript:window.HtmlViewer.showHTML" + "('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
				super.onPageFinished(view, url);
				
			}
		});
		
		webview.loadUrl("http://sso.cau.ac.kr/WebSSO/AuthWeb/Logon.aspx?ssosite=cauin.cau.ac.kr&credType=BASIC&retURL=http://cautis.cau.ac.kr/TIS/comm/loginInfo/selectLoginInfo.do&userID="+portalID.getText().toString()+"&password="+portalPW.getText().toString());
		
	}
	
	
	public static String requestInformation(String cookie){
		String result = null;
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
			cookie = URLEncoder.encode(cookie);
			String outString = "cookie="+cookie;
			
			os.write(outString.getBytes("UTF-8"));
			os.flush();
			os.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			while((line = br.readLine()) != null){
				lineResult += line;
			}
			System.out.println(lineResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URLDecoder urlDecoder = new URLDecoder();
		lineResult = urlDecoder.decode(lineResult);
		return lineResult;
	}
	
	public class AsyncGetLectureList extends AsyncTask<String, String, String>{

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			subjectShowTextView.setText(result);
			super.onPostExecute(result);
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return requestInformation(params[0]);
		}
		
	}
}
