package com.cbproject.caubook.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {

	// ������ ���̽��� �̸� �� ���� ���� �ٽ� ����
	public final String dbName = "unibook_db";
	private final int dbVersion = 1;
	
	// SQLite ������ ���̽� �����ʿ� ��� �б� �� ���� ���� ��ü ����
	protected DBOpenner dbOpenner; 
	protected SQLiteDatabase dbReader;
	protected SQLiteDatabase dbWriter;
	
	// ������ ���̽� ���� ���� Ŭ���� => DBManager �ܺο��� ������ �ʿ䰡 ���� ����Ŭ������ ����
	class DBOpenner extends SQLiteOpenHelper {
		public DBOpenner(Context context) {
			super(context, dbName, null, dbVersion);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			String createUserTable = "CREATE TABLE " + UserTable.tableName + "("
					+ "userID INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "portalID INTEGER"+ ")";
			String createConversationTable = "CREATE TABLE " + ConversationTable.tableName + "("
					+ "userID INTEGER, "
					+ "orderNum INTEGER, "
					+ "content TEXT, "
					+ "time TEXT, "
					+ "msgType TEXT, "
					+ "PRIMARY KEY(userID, orderNum)" + ")";
			db.execSQL(createUserTable);
			db.execSQL(createConversationTable);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			 db.execSQL("DROP TABLE IF EXISTS " + UserTable.tableName);
			 db.execSQL("DROP TABLE IF EXISTS " + ConversationTable.tableName);
			 
	         // �����ͺ��̽� �����
	         onCreate(db);
		}
	}
	
	// DBManager ������
	public DBManager(Context context) {
		this.dbOpenner = new DBOpenner(context);
	}
	
	// ������ ���̽� ���� �Լ�
	public void Close() {
		dbReader.close();
		dbWriter.close();
	}
}
