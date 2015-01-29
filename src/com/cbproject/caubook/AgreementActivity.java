package com.cbproject.caubook;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AgreementActivity extends ActionBarActivity {
	
	Button btnAgreeYes,btnAgreeNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_agreement);
		
		btnAgreeYes = (Button)findViewById(R.id.btn_agree_yes);
		btnAgreeNo = (Button)findViewById(R.id.btn_agree_no);
		
		btnAgreeYes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MyBookRegisterActivity.class);
				startActivity(intent);
			}
		});
		
		btnAgreeNo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
