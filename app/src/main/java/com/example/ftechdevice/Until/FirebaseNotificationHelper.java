package com.example.ftechdevice.Until;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ftechdevice.R;
import com.google.auth.oauth2.GoogleCredentials;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FirebaseNotificationHelper {

    private static final String FCM_URL = "https://fcm.googleapis.com/v1/projects/ftechdevice/messages:send";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private Context context;

    public FirebaseNotificationHelper(Context context) {
        this.context = context;
    }

    public interface TokenListener {
        void onTokenReceived(String token);
        void onError(Exception e);
    }

    public void getAccessToken(TokenListener listener) {
        new GetAccessTokenTask(context, listener).execute();
    }

    public void sendNotification(String targetToken, String title, String body) {
        getAccessToken(new TokenListener() {
            @Override
            public void onTokenReceived(String token) {
                new SendNotificationTask(token, targetToken, title, body).execute();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static class GetAccessTokenTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private TokenListener listener;

        GetAccessTokenTask(Context context, TokenListener listener) {
            this.context = context;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                InputStream serviceAccountStream = context.getResources().openRawResource(R.raw.service_account);
                GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccountStream)
                        .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
                googleCredentials.refresh();
                return googleCredentials.getAccessToken().getTokenValue();
            } catch (IOException e) {
                listener.onError(e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String token) {
            if (token != null) {
                listener.onTokenReceived(token);
            }
        }
    }

    private static class SendNotificationTask extends AsyncTask<Void, Void, Void> {

        private String accessToken;
        private String targetToken;
        private String title;
        private String body;

        SendNotificationTask(String accessToken, String targetToken, String title, String body) {
            this.accessToken = accessToken;
            this.targetToken = targetToken;
            this.title = title;
            this.body = body;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                OkHttpClient client = new OkHttpClient();
                JSONObject message = buildMessage(targetToken, title, body);
                RequestBody requestBody = RequestBody.create(message.toString(), JSON);
                Request request = new Request.Builder()
                        .url(FCM_URL)
                        .post(requestBody)
                        .addHeader("Authorization", "Bearer " + accessToken)
                        .build();
                Response response = client.newCall(request).execute();
                Log.d("FCM Response", response.body().string());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private JSONObject buildMessage(String targetToken, String title, String body) throws JSONException {
            JSONObject message = new JSONObject();
            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", body);
            message.put("token", targetToken);
            message.put("notification", notification);

            JSONObject messageWrapper = new JSONObject();
            messageWrapper.put("message", message);

            return messageWrapper;
        }
    }
}