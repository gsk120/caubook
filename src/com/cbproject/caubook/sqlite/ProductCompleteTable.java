package com.cbproject.caubook.sqlite;

import android.content.Context;

public class ProductCompleteTable extends DBManager{

	public static final String tableName = "productComplete";
	
	// ��ǰ ���� Ʃ���ϳ��� ����
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
	
	// ��ǰ ���� ���̺� ��ü ������
	public ProductCompleteTable(Context context) {
		super(context);
	}
	
	// TODO sqlite�� �´� ���� �Լ� �����ؾ���
}
