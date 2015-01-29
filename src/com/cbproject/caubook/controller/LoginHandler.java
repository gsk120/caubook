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
		
		// ��ȿ�� ���̵� ��й�ȣ�̸� �α��� ����
		return true;
	}
}
