package com.cbproject.caubook.sqlite;

import android.content.Context;

public class CourseInfoTable extends DBManager{

	public static final String tableName = "courseInfo";
	
	// ��ǰ ���� Ʃ���ϳ��� ����
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
	
	// ��ǰ ���� ���̺� ��ü ������
	public CourseInfoTable(Context context) {
		super(context);
	}
	
	// TODO sqlite�� �´� ���� �Լ� �����ؾ���
}
