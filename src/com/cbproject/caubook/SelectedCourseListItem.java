package com.cbproject.caubook;

import java.io.Serializable;

import android.widget.ImageView;

public class SelectedCourseListItem implements Serializable{
	private static final long serialVersionUID = 1L;	// Serializable ID
	private String strCourseTitle;		//�����
	private String strProfessorName;	//������
	private String strBookName;			//å�̸�
	private String strBookPrice;		//å����
	private String strEtc;				//��Ÿ
	private ImageView imgBook;			//int�� id�� ������ ���߿� �ϴ��� ImageView
	private boolean bBookSell;			//�̿ϼ� å ��� ����Ʈ���� �Ǹ� ��� �Ϸ�Ǹ� true�� ����
	
	public String getStrCourseTitle() {
		return strCourseTitle;
	}
	public void setStrCourseTitle(String strCourseTitle) {
		this.strCourseTitle = strCourseTitle;
	}
	public String getStrProfessorName() {
		return strProfessorName;
	}
	public void setStrProfessorName(String strProfessorName) {
		this.strProfessorName = strProfessorName;
	}
	public String getStrBookName() {
		return strBookName;
	}
	public void setStrBookName(String strBookName) {
		this.strBookName = strBookName;
	}
	public String getStrBookPrice() {
		return strBookPrice;
	}
	public void setStrBookPrice(String strBookPrice) {
		this.strBookPrice = strBookPrice;
	}
	public String getStrEtc() {
		return strEtc;
	}
	public void setStrEtc(String strEtc) {
		this.strEtc = strEtc;
	}
	public ImageView getImgBook() {
		return imgBook;
	}
	public void setImgBook(ImageView imgBook) {
		this.imgBook = imgBook;
	}
	public boolean isbBookSell() {
		return bBookSell;
	}
	public void setbBookSell(boolean bBookSell) {
		this.bBookSell = bBookSell;
	}
	
}
