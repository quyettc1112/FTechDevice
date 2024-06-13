package com.example.ftechdevice.AppConfig.CustomView.CustomToolBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.example.ftechdevice.R;
import com.example.ftechdevice.databinding.CustomToolBarBinding;

public class CustomToolbar extends ConstraintLayout {

    private CustomToolBarBinding binding;

    private OnStartIconClickListener onStartIconClickListener;
    private OnEndIconClickListener onEndIconClickListener;

    public CustomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar);
        String title = attr.getString(R.styleable.CustomToolbar_android_title);
        int startIcon = attr.getResourceId(R.styleable.CustomToolbar_startIcon, R.drawable.arrow_back_ios_new_24px);
        int endIcon = attr.getResourceId(R.styleable.CustomToolbar_endIcon, R.drawable.more_vert_24px);
        boolean showStartIcon = attr.getBoolean(R.styleable.CustomToolbar_showStartIcon, true);
        boolean showEndIcon = attr.getBoolean(R.styleable.CustomToolbar_showEndIcon, false);
        boolean showTextStart = attr.getBoolean(R.styleable.CustomToolbar_showStartText, true);
        String textStart = attr.getString(R.styleable.CustomToolbar_textStart);
        boolean showShadow = attr.getBoolean(R.styleable.CustomToolbar_showShadow, false);
        attr.recycle();

        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_tool_bar, this, true);

        setTitle(title);
        setStartIconResource(startIcon);
        setEndIconResource(endIcon);
        setTextStart(textStart);
        isShowEndIcon(showEndIcon);
        isShowStartIcon(showStartIcon);
        isShowStartText(showTextStart);
        isShowShadow(showShadow);

        binding.llStartIcon.setOnClickListener(v -> {
            if (onStartIconClickListener != null) {
                onStartIconClickListener.onStartIconClick();
            }
        });

        binding.llEndIcon.setOnClickListener(v -> {
            if (onEndIconClickListener != null) {
                onEndIconClickListener.onEndIconClick();
            }
        });
    }

    public void setTitle(String title) {
        binding.tvTitle.setText(title);
    }

    public void setTextStart(String text) {
        binding.tvStartText.setText(text);
    }

    public void setStartIconResource(int icon) {
        binding.ivStartIcon.setImageResource(icon);
    }

    public void setEndIconResource(int icon) {
        binding.ivEndIcon.setImageResource(icon);
    }

    public void isShowStartIcon(boolean show) {
        binding.ivStartIcon.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void isShowEndIcon(boolean show) {
        binding.ivEndIcon.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void isShowStartText(boolean show) {
        binding.tvStartText.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void isShowShadow(boolean show) {
        binding.toolBarShadow.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setOnStartIconClickListener(OnStartIconClickListener listener) {
        this.onStartIconClickListener = listener;
    }

    public void setOnEndIconClickListener(OnEndIconClickListener listener) {
        this.onEndIconClickListener = listener;
    }

    public interface OnStartIconClickListener {
        void onStartIconClick();
    }

    public interface OnEndIconClickListener {
        void onEndIconClick();
    }
}
