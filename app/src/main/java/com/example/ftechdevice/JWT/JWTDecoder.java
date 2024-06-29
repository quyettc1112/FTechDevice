package com.example.ftechdevice.JWT;

import static com.example.ftechdevice.Common.TokenManger.TokenManager.getAccessToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class JWTDecoder {

    // Phương thức để giải mã phần payload của JWT và trích xuất thông tin
    public static JSONObject decodeJWT(String jwtToken) {
        String[] parts = jwtToken.split("\\.");
        try {
            if (parts.length == 3) {
                String payload = parts[1];
                byte[] decodedBytes = Base64.decode(payload, Base64.URL_SAFE);
                String decodedPayload = new String(decodedBytes, StandardCharsets.UTF_8);

                return new JSONObject(decodedPayload);
            } else {
                Log.e("JWTDecoder", "Invalid JWT format");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            Log.e("JWTDecoder", "Invalid Base64 URL format");
            e.printStackTrace();
        }
        return null;
    }



    public static void removeAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("access_token");
        editor.apply();
    }

    public static void main(String[] args) {
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuaGFuIiwiZW1haWwiOiJ0cmFuaHV1bmhhbjA5OEBnbWFpbC5jb20iLCJ1c2VySWQiOjIxLCJSb2xlTmFtZSI6IkFERU5JTiIsImlhdCI6MTcxOTMzNTA4NCwiZXhwIjoxNzE5NDIxNDg0fQ.DxA69DW1ikAkVPJigBFrXNqRlx-eJJJZECSLWQRtVvA";

        JSONObject decodedPayload = decodeJWT(jwtToken);

        if (decodedPayload != null) {
            try {
                // Trích xuất các giá trị từ JSON
                String sub = decodedPayload.getString("sub");
                String email = decodedPayload.getString("email");
                int userId = decodedPayload.getInt("userId");
                String roleName = decodedPayload.getString("RoleName");
                long iat = decodedPayload.getLong("iat");
                long exp = decodedPayload.getLong("exp");

                // In ra các giá trị đã trích xuất
                System.out.println("sub: " + sub);
                System.out.println("email: " + email);
                System.out.println("userId: " + userId);
                System.out.println("RoleName: " + roleName);
                System.out.println("iat: " + iat);
                System.out.println("exp: " + exp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
