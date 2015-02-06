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
		
		// TODO ���� �����Ͽ� ���̵� ��й�ȣ ��ġ ���� Ȯ�� ���
		new CAULogin().execute("http://attitrio3.cafe24.com/IdOverrapCheck.jsp", "id", "gsk120");
		// ��ȿ�� ���̵� ��й�ȣ�̸� �α��� ����
		return true;
	}
	
	private class CAULogin extends AsyncTask<String, Void, String> {

		// doInBackground ���� �� UI ������ �۾�
		protected void onPreExecute() {
			// TODO �ӽ� Ʋ ����
		}
		
		@Override
		protected String doInBackground(String... params) {
			StringBuilder builder = new StringBuilder();
			try {
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(5000);
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("POST");	// POST ������� ������ �����ϰڴٴ� ����
				conn.setDoOutput(true);		// POST�� �Ű������� ���������� ���� -> GET����� ��� ���ʿ�
				conn.setDoInput(true);		// ������ ���� response�� �޴´ٴ� ����
				conn.connect();
				
				// �Ű����� ����
				OutputStream op = conn.getOutputStream();
				String param = params[1] + "=" + params[2];
				op.write(param.getBytes());
				op.flush();
				op.close();
				
				// URLConnection ��� �޾ƿ���
				InputStream is = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String line;
				while((line = reader.readLine())!=null) {
					builder.append(line);
				}
				
				// InputStream �ݱ�
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// �ش� ���� response onPostExecute�� �����ϱ�
			return builder.toString();
		}
		
		// doInBackground ���� �� UI ������ �۾�
		protected void onPostExecute(String result) {
			// TODO ��� �۾� ���� �ϴ� �α�Ĺ�� ����.
			Log.i("result", result);
		}
	}
}
