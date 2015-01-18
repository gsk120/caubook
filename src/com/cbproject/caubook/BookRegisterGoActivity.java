package com.cbproject.caubook;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;

public class BookRegisterGoActivity extends ActionBarActivity {
	
	Button btn_book_register_go;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_register_go);
	
		btn_book_register_go = (Button)findViewById(R.id.btn_book_register_go);
		
		btn_book_register_go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), BookRegisterListActivity.class);
				startActivity(intent);
			}
		});
	}

}
