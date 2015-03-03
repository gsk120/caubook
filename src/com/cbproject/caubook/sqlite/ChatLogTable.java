package com.cbproject.caubook.sqlite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ChatLogTable extends DBManager{
	
	public static final String tableName = "chatLog";
	
	// 대화 내용 테이블 원소 클래스
	public static class ChatLogData {
		private int roomNo;
		private String timestamp;
		private String content;
		private int sender;
		
		public ChatLogData(int _no, String _time, String _content, int _sender) {
			this.roomNo = _no;
			this.timestamp = _time;
			this.content = _content;
			this.sender = _sender;
		}
		
		public int getRoomNo() { return this.roomNo; }
		public String getTimeStamp() { return this.timestamp; }
		public String getContent() { return this.content; }
		public int getSender() { return this.sender; }
	}
	
	// 대화내용 테이블 객체 생성자
	public ChatLogTable(Context context) {
		super(context);
	}
	
	// select 쿼리 함수
	public ChatLogData selectQuery(int _room, String _time) {
		dbReader = dbOpenner.getReadableDatabase();
		Cursor cursor = dbReader.query(tableName, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			// 합성키가 원하는 값하고 같은 경우 select
			if(cursor.getInt(cursor.getColumnIndex("roomNo")) == _room &&
					cursor.getString(cursor.getColumnIndex("timestamp")).equals(_time)) {
				String strContent = cursor.getString(cursor.getColumnIndex("content"));
				int senderId = cursor.getInt(cursor.getColumnIndex("sender"));
				
				// 데이터 객체 만들어 리턴
				return new ChatLogData(_room, _time, strContent, senderId);
			}
		}
		return null;
	}
	
	// 해당하는 userID의 모든 대화 내용을 뽑는 함수
	public ArrayList<ChatLogData> selectAllQuery(int roomNo) {
		dbReader = dbOpenner.getReadableDatabase();
		ArrayList<ChatLogData> resultList = new ArrayList<ChatLogTable.ChatLogData>();
		Cursor cursor = dbReader.query(tableName, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			int keyID = cursor.getInt(cursor.getColumnIndex("roomNo"));
			if(keyID == roomNo) {
				String timestamp = cursor.getString(cursor.getColumnIndex("timestamp")); 
				String strContent = cursor.getString(cursor.getColumnIndex("content"));
				int senderId = cursor.getInt(cursor.getColumnIndex("sender"));
				// 리스트에 추가
				resultList.add(new ChatLogData(keyID, timestamp, strContent, senderId));
			}
		}
		return resultList;
	}
		
	// insert 쿼리 함수
	public boolean insertQuery(ChatLogData data) {
		dbWriter = dbOpenner.getWritableDatabase();
		// insert 할 value 만들기
		ContentValues val = new ContentValues();
		val.put("roomNo", data.getRoomNo());
		val.put("timestamp", data.getTimeStamp());
		val.put("content", data.getContent());
		val.put("sender", data.getSender());
		
		// 데이터 베이스 insert 쿼리 실행 결과 확인
		if (dbWriter.insert(tableName, null, val) != -1) {
			return true;
		}
		return false;
	}
}
