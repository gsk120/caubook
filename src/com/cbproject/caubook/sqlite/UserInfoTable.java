package com.cbproject.caubook.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class UserInfoTable extends DBManager {
	
	public static final String tableName = "userInfo";
	
	public static class UserInfoData {
		private int userNo;
		private String portalID;
		private String studentID;
		private String gcmRegID;
		private String kakaoID;
		
		public UserInfoData(int _no, String _portal, String _student, String _gcm, String _kakao) {
			this.userNo = _no;
			this.portalID = _portal;
			this.studentID = _student;
			this.gcmRegID = _gcm;
			this.kakaoID = _kakao;
		}

		public int getUserNo() { return userNo; }
		public String getPortalID() { return portalID; }
		public String getStudentID() { return studentID; }
		public String getGcmRegID() { return gcmRegID; }
		public String getKakaoID() { return kakaoID; }
	}
	
	// 사용자 정보 테이블 생성자
	public UserInfoTable(Context context) {
		super(context);
	}
	
	// select 쿼리 함수
	public UserInfoData selectQuery(int key) {
		dbReader = dbOpenner.getReadableDatabase();
		Cursor cursor = dbReader.query(tableName, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			if(cursor.getInt(cursor.getColumnIndex("userNo")) == key) {
				return new UserInfoData(key, cursor.getString(cursor.getColumnIndex("portalID")),
						cursor.getString(cursor.getColumnIndex("studentID")),
						cursor.getString(cursor.getColumnIndex("gcmRegID")),
						cursor.getString(cursor.getColumnIndex("kakaoID")));
			}
		}
		return null;
	}
	
	// portal id가 있는지 없는지 확인하는 함수
	public int isExist(String portal) {
		dbReader = dbOpenner.getReadableDatabase();
		Cursor cursor = dbReader.query(tableName, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			if(cursor.getString(cursor.getColumnIndex("portalID")).equals(portal)) {
				return cursor.getInt(cursor.getColumnIndex("userNo"));
			}
		}
		return -1;
	}
	
	// insert 쿼리 함수
	public boolean insertQuery(UserInfoData data) {
		dbWriter = dbOpenner.getWritableDatabase();
		// insert 할 value 만들기
		ContentValues val = new ContentValues();
		val.put("portalID", data.getPortalID());
		val.put("studentID", data.getStudentID());
		val.put("gcmRegID", data.getStudentID());
		val.put("kakaoID", data.getKakaoID());
		
		// 데이터 베이스 insert 쿼리 실행 결과 확인
		if (dbWriter.insert(tableName, null, val) != -1) {
			return true;
		}
		return false;
	}
}
