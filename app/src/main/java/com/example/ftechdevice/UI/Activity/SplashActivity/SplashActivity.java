package com.example.ftechdevice.UI.Activity.SplashActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.L;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.ChatModule.ChatActivity.ChatActivity;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.UI.Activity.StartActivity.StartActivity;
import com.example.ftechdevice.databinding.ActivitySplashBinding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSplashAnimation();

        if (getIntent().getExtras() != null) {
            // Lấy dữ liệu từ Intent
            String message = getIntent().getExtras().getString("message");
            // Bạn có thể xử lý dữ liệu ở đây hoặc chuyển sang ChatActivity
            if (message != null) {
                Intent chatIntent = new Intent(this, ChatActivity.class);
                chatIntent.putExtra("message", message);
                startActivity(chatIntent);
            }
        }

        IntentActivity();


    }

    private void loadUserData() {
        // Implement your method to load user data
    }

    private void IntentActivity() {
        long delayMillis = 1300; // 1.25 second
        boolean isFirstStart = TokenManager.isFirstStart(this);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isFirstStart) {
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                TokenManager.saveFirstStart(this);
                Log.d("CheckIsfirsterTAasdasda", String.valueOf(TokenManager.isFirstStart(this)));
                finish();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, delayMillis);
    }

    private void setSplashAnimation() {
        Animation topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        binding.imageView.startAnimation(topAnimation);
        // Uncomment if you have a logoName view
        // binding.logoName.startAnimation(bottomAnimation);
    }
}