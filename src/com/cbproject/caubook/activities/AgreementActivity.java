package com.cbproject.caubook.activities;

import com.cbproject.caubook.R;
import com.cbproject.caubook.controller.BackPressCloseHandler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class AgreementActivity extends ActionBarActivity implements OnClickListener {
	
	private Button btnAgreeYes;
	private Button btnAgreeNo;
	private BackPressCloseHandler backPressCloseHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_agreement);

		backPressCloseHandler = new BackPressCloseHandler(this);
		
		btnAgreeYes = (Button)findViewById(R.id.btn_agree_yes);
		btnAgreeNo = (Button)findViewById(R.id.btn_agree_no);
		
		btnAgreeYes.setOnClickListener(this);
		btnAgreeNo.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}
	
	@Override
	public void onClick(View v) {
		CheckBox agreeUsage = (CheckBox)findViewById(R.id.check_usage_agree);
		CheckBox agreePerson = (CheckBox)findViewById(R.id.check_person_agree);
		switch(v.getId()) {
		case R.id.btn_agree_yes:
			// 동의 버튼 눌렀을 경우
			if(!agreeUsage.isChecked() || !agreePerson.isChecked()){
				// 약관에 동의하지 않은경우 경고문구 출력
				Toast.makeText(getApplicationContext(), "약관에 동의하시기 바랍니다.", Toast.LENGTH_SHORT).show();
				return;						
			}
			
			// 동의가 완료되었다고 preference에 저장.
			SharedPreferences pref = getSharedPreferences("savedInfo", MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putBoolean("app_agree", true);
			editor.commit();
			
			// 다음 액티비티로 전환
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			finish();
			break;
			
		case R.id.btn_agree_no:
			// 동의하지 않기 누른 경우 다이얼로그 창 뜨면서 종료여부 묻기
			new AlertDialog.Builder(this)
			.setTitle("이용약관 동의")
			.setMessage("동의하지 않는 경우\nUniBook을 사용할 수 없습니다.")
			.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.setNegativeButton("취소", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).show();
			break;
		}
	}
}
