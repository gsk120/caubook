package com.cbproject.caubook.sqlite;

import android.content.ContentValues;
import android.content.Context;

public class ChatRoomTable extends DBManager{

	public static final String tableName = "chatRoom";
	
	// 방정보 튜플하나의 정보
	public static class ChatRoomData {
		private int roomNo;
		private int productNo;
		private int customer;
		
		public ChatRoomData (int room_no, int product, int client) {
			this.roomNo = room_no;
			this.productNo = product;
			this.customer = client;
		}
		
		public int getRoomNo() { return this.roomNo; }
		public int getProductNo() { return this.productNo; }
		public int getCustomer() { return this.customer; }
	}
	
	// 방정보 테이블 객체 생성자
	public ChatRoomTable(Context context) {
		super(context);
	}

	// insert 쿼리 함수
	public boolean insertQuery(ChatRoomData data) {
		dbWriter = dbOpenner.getWritableDatabase();
		// insert 할 value 만들기
		ContentValues val = new ContentValues();
		val.put("roomNo", data.getRoomNo());
		val.put("productNo", data.getProductNo());
		val.put("customer", data.getCustomer());
		
		// 데이터 베이스 insert 쿼리 실행 결과 확인
		if (dbWriter.insert(tableName, null, val) != -1) {
			return true;
		}
		return false;
	}
}
