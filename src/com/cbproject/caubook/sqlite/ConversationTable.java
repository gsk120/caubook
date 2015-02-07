package com.cbproject.caubook.sqlite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.cbproject.caubook.MessageTypeEnum;

public class ConversationTable extends DBManager{
	
	public static final String tableName = "conversaton";
	
	// 대화 내용 테이블 원소 클래스
	public static class ConversationData {
		private int userID;
		private int order;
		private String content;
		private Date time;
		private MessageTypeEnum msgType;
		
		public ConversationData(int _id, int _order, String _content, Date _time, MessageTypeEnum _type) {
			this.userID = _id;
			this.order = _order;
			this.content = _content;
			this.time = _time;
			this.msgType = _type;
		}
		
		public int getUserID() { return this.userID; }
		public int getOrder() { return this.order; }
		public String getContent() { return this.content; }
		public Date getTime() { return this.time; }
		public MessageTypeEnum getMessageType() { return this.msgType; }
	}
	
	// 대화내용 테이블 객체 생성자
	public ConversationTable(Context context) {
		super(context);
	}
	
	// select 쿼리 함수
	public ConversationData selectQuery(int keyID, int keyOrder) {
		dbReader = dbOpenner.getReadableDatabase();
		Cursor cursor = dbReader.query(tableName, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			// 합성키가 원하는 값하고 같은 경우 select
			if(cursor.getInt(cursor.getColumnIndex("userID")) == keyID &&
					cursor.getInt(cursor.getColumnIndex("orderNum")) == keyOrder) {
				String strContent = cursor.getString(cursor.getColumnIndex("content"));
				String strTime = cursor.getString(cursor.getColumnIndex("time"));
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strType = cursor.getString(cursor.getColumnIndex("msgType"));
				try {
					// 데이터 객체 만들어 리턴
					return new ConversationData(keyID, keyOrder, strContent, sdFormat.parse(strTime), MessageTypeEnum.valueOf(strType));
				} catch (ParseException e) {
					Log.e("ParseError", "Conversation data parse error");
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}
	
	// 해당하는 userID의 모든 대화 내용을 뽑는 함수
	public ArrayList<ConversationData> selectAllQuery(int userID) {
		dbReader = dbOpenner.getReadableDatabase();
		ArrayList<ConversationData> resultList = new ArrayList<ConversationTable.ConversationData>();
		Cursor cursor = dbReader.query(tableName, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			int keyID = cursor.getInt(cursor.getColumnIndex("userID"));
			if(keyID == userID) {
				int keyOrder = cursor.getInt(cursor.getColumnIndex("orderNum")); 
				String strContent = cursor.getString(cursor.getColumnIndex("content"));
				String strTime = cursor.getString(cursor.getColumnIndex("time"));
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strType = cursor.getString(cursor.getColumnIndex("msgType"));
				try {
					// 리스트에 추가
					resultList.add(new ConversationData(keyID, keyOrder, strContent, sdFormat.parse(strTime), MessageTypeEnum.valueOf(strType)));
				} catch (ParseException e) {
					Log.e("ParseError", "Conversation data parse error");
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}
		
	// insert 쿼리 함수
	public boolean insertQuery(ConversationData data) {
		dbWriter = dbOpenner.getWritableDatabase();
		// insert 할 value 만들기
		ContentValues val = new ContentValues();
		val.put("userID", data.getUserID());
		val.put("orderNum", data.getOrder());
		val.put("content", data.getContent());
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		val.put("time", sdFormat.format(data.getTime()));
		val.put("msgType", data.getMessageType().name());
		
		// 데이터 베이스 insert 쿼리 실행 결과 확인
		if (dbWriter.insert(tableName, null, val) != -1) {
			return true;
		}
		return false;
	}
}
