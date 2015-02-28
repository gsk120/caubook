package com.cbproject.caubook.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;
import com.cbproject.caubook.sqlite.ProductTable.ProductData;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
				// 음.. 여기서 유저의 기본키 임시로 10으로 하드코딩! -> 나중에는 로그인 중에 자신의 정보를 갖고 있어야함.
				new CreateChatRoom().execute(productInfo.getProductNo(), 8);
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
		productInfo = new ProductData(2, 13, 111111, "test course", 20000, true, false, "");
		
	}
	
	private class CreateChatRoom extends AsyncTask<Integer, Void, String> {

		@Override
		protected String doInBackground(Integer... params) {
			return createChatRoom(params[0], params[1]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// result 값에 따라 새로운 방인지, 기존의 방인지 확인 후 대응
			result = Html.fromHtml(result).toString().trim();
			Log.e("결과", result);
			if(result.equals("1")) {
				// 신규 채팅방 개설 성공
				Log.e("chat_room_result", "신규");
				Intent intent = new Intent(ShowBookInfoActivity.this, ChattingActivity.class);
				intent.putExtra("product", productInfo);
				intent.putExtra("roomNo", 2);	//임시 방번호
				startActivity(intent);
			} else {
				// 기존 채팅방 존재 => TODO 이경우 기존 chatLog를 불러와아함.
				Log.e("chat_room_result", "기존");
				Intent intent = new Intent(ShowBookInfoActivity.this, ChattingActivity.class);
				intent.putExtra("product", productInfo);
				intent.putExtra("roomNo", 2);	//임시 방번호
				startActivity(intent);
			}
		}
		
		// doInBackground에서 실제로 작동하는 함수
		private String createChatRoom(int product, int customer) {
			URL url = null;
			String line = "";
			String lineResult = "";
			try {
				url = new URL("http://54.92.63.117:3000/createChatRoom");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				OutputStream os = conn.getOutputStream();
				
				//cookie = URLEncoder.encode(cookie, "UTF-8");
				String outString = "productNo=" + product + "&" + "customer=" + customer;
				
				os.write(outString.getBytes("UTF-8"));
				os.flush();
				os.close();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				while((line = br.readLine()) != null){
					lineResult += line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				lineResult = URLDecoder.decode(lineResult, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return lineResult;
		}
	}
}
