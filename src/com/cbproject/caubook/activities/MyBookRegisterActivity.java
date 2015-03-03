package com.cbproject.caubook.activities;

import java.util.ArrayList;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;
import com.cbproject.caubook.controller.BackPressCloseHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

public class MyBookRegisterActivity extends ActionBarActivity {
	
	private ListView listCourse;
	private CourseListAdapter listCourseAdapter;
	private SelectedCourseListItem selectedCourseListItem;
	private ArrayList<SelectedCourseListItem> selectedCourseListData;
	private BackPressCloseHandler backPressCloseHandler;
	private boolean FLAG;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_posess_register);
	
		backPressCloseHandler = new BackPressCloseHandler(this);
		
		Button btnBookRegister = (Button)findViewById(R.id.btn_book_register);
		Button btnRegisterPass = (Button)findViewById(R.id.btn_book_register_pass);
		
		
		// 임시로 리스트뷰 아무렇게 세팅해 둠.
		listCourse = (ListView)findViewById(R.id.list_book_register);
		listCourseAdapter = new CourseListAdapter(this);
		
		ArrayList<SelectedCourseListItem> courseList = new ArrayList<SelectedCourseListItem>();
		courseList = (ArrayList<SelectedCourseListItem>) getIntent().getSerializableExtra("courseList");
		
		for(int i=0; i<courseList.size(); i++) {
			listCourseAdapter.addItem(new CourseListItem(courseList.get(i).getStrCourseTitle(), "아직몰라", false));
		}
