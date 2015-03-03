package com.cbproject.caubook.sqlite;

import java.io.Serializable;

import android.content.Context;

public class ProductTable extends DBManager{

	public static final String tableName = "product";
	
	// 상품 정보 튜플하나의 정보
	public static class ProductData implements Serializable{
		private static final long serialVersionUID = 1L;	// Serializable ID
		private int productNo;
		private int seller;
		private String course;
		private String bookName;
		private String price;
		private String register;
		private String sellResult;
		private String picture;		// 사진은 나중에 비트맵으로 변경
		
		public ProductData (int _no, int _seller, String _course, String _book, String _price, String _reg, String _sell, String _pic) {
			this.productNo = _no;
			this.seller = _seller;
			this.course = _course;
			this.bookName = _book;
			this.price = _price;
			this.register = _reg;
			this.sellResult = _sell;
			this.picture = _pic;
		}
		
		public int getProductNo() { return this.productNo; }
		public int getSeller() { return seller;	}
		public String getCourse() { return course;	}
		public String getBookName() { return bookName; }
		public String getPrice() { return price; }
		public String isRegister() { return register; }
		public String isSellResult() { return sellResult; }
		public String getPicture() { return picture; }
		
	}
	
	// 상품 정보 테이블 객체 생성자
	public ProductTable(Context context) {
		super(context);
	}
	
	// TODO sqlite에 맞는 쿼리 함수 제작해야함
}
