package com.cbproject.caubook.controller;

import java.util.ArrayList;

import com.cbproject.caubook.SelectedCourseListItem;
import com.cbproject.caubook.activities.MainActivity;
import com.cbproject.caubook.activities.MyBookRegisterActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;

public class AlertDialogHandler {
	
	Context context;
	ArrayList<SelectedCourseListItem> selectedCourseListData;
	
	public AlertDialogHandler(Context _context) {
		context = _context;
	}
	
	public AlertDialog AlertDialogLogout(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setTitle("로그아웃");
		builder.setMessage("로그아웃 하시겠습니까?");
		//builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("로그아웃", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface alertDialogLogout, int which) {
				
				SharedPreferences pref = ((Activity)context).getSharedPreferences("savedInfo", 
																		((Activity)context).MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.remove("id");
				editor.remove("pw");
				editor.remove("auto_login");
				editor.commit();
				
				Intent intent = new Intent(context, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				((Activity)context).startActivity(intent);
			}
		});
		
		builder.setNegativeButton("아니오", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface alertDialogLogout, int which) {
				alertDialogLogout.cancel();
			}
		});
		
		AlertDialog alertDialogLogout = builder.create();
		
		return alertDialogLogout;
	}
	
	public AlertDialog AlertDialogMyBookRegisterModify(ArrayList<SelectedCourseListItem> _selectedCourseListData){
		selectedCourseListData = _selectedCourseListData;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setTitle("책 등록 수정");
		builder.setMessage("수정 하시겠습니까?");
		//builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("예", new OnClickListener() {

			@Override
			public void onClick(DialogInterface alerDialogMyBookRegisterModify, int which) {
				Intent intent = new Intent(context, MyBookRegisterActivity.class);
				intent.putExtra("selectedCourseListData", selectedCourseListData);
            	((Activity)context).startActivity(intent);
            	((Activity)context).finish();
			}
			
		});
		
		builder.setNegativeButton("아니오", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface alerDialogMyBookRegisterModify, int which) {
				alerDialogMyBookRegisterModify.cancel();
			}
		});
		
		AlertDialog alerDialogMyBookRegisterModify = builder.create();
		
		return alerDialogMyBookRegisterModify;
	}
	
	public AlertDialog AlertDialogMyBookRegisterInit(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setTitle("책 등록 초기화");
		builder.setMessage("초기화 하시겠습니까?");
		//builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("예", new OnClickListener() {

			@Override
			public void onClick(DialogInterface alerDialogMyBookRegisterInit, int which) {
				Intent intent = new Intent(context, MyBookRegisterActivity.class);
				((Activity)context).startActivity(intent);
            	((Activity)context).finish();
			}
			
		});
		
		builder.setNegativeButton("아니오", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface alerDialogMyBookRegisterInit, int which) {
				alerDialogMyBookRegisterInit.cancel();
			}
		});
		
		AlertDialog alerDialogMyBookRegisterInit = builder.create();
		
		return alerDialogMyBookRegisterInit;
	}
	
	public AlertDialog AlertDialogExit(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setTitle("종료");
		builder.setMessage("종료 하시겠습니까?");
		//builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("예", new OnClickListener() {

			@Override
			public void onClick(DialogInterface alerDialogExit, int which) {
				((Activity)context).finish();
			}
			
		});
		
		builder.setNegativeButton("아니오", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface alerDialogExit, int which) {
				alerDialogExit.cancel();
			}
		});
		
		AlertDialog alerDialogExit = builder.create();
		
		return alerDialogExit;
	}
}
