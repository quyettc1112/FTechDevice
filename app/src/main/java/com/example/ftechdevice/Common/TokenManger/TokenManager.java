package com.example.ftechdevice.Common.TokenManger;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import org.json.JSONObject;

public class TokenManager {

    public static final String FIRST_START_APP = "FIRST_START_APP";
    public static final String FIRST_START_APP_VALUE = "FIRST_START_APP";
    private static final String PREF_NAME = "AuthTokenPrefs";
    private static final String ACCESS_TOKEN_KEY = "access_token";

    public static void saveFirstStart(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FIRST_START_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_START_APP_VALUE, false);
        editor.apply();
    }

    public static boolean isFirstStart(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FIRST_START_APP, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(FIRST_START_APP_VALUE, true);
    }

    // Hàm để lưu accessToken
    public static void saveAccessToken(Context context, String accessToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.apply();
    }

    // Hàm để lấy accessToken
    public static String getAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null);
    }

    // Hàm để xóa accessToken (đăng xuất)
    //
    public static void clearAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ACCESS_TOKEN_KEY);
        editor.apply();
    }

    public static boolean isTokenValid(Context context) {
        String token = getAccessToken(context);
        if (token == null) {
            return false;
        }

        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }

            String payload = new String(Base64.decode(parts[1], Base64.URL_SAFE));
            JSONObject jsonPayload = new JSONObject(payload);

            long exp = jsonPayload.getLong("exp");
            long currentTime = System.currentTimeMillis() / 1000;

            if (currentTime > exp) {
                // Token đã hết hạn, xóa nó
                clearAccessToken(context);
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}