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
			
			String createUserInfo = "CREATE TABLE " + UserInfoTable.tableName + "("
					+ "userNo INTEGER NOT NULL, "
					+ "portalID VARCHAR(30) NOT NULL, "
					+ "studentID VARCHAR(15) NOT NULL, "
					+ "gcmRegID TEXT, "
					+ "kakaoID TEXT, "
					+ "PRIMARY KEY(userNo)"+ ")";
			
			String createCourseInfo = "CREATE TABLE " + CourseInfoTable.tableName + "("
					+ "code VARCHAR(10) NOT NULL, "
					+ "name TEXT, "
					+ "PRIMARY KEY(code)" +")";
			
			String createChatLog = "CREATE TABLE " + ChatLogTable.tableName + "("
					+ "roomNo INTEGER NOT NULL, "
					+ "timestamp VARCHAR(64) NOT NULL, "
					+ "content TEXT, "
					+ "sender INTEGER NOT NULL, "
					+ "PRIMARY KEY(roonNo, timestamp)" + ")";
			
			String createChatRoom = "CREATE TABLE " + ChatRoomTable.tableName + "("
					+ "roomNo INTEGER NOT NULL, "
					+ "productNo INTEGER NOT NULL, "
					+ "customer INTEGER NOT NULL, "
					+ "PRIMARY KEY(roomNo)" + ")";
			
			String createProduct = "CREATE TABLE " + ProductTable.tableName + "("
					+ "productNo INTEGER NOT NULL, "
					+ "seller INTEGER NOT NULL, "
					+ "course VARCHAR(10), "
					+ "professor VARCHAR(20), "
					+ "bookName TEXT, "
					+ "price VARCHAR(10), "
					+ "etc TEXT, "
					+ "register VARCHAR(10), "
					+ "sellResult VARCHAR(10), "
					+ "picture TEXT,"
					+ "PRIMARY KEY(productNo)" + ")";
			
			String createProductComplete = "CREATE TABLE " + ProductCompleteTable.tableName + "("
					+ "productNo INTEGER NOT NULL, "
					+ "buyer INTEGER NOT NULL, "
					+ "PRIMARY KEY(productNo, buyer)" + ")";
			
			String createQuestion = "CREATE TABLE " + QuestionTable.tableName + "("
					+ "productNo INTEGER NOT NULL, "
					+ "timestamp VARCHAR(64) NOT NULL, "
					+ "content TEXT NOT NULL, "
					+ "writer INTEGER NOT NULL, "
					+ "type VARCHAR(10) NOT NULL, "
					+ "PRIMARY KEY(productNo, timestamp)" + ")";
			
			db.execSQL(createUserInfo);
			db.execSQL(createCourseInfo);
			db.execSQL(createChatLog);
			db.execSQL(createChatRoom);
			db.execSQL(createProduct);
			db.execSQL(createProductComplete);
			db.execSQL(createQuestion);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			 db.execSQL("DROP TABLE IF EXISTS " + UserInfoTable.tableName);
			 db.execSQL("DROP TABLE IF EXISTS " + ChatLogTable.tableName);
			 db.execSQL("DROP TABLE IF EXISTS " + ChatRoomTable.tableName);
			 db.execSQL("DROP TABLE IF EXISTS " + CourseInfoTable.tableName);
			 db.execSQL("DROP TABLE IF EXISTS " + ProductTable.tableName);
			 db.execSQL("DROP TABLE IF EXISTS " + ProductCompleteTable.tableName);
			 db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.tableName);
			 
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
