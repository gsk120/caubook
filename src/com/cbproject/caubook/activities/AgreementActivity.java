package com.cbproject.caubook.activities;

import com.cbproject.caubook.R;

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
	
	private Button btnYes;
	private Button btnNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_agreement);

		btnYes = (Button)findViewById(R.id.btn_agree_yes);
		btnNo = (Button)findViewById(R.id.btn_agree_no);

		btnYes.setOnClickListener(this);
		btnNo.setOnClickListener(this);
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
				Toast.makeText(getApplicationContext(), R.string.agree_error, Toast.LENGTH_SHORT).show();
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
			break;
			
		case R.id.btn_agree_no:
			// 동의하지 않는 경우 어플리케이션 종료
			break;
		}
		finish();
	}
}
