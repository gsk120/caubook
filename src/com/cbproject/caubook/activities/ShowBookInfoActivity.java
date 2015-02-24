package com.cbproject.caubook.activities;

import java.util.ArrayList;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShowBookInfoActivity extends ActionBarActivity {
	
	private ArrayList<SelectedCourseListItem> buyCourseListData;
	private ArrayList<SelectedCourseListItem> selectedCourseListData;
	private int selectedPosition;
	private EditText editTextShowbookInfoCourseName;
	private Button btnShowBookInfoQuestion;
	private Button btnShowBookInfoReturn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_show_book_info);
		
		selectedPosition = getIntent().getIntExtra("position", -1);
		buyCourseListData = (ArrayList<SelectedCourseListItem>) getIntent()
												.getSerializableExtra("buyCourseListData");
		
		//DB 업어서 일단 임시로 넘겨줌
		selectedCourseListData = (ArrayList<SelectedCourseListItem>) getIntent()
											.getSerializableExtra("selectedCourseListData");
		
		editTextShowbookInfoCourseName = (EditText)findViewById(R.id.edit_text_show_book_info_course_name);
		btnShowBookInfoQuestion = (Button)findViewById(R.id.btn_show_book_info_question);
		btnShowBookInfoReturn = (Button)findViewById(R.id.btn_show_book_info_return);
		
		editTextShowbookInfoCourseName.setText(buyCourseListData.get(selectedPosition).getStrCourseTitle());
		
		btnShowBookInfoQuestion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "문의하러 가기", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		btnShowBookInfoReturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TradeTabActivity.class);
				intent.putExtra("selectedCourseListData", selectedCourseListData);
				startActivity(intent);
				finish();
				
			}
		});
	}
}
