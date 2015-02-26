package com.cbproject.caubook.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cbproject.caubook.R;
import com.cbproject.caubook.controller.BackPressCloseHandler;
import com.cbproject.caubook.controller.GCMHandler;
import com.cbproject.caubook.controller.LoginHandler;
import com.google.android.gcm.GCMRegistrar;

public class LoginActivity extends ActionBarActivity implements OnClickListener{

	private Button btnLogin;
	private EditText inputID;
	private EditText inputPW;
	private CheckBox autoLogin;
	private SharedPreferences pref;
	private BackPressCloseHandler backPressCloseHandler;
	private ProgressBar progressLoading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_login);
		
		backPressCloseHandler = new BackPressCloseHandler(this);
		
		pref = getSharedPreferences("savedInfo", MODE_PRIVATE);
		
		inputID = (EditText)findViewById(R.id.edit_user_id);
		inputPW = (EditText)findViewById(R.id.edit_user_password);
		autoLogin = (CheckBox)findViewById(R.id.check_auto_login);
		btnLogin = (Button)findViewById(R.id.btn_login);
		progressLoading = (ProgressBar)findViewById(R.id.progress_login_loading);
		progressLoading.setVisibility(View.INVISIBLE);
		
		autoLogin.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_login:
			// 로그인 진행!
			String strId = inputID.getEditableText().toString();
			String strPw = inputPW.getEditableText().toString();
			LoginHandler hLogin = new LoginHandler(this, strId, strPw);
			progressLoading.setVisibility(View.VISIBLE);	// 로딩
			if(hLogin.doLogin()) {
				// 로그인 성공 -> 현재 상태 프리퍼런스에 저장
				SharedPreferences.Editor editor = pref.edit();
				editor.putString("id", strId);
				editor.putString("pw", strPw);
				editor.commit();
				
				//로그인 성공 후 GCM등록ID 서버에 저장
				GCMHandler GCMRegId = new GCMHandler(this,strId);
				GCMRegId.registerGcm();
				
			} else {
				//로그인 실패			
			}
			break;
		case R.id.check_auto_login:
			SharedPreferences.Editor editor = pref.edit();
			editor.putBoolean("auto_login", autoLogin.isChecked());
			editor.commit();
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}
}
