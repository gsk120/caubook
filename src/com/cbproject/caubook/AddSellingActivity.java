package com.cbproject.caubook;

import java.util.ArrayList;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AddSellingActivity extends ActionBarActivity {
	
	Button btnBookRegisterGo;
	
	private ListView listCourse;
	private CourseListAdapter listCourseAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_add_selling);
	
<<<<<<< HEAD:src/com/cbproject/caubook/AddSellingActivity.java
		btnBookRegisterGo = (Button)findViewById(R.id.btn_book_register_go);
		
		btnBookRegisterGo.setOnClickListener(new OnClickListener() {
=======
		btn_book_register = (Button)findViewById(R.id.btn_book_register);
		btn_book_register.setOnClickListener(new OnClickListener() {
>>>>>>> 7807cd53d7e1f2d9841347b9618ccfad1a1c725b:src/com/cbproject/caubook/BookRegisterSelectActivity.java
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TradeTabActivity.class);
				startActivity(intent);
			}
		});
		
		listCourse = (ListView)findViewById(R.id.list_book_register);
		listCourseAdapter = new CourseListAdapter(this);
		listCourseAdapter.addItem(new CourseListItem("선형대수학", "선형대수학 5판", false));
		listCourseAdapter.addItem(new CourseListItem("자료구조", "Data Structure", false));
		listCourseAdapter.addItem(new CourseListItem("알고리즘", "Algorithm 3판", false));
		listCourseAdapter.addItem(new CourseListItem("결혼과가족", "결혼과 가족", false));
		listCourseAdapter.addItem(new CourseListItem("OOP", "객체지향 프로그래밍", false));
		listCourseAdapter.addItem(new CourseListItem("운영체제", "Operating System", false));
		listCourseAdapter.addItem(new CourseListItem("리눅스 시스템", "Linux programming", false));
		listCourseAdapter.addItem(new CourseListItem("C프로그래밍", "C Programming", false));
		listCourseAdapter.addItem(new CourseListItem("자바프로그래밍", "Java Programming", false));
		listCourseAdapter.addItem(new CourseListItem("어셈블리", "Assembly", false));
		listCourseAdapter.addItem(new CourseListItem("공업수학", "Engineering math", false));
		listCourseAdapter.addItem(new CourseListItem("앱개발프로그래밍", "App android programming", false));
		
		
		listCourse.setAdapter(listCourseAdapter);
	}

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
		public boolean isBookPossess() { return bBookPossess; }
	}
	
	// 리스트뷰 한개의 뷰를 관리하는 클래스
	private class CourseListViewHolder {
		private TextView textCourseTitle;
		private TextView textBookName;
		private CheckBox chkBookExist;
		
		public TextView getTextCourseTitle() { return textCourseTitle; }
		public TextView getTextCourseBook() { return textBookName; }
		public CheckBox getChkCourse() { return chkBookExist; }
		
		public void setTextCourseTitle(TextView titleView) { this.textCourseTitle = titleView; }
		public void setTextBookName(TextView bookName) { this.textBookName = bookName; }
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
		
		public ArrayList<CourseListItem> getAlbumList() {
			return this.courseListData;
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
				convertView.setTag(holder);
				
			}else{
				holder = (CourseListViewHolder) convertView.getTag();
			}
			
			CourseListItem data = courseListData.get(position);
			holder.getTextCourseTitle().setText(data.getStrCourseTitle());
			holder.getTextCourseBook().setText(data.getStrBookName());
			
			return convertView;
		}
		
		public void addItem(CourseListItem addItem){
			courseListData.add(addItem);
		}
	}
	
}
