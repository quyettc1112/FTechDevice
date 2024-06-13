package com.example.ftechdevice.AppConfig.BaseConfig;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ftechdevice.AppConfig.CustomView.CustomDialog.ConfirmDialog;
import com.example.ftechdevice.AppConfig.CustomView.CustomDialog.ErrorDialog;
import com.example.ftechdevice.AppConfig.CustomView.CustomDialog.NotifyDialog;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoading(boolean isShow) {
        // Add your loading logic here
    }

    public void goBackActivity(Context context, Class<? extends Activity> nextActivity) {
        Intent intent = new Intent(context, nextActivity);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public void showLoading(String title, String message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);

        if (cancelable) {
            progressDialog.setOnCancelListener(cancelListener);
        }

        progressDialog.show();
    }

    public void showLoading(String title, String message, boolean cancelable) {
        showLoading(title, message, cancelable, null);
    }

    public void showLoading(String title, String message) {
        showLoading(title, message, true, null);
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showErrorDialog(String message) {
        ErrorDialog errorDialog = new ErrorDialog(this, message);
        errorDialog.show();
        if (errorDialog.getWindow() != null) {
            errorDialog.getWindow().setGravity(Gravity.CENTER);
            errorDialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }

    public void showNotifyDialog(int titleResourceId, int messageResourceId, int textButtonResourceId) {
        String title = getString(titleResourceId);
        String message = getString(messageResourceId);
        String textButton = (textButtonResourceId == -1) ? null : getString(textButtonResourceId);
        showNotifyDialog(message, title, textButton);
    }

    public void showNotifyDialog(int titleResourceId, int messageResourceId) {
        showNotifyDialog(titleResourceId, messageResourceId, -1);
    }

    public void showNotifyDialog(String message, String title, @Nullable String textButton) {
        NotifyDialog notifyDialog = new NotifyDialog(this, title, message, textButton);
        notifyDialog.show();
        if (notifyDialog.getWindow() != null) {
            notifyDialog.getWindow().setGravity(Gravity.CENTER);
            notifyDialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }

    public void showConfirmDialog(int titleResourceId, int messageResourceId, int positiveTitleResourceId, int negativeTitleResourceId, int textButtonResourceId, ConfirmDialog.ConfirmCallback callback) {
        String title = getString(titleResourceId);
        String message = (messageResourceId != -1) ? getString(messageResourceId) : null;
        String positiveButtonTitle = getString(positiveTitleResourceId);
        String negativeButtonTitle = getString(negativeTitleResourceId);
        String textButton = (textButtonResourceId == -1) ? null : getString(textButtonResourceId);
        showConfirmDialog(title, message, positiveButtonTitle, negativeButtonTitle, textButton, callback);
    }

    public void showConfirmDialog(int titleResourceId, int messageResourceId, int positiveTitleResourceId, int negativeTitleResourceId, ConfirmDialog.ConfirmCallback callback) {
        showConfirmDialog(titleResourceId, messageResourceId, positiveTitleResourceId, negativeTitleResourceId, -1, callback);
    }

    public void showConfirmDialog(String title, @Nullable String message, String positiveButtonTitle, String negativeButtonTitle, @Nullable String textButton, ConfirmDialog.ConfirmCallback callback) {
        ConfirmDialog confirmDialog = new ConfirmDialog(this, callback, title, message, positiveButtonTitle, negativeButtonTitle);
        confirmDialog.show();
        if (confirmDialog.getWindow() != null) {
            confirmDialog.getWindow().setGravity(Gravity.CENTER);
            confirmDialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }
}
