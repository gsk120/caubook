package com.cbproject.caubook.controller;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.cbproject.caubook.SelectedCourseListItem;

public class ParseHandler extends DefaultHandler{
	
	private String position = "";		// 현재 읽고 있는 태그의 위치
	private String value = "";
	private SelectedCourseListItem courseItem = null;		//파싱한 하나의 아이템을 임시 저장하는 객체
	private ArrayList<SelectedCourseListItem> courseList;	//파싱한 리스트를 담고있는 객체
	private String studentNo = "";
	
	// 파싱한 결과 리스트 보내는 함수
	public ArrayList<SelectedCourseListItem> getParsedData() {
		return this.courseList;
	}
	
	public String getStudentNo() {
		return this.studentNo;
	}
	
	@Override
    public void startDocument() throws SAXException {
		this.courseList = new ArrayList<SelectedCourseListItem>();
    }
 
    @Override
    public void endDocument() throws SAXException {
        // Nothing to do
    }
 
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        if (qName.equals("map")) {
            courseItem = new SelectedCourseListItem();
            courseItem.setStrCourseTitle("");
            courseItem.setCourseCode("");
        } else if (qName.equals("kor_nm")) {
            position = "kor_nm";
            value = atts.getValue("value");
            courseItem.setStrCourseTitle(value);
        } else if (qName.equals("sbjt_clss")) {
        	position = "sbjt_clss";
        	value = atts.getValue("value");
        	courseItem.setCourseCode(value);
        } else if (qName.equals("std_no")) {
        	position = "std_no";
        	studentNo = atts.getValue("value");
        }
    }
 
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
         if(qName.equals("map")) {
        	 if(!courseItem.getCourseCode().equals("")) {
        		 courseList.add(courseItem);
        	 }
         }
    }
 
     
    @Override
    public void characters(char ch[], int start, int length) {
        if(position.equals("kor_nm")) {
        	courseItem.setStrBookName(value);
        	position = "";
        }
    }
}
