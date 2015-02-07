package com.cbproject.caubook.activities;

import java.util.ArrayList;
import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;

public class AddSellingActivity extends ActionBarActivity {
	
	private Button btnSelectedBookAddSelling;
	private Button btnBookAddSellingReturn;
	private EditText editTextBookAddSellingCourseName;
	private ArrayList<SelectedCourseListItem> selectedCourseListData;
	private int selectedPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_add_selling);
	
		btnSelectedBookAddSelling = (Button)findViewById(R.id.btn_book_add_selling);
		btnBookAddSellingReturn = (Button)findViewById(R.id.btn_book_add_selling_return);
		editTextBookAddSellingCourseName = (EditText)findViewById(R.id.edit_text_book_add_selling_course_search);
		
		btnSelectedBookAddSelling.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectedCourseListData.get(selectedPosition).setbBookSell(true);	//최종등록 true로 변경
				Intent intent = new Intent(getApplicationContext(), TradeTabActivity.class);
				intent.putExtra("selectedCourseListData", selectedCourseListData);
				startActivity(intent);
				finish();
				
				Toast.makeText(getApplicationContext(), "구매 탭에 등록되었습니다.", Toast.LENGTH_SHORT).show();
			}
		});
		
		btnBookAddSellingReturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TradeTabActivity.class);
				intent.putExtra("selectedCourseListData", selectedCourseListData);
				startActivity(intent);
				finish();
			}
		});
		
		//TradeTabActivity에서 넘어온 데이터 저장
		selectedPosition = getIntent().getIntExtra("position", -1);
		selectedCourseListData = (ArrayList<SelectedCourseListItem>)
									getIntent().getSerializableExtra("selectedCourseListData");
		
		editTextBookAddSellingCourseName.setText(selectedCourseListData.get(selectedPosition).getStrCourseTitle());
	
	}//onCreate
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
	}
}
