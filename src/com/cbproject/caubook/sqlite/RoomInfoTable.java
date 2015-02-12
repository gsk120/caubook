package com.cbproject.caubook.sqlite;

import android.content.ContentValues;
import android.content.Context;

public class RoomInfoTable extends DBManager{

	public static final String tableName = "roominfo";
	
	// 방정보 튜플하나의 정보
	public static class RoomData {
		private int roomID;
		private int firstUser;
		private int secondUser;
		
		public RoomData (int room_id, int user1, int user2) {
			this.roomID = room_id;
			this.firstUser = user1;
			this.secondUser = user2;
		}
		
		public int getRoomID() { return this.roomID; }
		public int getFirstUser() { return this.firstUser; }
		public int getSecondUser() { return this.secondUser; }
	}
	
	// 방정보 테이블 객체 생성자
	public RoomInfoTable(Context context) {
		super(context);
	}

	// insert 쿼리 함수
	public boolean insertQuery(RoomData data) {
		dbWriter = dbOpenner.getWritableDatabase();
		// insert 할 value 만들기
		ContentValues val = new ContentValues();
		val.put("room_id", data.getRoomID());
		val.put("first_user", data.getFirstUser());
		val.put("second_user", data.getSecondUser());
		
		// 데이터 베이스 insert 쿼리 실행 결과 확인
		if (dbWriter.insert(tableName, null, val) != -1) {
			return true;
		}
		return false;
	}
}
