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
	
	// ��ȭ ���� ���̺� ���� Ŭ����
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
	
	// ��ȭ���� ���̺� ��ü ������
	public ConversationTable(Context context) {
		super(context);
	}
	
	// select ���� �Լ�
	public ConversationData selectQuery(int keyID, int keyOrder) {
		dbReader = dbOpenner.getReadableDatabase();
		Cursor cursor = dbReader.query(tableName, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			// �ռ�Ű�� ���ϴ� ���ϰ� ���� ��� select
			if(cursor.getInt(cursor.getColumnIndex("userID")) == keyID &&
					cursor.getInt(cursor.getColumnIndex("orderNum")) == keyOrder) {
				String strContent = cursor.getString(cursor.getColumnIndex("content"));
				String strTime = cursor.getString(cursor.getColumnIndex("time"));
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strType = cursor.getString(cursor.getColumnIndex("msgType"));
				try {
					// ������ ��ü ����� ����
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
	
	// �ش��ϴ� userID�� ��� ��ȭ ������ �̴� �Լ�
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
					// ����Ʈ�� �߰�
					resultList.add(new ConversationData(keyID, keyOrder, strContent, sdFormat.parse(strTime), MessageTypeEnum.valueOf(strType)));
				} catch (ParseException e) {
					Log.e("ParseError", "Conversation data parse error");
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}
		
	// insert ���� �Լ�
	public boolean insertQuery(ConversationData data) {
		dbWriter = dbOpenner.getWritableDatabase();
		// insert �� value �����
		ContentValues val = new ContentValues();
		val.put("userID", data.getUserID());
		val.put("orderNum", data.getOrder());
		val.put("content", data.getContent());
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		val.put("time", sdFormat.format(data.getTime()));
		val.put("msgType", data.getMessageType().name());
		
		// ������ ���̽� insert ���� ���� ��� Ȯ��
		if (dbWriter.insert(tableName, null, val) != -1) {
			return true;
		}
		return false;
	}
}
