package com.example.ftechdevice.Common.TokenManger;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    // tạo hàm để lấy avf lưu token của người dùng
    public static final String FIRST_START_APP = "FIRST_START_APP";
    public static final String FIRST_START_APP_VALUE = "FIRST_START_APP";

    public static void saveFirstStart(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FIRST_START_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_START_APP, false);
        editor.apply();
    }

    public static boolean isFirstStart(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FIRST_START_APP, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(FIRST_START_APP, true);
    }
}