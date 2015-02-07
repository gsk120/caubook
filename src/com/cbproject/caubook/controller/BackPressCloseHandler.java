package com.cbproject.caubook.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
 
public class BackPressCloseHandler {
 
    private long backKeyPressedTime = 0;
    private Toast toast;
 
    private Context context;
 
    public BackPressCloseHandler(Context context) {
        this.context = context;
    }
 
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            ((Activity)context).finish();
            toast.cancel();
        }
    }
 
    public void showGuide() {
        toast = Toast.makeText(context,
                "\'�ڷ�\'��ư�� �ѹ� �� �����ø� ����˴ϴ�.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
