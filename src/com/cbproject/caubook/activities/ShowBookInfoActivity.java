package com.cbproject.caubook.activities;

import java.util.ArrayList;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;
import com.cbproject.caubook.sqlite.ProductTable.ProductData;

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
	
	private ProductData productInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_show_book_info);
		
		//DB 업어서 일단 임시로 넘겨줌
		// TODO 현재는 선택한 아이템의 인덱스, 구매목록 리스트, 그리고 현재는 디비가 없기때문에 자신의 판매 리스트까지 넘긴다. 요거 
		selectedPosition = getIntent().getIntExtra("position", -1);
		buyCourseListData = (ArrayList<SelectedCourseListItem>) getIntent().getSerializableExtra("buyCourseListData");
		selectedCourseListData = (ArrayList<SelectedCourseListItem>) getIntent().getSerializableExtra("selectedCourseListData");
		
		editTextShowbookInfoCourseName = (EditText)findViewById(R.id.edit_text_show_book_info_course_name);
		btnShowBookInfoQuestion = (Button)findViewById(R.id.btn_show_book_info_question);
		btnShowBookInfoReturn = (Button)findViewById(R.id.btn_show_book_info_return);
		
		editTextShowbookInfoCourseName.setText(buyCourseListData.get(selectedPosition).getStrCourseTitle());
		
		btnShowBookInfoQuestion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 채팅방 연결
				Intent intent = new Intent(ShowBookInfoActivity.this, ChattingActivity.class);
				intent.putExtra("product", productInfo);
				startActivity(intent);				
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
		
		// TODO 상품 정보 임시 구성 -> 나중엔 인텐트로 TradeTabActivity로 부터 넘어오도록 구성
		productInfo = new ProductData(1, 1, 1, "선형대수학", 20000, true, false, "test");
		
	}
}
