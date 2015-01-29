package com.cbproject.caubook;

import java.io.Serializable;

import android.widget.ImageView;

public class SelectedCourseListItem implements Serializable{
	private static final long serialVersionUID = 1L;	// Serializable ID
	private String strCourseTitle;		//과목명
	private String strProfessorName;	//교수명
	private String strBookName;			//책이름
	private String strBookPrice;		//책가격
	private String strEtc;				//기타
	private ImageView imgBook;			//int로 id값 받을지 나중에 일단은 ImageView
	private boolean bBookSell;			//미완성 책 목록 리스트에서 판매 등록 완료되면 true로 변경
	
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
