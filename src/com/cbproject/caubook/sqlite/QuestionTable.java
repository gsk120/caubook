package com.cbproject.caubook.sqlite;

import android.content.Context;

public class QuestionTable extends DBManager{

	public static final String tableName = "question";
	
	// 상품 정보 튜플하나의 정보
	public static class QuestionData {
		private int productNo;
		private String timestamp;
		private String content;
		private int writer;
		private String type;
		
		public QuestionData(int _product, String _time, String _content, int _writer, String _type) {
			this.productNo = _product;
			this.timestamp = _time;
			this.content = _content;
			this.writer = _writer;
			this.type = _type;
		}

		public int getProductNo() { return productNo; }
		public String getTimestamp() { return timestamp; }
		public String getContent() { return content; }
		public int getWriter() { return writer; }
		public String getType() { return type; }
		
	}
	
	// 상품 정보 테이블 객체 생성자
	public QuestionTable(Context context) {
		super(context);
	}
	
	// TODO sqlite에 맞는 쿼리 함수 제작해야함
}
