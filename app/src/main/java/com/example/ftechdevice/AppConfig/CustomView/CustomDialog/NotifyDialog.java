package com.example.ftechdevice.AppConfig.CustomView.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.text.HtmlCompat;

import com.example.ftechdevice.R;

public class NotifyDialog extends Dialog {

    private final String title;
    private final String message;
    private final String textButton;

    public NotifyDialog(Context context, String title, String message, String textButton) {
        super(context);
        this.title = title;
        this.message = message;
        this.textButton = textButton;
    }

    public NotifyDialog(Context context, String title, String message) {
        this(context, title, message, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notify);

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        TextView tvContent = findViewById(R.id.tvContent);
        tvContent.setText(HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY));

        AppCompatButton btnOK = findViewById(R.id.btnOK);
        if (textButton != null) {
            btnOK.setText(textButton);
        }
        btnOK.setOnClickListener(v -> dismiss());
    }
}
