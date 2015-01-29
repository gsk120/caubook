package com.cbproject.caubook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AgreementActivity extends ActionBarActivity implements OnClickListener{
	
	private Button agreeYes;
	private Button agreeNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_agreement);

		agreeYes = (Button)findViewById(R.id.btn_agree_yes);
		agreeNo = (Button)findViewById(R.id.btn_agree_no);
		
		agreeYes.setOnClickListener(this);
		agreeNo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_agree_yes:
			Intent intent = new Intent(getApplicationContext(), MyBookRegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_agree_no:
			break;
		}
		finish();
	}
}
