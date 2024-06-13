package com.example.ftechdevice.AppConfig.CustomView.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.ftechdevice.R;

public class ErrorDialog extends Dialog {

    private final String errorContent;
    private final String textButton;

    public ErrorDialog(Context context, String errorContent, String textButton) {
        super(context);
        this.errorContent = errorContent;
        this.textButton = textButton;
    }

    public ErrorDialog(Context context, String errorContent) {
        this(context, errorContent, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_error, null, false);

        TextView tvContentError = rootView.findViewById(R.id.tvContentError);
        tvContentError.setText(errorContent);

        Button btnOK = rootView.findViewById(R.id.btnOK);
        if (textButton != null) {
            btnOK.setText(textButton);
        }
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setContentView(rootView);
    }
}
