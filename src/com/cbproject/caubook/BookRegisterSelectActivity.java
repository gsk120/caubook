package com.cbproject.caubook;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;

public class BookRegisterSelectActivity extends ActionBarActivity {
	
	Button btn_book_register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_register_select);
	
		btn_book_register = (Button)findViewById(R.id.btn_book_register);
		
		btn_book_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), BookRegisterGoActivity.class);
				startActivity(intent);
			}
		});
	}

}
