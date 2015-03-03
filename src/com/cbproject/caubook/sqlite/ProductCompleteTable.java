package com.cbproject.caubook.sqlite;

import android.content.Context;

public class ProductCompleteTable extends DBManager{

	public static final String tableName = "productComplete";
	
	// 상품 정보 튜플하나의 정보
	public static class ProductCompleteData{
		private int productNo;
		private int buyer;
		
		public ProductCompleteData(int _product, int _buyer) { 
			this.productNo = _product;
			this.buyer = _buyer;
		}
		
		public int getProductNo() {	return productNo; }
		public int getBuyer() { return buyer; }
	}
	
	// 상품 정보 테이블 객체 생성자
	public ProductCompleteTable(Context context) {
		super(context);
	}
	
	// TODO sqlite에 맞는 쿼리 함수 제작해야함
}
