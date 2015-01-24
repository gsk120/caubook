package com.cbproject.caubook;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AgreementActivity extends ActionBarActivity {
	
	Button agreeYes,agreeNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
<<<<<<< HEAD:src/com/cbproject/caubook/AgreementActivity.java
		setContentView(R.layout.a_agreement);
=======
		setContentView(R.layout.agreement_layout);
>>>>>>> 7807cd53d7e1f2d9841347b9618ccfad1a1c725b:src/com/cbproject/caubook/AgreeActivity.java
		
		agreeYes = (Button)findViewById(R.id.btn_agree_yes);
		agreeNo = (Button)findViewById(R.id.btn_agree_no);
		
		agreeYes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), PosessRegisterActivity.class);
				startActivity(intent);
				
			}
		});
		
		agreeNo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
