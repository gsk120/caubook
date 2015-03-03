package com.cbproject.caubook.sqlite;

import android.content.Context;

public class CourseInfoTable extends DBManager{

	public static final String tableName = "courseInfo";
	
	// 상품 정보 튜플하나의 정보
	public static class CourseInfoData{
		private String code;
		private String name;
		
		public CourseInfoData(String _code, String _name) {
			this.code = _code;
			this.name = _name;
		}
		
		public String getCode() { return code; }
		public String getName() { return name; }
	}
	
	// 상품 정보 테이블 객체 생성자
	public CourseInfoTable(Context context) {
		super(context);
	}
	
	// TODO sqlite에 맞는 쿼리 함수 제작해야함
}
