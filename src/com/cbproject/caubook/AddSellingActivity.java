package com.cbproject.caubook;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;

public class AddSellingActivity extends ActionBarActivity {
	
	Button btnBookRegisterSelect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_add_selling);
	
		btnBookRegisterSelect = (Button)findViewById(R.id.btn_book_register_go);
		
		btnBookRegisterSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AddSellingActivity.class);
				startActivity(intent);
			}
		});
	}

}
