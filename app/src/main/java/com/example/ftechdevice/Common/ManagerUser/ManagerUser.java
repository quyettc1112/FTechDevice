package com.example.ftechdevice.Common.ManagerUser;

import android.content.Context;
import android.content.SharedPreferences;

public class ManagerUser {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE_ID = "role_id";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void saveUserInfo(Context context, String email, String password, String phone, String username, int roleId) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_USERNAME, username);
        editor.putInt(KEY_ROLE_ID, roleId);
        editor.apply();
    }
    public static void clearUserInfo(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
    public static String getEmail(Context context) {
        return getSharedPreferences(context).getString(KEY_EMAIL, null);
    }

    public static String getPassword(Context context) {
        return getSharedPreferences(context).getString(KEY_PASSWORD, null);
    }

    public static String getPhone(Context context) {
        return getSharedPreferences(context).getString(KEY_PHONE, null);
    }

    public static String getUsername(Context context) {
        return getSharedPreferences(context).getString(KEY_USERNAME, null);
    }

    public static int getRoleId(Context context) {
        return getSharedPreferences(context).getInt(KEY_ROLE_ID, -1);
    }
}
