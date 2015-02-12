package com.cbproject.caubook.controller;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.cbproject.caubook.SelectedCourseListItem;

public class ParseHandler extends DefaultHandler{
	
	private String position = "";		// ���� �а� �ִ� �±��� ��ġ
	private String value = "";
	private SelectedCourseListItem courseItem = null;		//�Ľ��� �ϳ��� �������� �ӽ� �����ϴ� ��ü
	private ArrayList<SelectedCourseListItem> courseList;	//�Ľ��� ����Ʈ�� ����ִ� ��ü
	
	// �Ľ��� ��� ����Ʈ ������ �Լ�
	public ArrayList<SelectedCourseListItem> getParsedData() {
		return this.courseList;
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
            courseItem.setStrBookName("");
        } else if (qName.equals("kor_nm")) {
            position = "kor_nm";
            value = atts.getValue("value");
            courseItem.setStrBookName(value);
        }
    }
 
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
         if(qName.equals("map")) {
        	 if(!courseItem.getStrBookName().equals("")) {
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
