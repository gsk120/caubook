package com.cbproject.caubook.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class UserTable extends DBManager {
	
	public static final String tableName = "User";
	
	public static class UserData {
		private int userID;
		private String portalID;
		
		public UserData(int _id, String _portal) {
			this.userID = _id;
			this.portalID = _portal;
		}
		
		public int getUserID() { return this.userID; }
		public String getPortalID() { return this.portalID; }
	}
	
	// 사용자 정보 테이블 생성자
	public UserTable(Context context) {
		super(context);
	}
	
	// select 쿼리 함수
	public UserData selectQuery(int key) {
		dbReader = dbOpenner.getReadableDatabase();
		Cursor cursor = dbReader.query(tableName, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			if(cursor.getInt(cursor.getColumnIndex("userID")) == key) {
				return new UserData(key, cursor.getString(cursor.getColumnIndex("portalID")));
			}
		}
		return null;
	}
	
	// portal id가 있는지 없는지 확인하는 함수
	public boolean isExist(String portal) {
		dbReader = dbOpenner.getReadableDatabase();
		Cursor cursor = dbReader.query(tableName, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			if(cursor.getString(cursor.getColumnIndex("portalID")).equals(portal)) {
				return true;
			}
		}
		return false;
	}
	
	// insert 쿼리 함수
	public boolean insertQuery(UserData data) {
		dbWriter = dbOpenner.getWritableDatabase();
		// insert 할 value 만들기
		ContentValues val = new ContentValues();
		val.put("portalID", data.getPortalID());
		
		// 데이터 베이스 insert 쿼리 실행 결과 확인
		if (dbWriter.insert(tableName, null, val) != -1) {
			return true;
		}
		return false;
	}
}
