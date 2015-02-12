package com.cbproject.caubook.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;
import com.cbproject.caubook.activities.MyBookRegisterActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Html;
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
	
	// 로그인을 하고 성공 여부를 리턴하는 함수
	public boolean doLogin() {
		
		// 아이디나 비밀번호가 비어있으면 로그인 불가
		if(userId.equals("")) {
			Toast.makeText(context, R.string.login_error_id_empty, Toast.LENGTH_SHORT).show();
			return false;
		}
		if(userPw.equals("")) {
			Toast.makeText(context, R.string.login_error_pw_empty, Toast.LENGTH_SHORT).show();
			return false;
		}
		
		// 웹뷰로 로그인 세션 얻어오기
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
		
		// 유효한 아이디 비밀번호이면 로그인 성공
		return true;
	}
	
	private class CAULogin extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			return requestInformation(params[0]);
		}
		
		// 매개 변수로 강의리스트를 받아옴
		protected void onPostExecute(String result) {
			// html 형식의 결과를 xml형식으로 변환
			String strFix = Html.fromHtml(result).toString();
			Log.i("ddd", strFix);
			
			ArrayList<SelectedCourseListItem> list = new ArrayList<SelectedCourseListItem>();
			try {
				// SAX 파서 및 xml reader 생성
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				
				// 파싱하기 위한 ParseHandler 생성
				ParseHandler myHandler = new ParseHandler();
				xr.setContentHandler(myHandler);

				// xml 파싱 시작
				xr.parse(new InputSource(new StringReader(strFix)));
 
				// 파싱한 결과 리스트 받아오기
				list = myHandler.getParsedData();
 
				// 파싱 결과 출력
				for (Iterator<SelectedCourseListItem> iterator = list.iterator(); iterator.hasNext();) {
					SelectedCourseListItem item = (SelectedCourseListItem) iterator.next();
					Log.i("course", item.getStrBookName());
				}
            
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// TODO 로그인 성공했을 경우에만 책등록 액티비티로 전환 (일단은 무조건 전환)
			Intent intent = new Intent(context, MyBookRegisterActivity.class);
			intent.putExtra("courseList", list);
			((Activity)context).startActivity(intent);
			((Activity)context).finish();
		}
		
		private String requestInformation(String cookie) {
			URL url = null;
			String line = "";
			String lineResult = "";
			try {
				url = new URL("http://54.92.63.117:3000/requestInformation");
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
				//Log.i("dddd", cookie);
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
