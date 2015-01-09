package com.cbproject.caubook;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AgreeActivity extends ActionBarActivity {
	
	Button agreeYes,agreeNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agree);
		
		agreeYes = (Button)findViewById(R.id.btn_agree_yes);
		agreeNo = (Button)findViewById(R.id.btn_agree_no);
		
		agreeYes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), BookRegisterActivity.class);
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
