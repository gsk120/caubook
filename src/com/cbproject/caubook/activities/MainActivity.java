package com.cbproject.caubook.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cbproject.caubook.R;
import com.cbproject.caubook.controller.BackPressCloseHandler;
import com.cbproject.caubook.controller.LoginHandler;

public class MainActivity extends ActionBarActivity {
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_main);
		
		
		
		// ���÷��� ȭ���� ���̵��� 1���� ������ �߰�
		Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
            	// ������ ���� �� �α��� �õ�
            	CheckIdentification();
            }
        }, 1000); 
	}
	
	// �ڵ� �α��� Ȥ�� �α��� �õ� �Լ�
	private void CheckIdentification() {
		SharedPreferences pref = getSharedPreferences("savedInfo", MODE_PRIVATE);
		
		// �ڵ� �α��� ���� Ȯ��
		if(pref.getBoolean("auto_login", false)) {
			String strId = pref.getString("id", "");
			String strPw = pref.getString("pw", "");
			LoginHandler hLogin = new LoginHandler(getApplicationContext(), strId, strPw);
			if(hLogin.doLogin()) {
				//�ڵ� �α��� ����
				Intent intent = new Intent(getApplicationContext(), AgreementActivity.class);
				startActivity(intent);
				finish();
			} else {
				//�ڵ� �α��� ����
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}
		else {
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
