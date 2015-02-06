package com.cbproject.caubook.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.cbproject.caubook.R;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class LoginHandler {
	private String userId;
	private String userPw;
	private Context context;
	
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
		
		// TODO 웹과 연동하여 아이디 비밀번호 일치 여부 확인 요망
		new CAULogin().execute("http://attitrio3.cafe24.com/IdOverrapCheck.jsp", "id", "gsk120");
		// 유효한 아이디 비밀번호이면 로그인 성공
		return true;
	}
	
	private class CAULogin extends AsyncTask<String, Void, String> {

		// doInBackground 실행 전 UI 스레드 작업
		protected void onPreExecute() {
			// TODO 임시 틀 구성
		}
		
		@Override
		protected String doInBackground(String... params) {
			StringBuilder builder = new StringBuilder();
			try {
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(5000);
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("POST");	// POST 방식으로 서버와 연결하겠다는 설정
				conn.setDoOutput(true);		// POST로 매개변수를 보내기위한 설정 -> GET방식인 경우 불필요
				conn.setDoInput(true);		// 서버로 부터 response를 받는다는 설정
				conn.connect();
				
				// 매개변수 전달
				OutputStream op = conn.getOutputStream();
				String param = params[1] + "=" + params[2];
				op.write(param.getBytes());
				op.flush();
				op.close();
				
				// URLConnection 결과 받아오기
				InputStream is = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String line;
				while((line = reader.readLine())!=null) {
					builder.append(line);
				}
				
				// InputStream 닫기
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 해당 연결 response onPostExecute로 전달하기
			return builder.toString();
		}
		
		// doInBackground 실행 후 UI 스레드 작업
		protected void onPostExecute(String result) {
			// TODO 결과 작업 띄우기 일단 로그캣에 찍음.
			Log.i("result", result);
		}
	}
}
