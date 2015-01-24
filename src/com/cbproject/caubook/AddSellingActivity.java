package com.cbproject.caubook;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;

public class AddSellingActivity extends ActionBarActivity {
	
	Button btnBookRegisterGo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_add_selling);
	
		btnBookRegisterGo = (Button)findViewById(R.id.btn_book_register_go);
		
		btnBookRegisterGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TradeTabActivity.class);
				startActivity(intent);
			}
		});
	}

}
