package com.cbproject.caubook.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.cbproject.caubook.R;
import com.cbproject.caubook.controller.LoginHandler;

public class LoginActivity extends ActionBarActivity {

	private Button btnLogin;
	private EditText inputID;
	private EditText inputPW;
	private CheckBox autoLogin;
	private SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_login);
		
		pref = getSharedPreferences("savedInfo", MODE_PRIVATE);
		
		inputID = (EditText)findViewById(R.id.edit_user_id);
		inputPW = (EditText)findViewById(R.id.edit_user_password);
		autoLogin = (CheckBox)findViewById(R.id.check_auto_login);
		btnLogin = (Button)findViewById(R.id.btn_login);
		
		autoLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean("auto_login", autoLogin.isChecked());
				editor.commit();
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String strId = inputID.getEditableText().toString();
				String strPw = inputPW.getEditableText().toString();
				LoginHandler hLogin = new LoginHandler(getApplicationContext(), strId, strPw);
				if(hLogin.doLogin()) {
					//로그인 성공 -> 동의 액티비티로 전환 -> 현재 상태 프리퍼런스에 저장
					SharedPreferences.Editor editor = pref.edit();
					editor.putString("id", strId);
					editor.putString("pw", strPw);
					editor.commit();
					
					Intent intent = new Intent(getApplicationContext(), AgreementActivity.class);
					startActivity(intent);
					finish();
				} else {
					//로그인 실패 				
				}
			}
		});
	}
}
