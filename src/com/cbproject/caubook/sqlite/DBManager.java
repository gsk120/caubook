package com.cbproject.caubook.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {

	// 데이터 베이스의 이름 및 버전 등의 핵심 정보
	public final String dbName = "unibook_db";
	private final int dbVersion = 1;
	
	// SQLite 데이터 베이스 오프너와 디비 읽기 및 쓰기 전용 객체 선언
	protected DBOpenner dbOpenner; 
	protected SQLiteDatabase dbReader;
	protected SQLiteDatabase dbWriter;
	
	// 데이터 베이스 오픈 관리 클래스 => DBManager 외부에선 관리할 필요가 없어 내부클래스로 선언
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
			 
	         // 데이터베이스 재생성
	         onCreate(db);
		}
	}
	
	// DBManager 생성자
	public DBManager(Context context) {
		this.dbOpenner = new DBOpenner(context);
	}
	
	// 데이터 베이스 종료 함수
	public void Close() {
		dbReader.close();
		dbWriter.close();
	}
}
