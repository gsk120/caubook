package com.cbproject.caubook.activities;

import com.cbproject.caubook.R;
import com.cbproject.caubook.controller.BackPressCloseHandler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AgreementActivity extends ActionBarActivity implements OnClickListener{
	
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
