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
                "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
