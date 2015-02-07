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
		
		
		
		// 스플래시 화면이 보이도록 1초의 딜레이 추가
		Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
            	// 딜레이 종료 후 로그인 시도
            	CheckIdentification();
            }
        }, 1000); 
	}
	
	// 자동 로그인 혹은 로그인 시도 함수
	private void CheckIdentification() {
		SharedPreferences pref = getSharedPreferences("savedInfo", MODE_PRIVATE);
		
		// 자동 로그인 여부 확인
		if(pref.getBoolean("auto_login", false)) {
			String strId = pref.getString("id", "");
			String strPw = pref.getString("pw", "");
			LoginHandler hLogin = new LoginHandler(getApplicationContext(), strId, strPw);
			if(hLogin.doLogin()) {
				//자동 로그인 성공
				Intent intent = new Intent(getApplicationContext(), AgreementActivity.class);
				startActivity(intent);
				finish();
			} else {
				//자동 로그인 실패
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
