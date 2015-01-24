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
import android.widget.ListView;
import android.widget.TextView;

public class BookRegisterSelectActivity extends ActionBarActivity {
	
	private ListView listCourse;
	private CourseListAdapter listCourseAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_register_select);
	
		Button btnBookRegister = (Button)findViewById(R.id.btn_book_register);
		Button btnRegisterPass = (Button)findViewById(R.id.btn_book_register_pass);
		
		btnBookRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), BookRegisterGoActivity.class);
				startActivity(intent);
			}
		});
		
		btnRegisterPass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ChattingActivity.class);
				startActivity(intent);
			}
		});
		
		// �ӽ÷� ����Ʈ�� �ƹ����� ������ ��.
		listCourse = (ListView)findViewById(R.id.list_book_register);
		listCourseAdapter = new CourseListAdapter(this);
		listCourseAdapter.addItem(new CourseListItem("���������", "��������� 5��", false));
		listCourseAdapter.addItem(new CourseListItem("�ڷᱸ��", "Data Structure", false));
		listCourseAdapter.addItem(new CourseListItem("�˰���", "Algorithm 3��", false));
		listCourseAdapter.addItem(new CourseListItem("��ȥ������", "��ȥ�� ����", false));
		listCourseAdapter.addItem(new CourseListItem("OOP", "��ü���� ���α׷���", false));
		listCourseAdapter.addItem(new CourseListItem("�ü��", "Operating System", false));
		listCourseAdapter.addItem(new CourseListItem("������ �ý���", "Linux programming", false));
		listCourseAdapter.addItem(new CourseListItem("C���α׷���", "C Programming", false));
		listCourseAdapter.addItem(new CourseListItem("�ڹ����α׷���", "Java Programming", false));
		listCourseAdapter.addItem(new CourseListItem("�����", "Assembly", false));
		listCourseAdapter.addItem(new CourseListItem("��������", "Engineering math", false));
		listCourseAdapter.addItem(new CourseListItem("�۰������α׷���", "App android programming", false));
		
		
		listCourse.setAdapter(listCourseAdapter);
	}

	// ����Ʈ�� �Ѱ��� �� ������ ���� Ŭ����
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
	
	// ����Ʈ�� �Ѱ��� �並 �����ϴ� Ŭ����
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
	
	// ����Ʈ�並 ��Ʈ���ϴ� Ŭ����
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
