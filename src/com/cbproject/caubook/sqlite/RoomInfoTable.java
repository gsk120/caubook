package com.cbproject.caubook.sqlite;

import android.content.ContentValues;
import android.content.Context;

public class RoomInfoTable extends DBManager{

	public static final String tableName = "roominfo";
	
	// ������ Ʃ���ϳ��� ����
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
	
	// ������ ���̺� ��ü ������
	public RoomInfoTable(Context context) {
		super(context);
	}

	// insert ���� �Լ�
	public boolean insertQuery(RoomData data) {
		dbWriter = dbOpenner.getWritableDatabase();
		// insert �� value �����
		ContentValues val = new ContentValues();
		val.put("room_id", data.getRoomID());
		val.put("first_user", data.getFirstUser());
		val.put("second_user", data.getSecondUser());
		
		// ������ ���̽� insert ���� ���� ��� Ȯ��
		if (dbWriter.insert(tableName, null, val) != -1) {
			return true;
		}
		return false;
	}
}
