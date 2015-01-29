package com.cbproject.caubook.controller;

import com.cbproject.caubook.R;

import android.content.Context;
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
		
		// 유효한 아이디 비밀번호이면 로그인 성공
		return true;
	}
}
