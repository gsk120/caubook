package com.cbproject.caubook.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.cbproject.caubook.R;
import com.cbproject.caubook.controller.BackPressCloseHandler;
import com.cbproject.caubook.controller.LoginHandler;

public class MainActivity extends ActionBarActivity {
	
	private ProgressBar progressLoading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_main);
		
		progressLoading = (ProgressBar)findViewById(R.id.progress_loading);
		
		// 스플래시 화면이 보이도록 1초의 딜레이 추가
		Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
            	// 딜레이 종료 후 로그인 시도
            	checkIdentification();
            }
        }, 1000); 
	}
	
	// 약관동의. 자동로그인 시도 함수
	private void checkIdentification() {
		SharedPreferences pref = getSharedPreferences("savedInfo", MODE_PRIVATE);
		
		// 약관 동의 여부 확인 (약관 동의 x인 경우)
		if(!pref.getBoolean("app_agree", false)) {
			// 약관동의 차체가 안되었으므로 액티비티 전환후 리턴
			Intent intent = new Intent(getApplicationContext(), AgreementActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		
		// 자동 로그인 여부 확인
		if(pref.getBoolean("auto_login", false)) {
			String strId = pref.getString("id", "");
			String strPw = pref.getString("pw", "");
			LoginHandler hLogin = new LoginHandler(this, strId, strPw, progressLoading);
			if(hLogin.doLogin()) {
				//자동 로그인 성공 -> 액티비티 전환 후 리턴
				Intent intent = new Intent(getApplicationContext(), TradeTabActivity.class);
				startActivity(intent);
				finish();
				return;
			}
		}
		
		// 약관 동의 o 자동로그인 x 인경우
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
		finish();
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