//		
//		listCourseAdapter.addItem(new CourseListItem("선형대수학", "선형대수학 5판", false));
//		listCourseAdapter.addItem(new CourseListItem("자료구조", "Data Structure", false));
//		listCourseAdapter.addItem(new CourseListItem("알고리즘", "Algorithm 3판", false));
//		listCourseAdapter.addItem(new CourseListItem("결혼과가족", "결혼과 가족", false));
//		listCourseAdapter.addItem(new CourseListItem("OOP", "객체지향 프로그래밍", false));
//		listCourseAdapter.addItem(new CourseListItem("운영체제", "Operating System", false));
//		listCourseAdapter.addItem(new CourseListItem("리눅스 시스템", "Linux programming", false));
//		listCourseAdapter.addItem(new CourseListItem("C프로그래밍", "C Programming", false));
//		listCourseAdapter.addItem(new CourseListItem("자바프로그래밍", "Java Programming", false));
//		listCourseAdapter.addItem(new CourseListItem("어셈블리", "Assembly", false));
//		listCourseAdapter.addItem(new CourseListItem("공업수학", "Engineering math", false));
//		listCourseAdapter.addItem(new CourseListItem("앱개발프로그래밍", "App android programming", false));
//		
		//전에 체크한 상태로 수정페이지 보여주기
		selectedCourseListData = (ArrayList<SelectedCourseListItem>)
				getIntent().getSerializableExtra("selectedCourseListData");
		
		if(selectedCourseListData == null){
			selectedCourseListData = new ArrayList<SelectedCourseListItem>();
		}
		
		for(int i = 0 ; i < selectedCourseListData.size() ; i++){
			for(int j = 0 ; j < listCourseAdapter.getCount() ; j++){
				if(selectedCourseListData.get(i).getStrCourseTitle().equals
								(listCourseAdapter.getCourseListData().get(j).getStrCourseTitle())){
					listCourseAdapter.getCourseListData().get(j).setBookPossess(true);
					break;
				}
			}
		}
		
		listCourse.setAdapter(listCourseAdapter);
		
		btnBookRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for(int i = 0 ; i < listCourseAdapter.getCount() ; i++){
					if(listCourseAdapter.getCourseListData().get(i).getBookPossess()){//체크된 과목 있으면	
						for(int j = 0 ; j < selectedCourseListData.size() ; j++){	//기존의 판매 탭에 있는 책 목록과 비교
							if(listCourseAdapter.getCourseListData().get(i).getStrCourseTitle().equals
									(selectedCourseListData.get(j).getStrCourseTitle())){
								FLAG=true;
								break;
							}
						}
						if(FLAG == false){	//체크된 과목중 판매 탭에  없는 것만 추가
							selectedCourseListItem = new SelectedCourseListItem(); //판매객체 만들어서 과목명 추가
							
							selectedCourseListItem.setStrCourseTitle(
									listCourseAdapter.getCourseListData().get(i).getStrCourseTitle());
							
							selectedCourseListItem.setbBookPossess(true);
							selectedCourseListData.add(selectedCourseListItem);//미완성 판매탭 판매 리스트에 추가
						}
					}
					FLAG=false;
				}
				
				Intent intent = new Intent(getApplicationContext(), TradeTabActivity.class);
				intent.putExtra("selectedCourseListData", selectedCourseListData);
				startActivity(intent);
				finish();
			}
		});
		
		btnRegisterPass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TradeTabActivity.class);
				intent.putExtra("selectedCourseListData", selectedCourseListData);
				startActivity(intent);
				finish();
			}
		});
	}//onCreate
	
	// 리스트뷰 한개에 들어갈 내용을 갖는 클래스
	private class CourseListItem {
		private String strCourseTitle;
		private String strBookName;
		private boolean bBookPossess;
		
		public CourseListItem(String _title, String _book, boolean _possess) {
			this.strCourseTitle = _title;
			this.strBookName = _book;
			this.bBookPossess = _possess;
		}
		
		public String getStrCourseTitle() { return strCourseTitle; }
		public String getStrBookName() { return strBookName; }
		public boolean getBookPossess() { return bBookPossess; }
		public void setBookPossess(boolean bBookPossess) { this.bBookPossess = bBookPossess;}
	}
	
	// 리스트뷰 한개의 뷰를 관리하는 클래스
	private class CourseListViewHolder {
		private TextView tvCourseTitle;
		private TextView tvBookName;
		private CheckBox chkBookExist;
		
		public TextView getTextCourseTitle() { return tvCourseTitle; }
		public TextView getTextCourseBook() { return tvBookName; }
		public CheckBox getChkCourse() { return chkBookExist; }
		
		public void setTextCourseTitle(TextView titleView) { this.tvCourseTitle = titleView; }
		public void setTextBookName(TextView bookName) { this.tvBookName = bookName; }
		public void setChkBookExist(CheckBox check) { this.chkBookExist = check; }
	}
	
	// 리스트뷰를 컨트롤하는 클래스
	private class CourseListAdapter extends BaseAdapter {

		private ArrayList<CourseListItem> courseListData;
		private Context context;
		
		public CourseListAdapter(Context _context){
			super();
			
			this.courseListData = new ArrayList<CourseListItem>();
			this.context = _context;
		}
				
		@Override
		public int getCount() {
			return courseListData.size();
		}

		@Override
		public Object getItem(int position) {
			return courseListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CourseListViewHolder holder;
			if(convertView == null){
				holder = new CourseListViewHolder();
				
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.list_bookregister, null);
				holder.setTextCourseTitle((TextView)convertView.findViewById(R.id.text_bookregister_coursetitle));
				holder.setTextBookName((TextView)convertView.findViewById(R.id.text_bookregister_coursebook));
				holder.setChkBookExist((CheckBox)convertView.findViewById(R.id.chk_bookregister_course));
				
				//스크롤 할때 체크가 뒤섞이는 문제 방지
				holder.getChkCourse().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						int position = (Integer)buttonView.getTag();
						courseListData.get(position).setBookPossess(buttonView.isChecked());
					}
				});
				
				convertView.setTag(holder);
				convertView.setTag(R.id.text_bookregister_coursetitle, holder.getTextCourseTitle());
				convertView.setTag(R.id.text_bookregister_coursebook, holder.getTextCourseBook());
				convertView.setTag(R.id.chk_bookregister_course,holder.getChkCourse());
				
			}else{
				holder = (CourseListViewHolder) convertView.getTag();
			}
			
			holder.getChkCourse().setTag(position);
			
			CourseListItem data = courseListData.get(position);
			holder.getTextCourseTitle().setText(data.getStrCourseTitle());
			holder.getTextCourseBook().setText(data.getStrBookName());
			holder.getChkCourse().setChecked(data.getBookPossess());
			
			return convertView;
		}
		
		public void addItem(CourseListItem addItem){
			courseListData.add(addItem);
		}
		
		public ArrayList<CourseListItem> getCourseListData(){
			return courseListData;
		}
	}
	
	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}
	
}
