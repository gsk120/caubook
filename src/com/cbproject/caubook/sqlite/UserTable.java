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
	
	// ����� ���� ���̺� ������
	public UserTable(Context context) {
		super(context);
	}
	
	// select ���� �Լ�
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
	
	// portal id�� �ִ��� ������ Ȯ���ϴ� �Լ�
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
	
	// insert ���� �Լ�
	public boolean insertQuery(UserData data) {
		dbWriter = dbOpenner.getWritableDatabase();
		// insert �� value �����
		ContentValues val = new ContentValues();
		val.put("portalID", data.getPortalID());
		
		// ������ ���̽� insert ���� ���� ��� Ȯ��
		if (dbWriter.insert(tableName, null, val) != -1) {
			return true;
		}
		return false;
	}
}
