package com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.AppConfig.CustomView.CustomToolBar.CustomToolbar;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity.RegisterActivity;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.databinding.ActivityLoginBinding;
import com.example.ftechdevice.databinding.ActivityLoginScreen2Binding;

@AndroidEntryPoint
public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        clickLoginButton();
        setAnimation();
        backButtonClick();
    }

    private void clickLoginButton() {
        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, LoginActivityScreen2.class);
            startActivity(intent);
        });

        binding.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void setAnimation() {
        Animation topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        binding.imageLogin.startAnimation(topAnimation);
        binding.imageContent.startAnimation(bottomAnimation);
    }

    private void backButtonClick() {
        binding.customToolbar.setOnStartIconClickListener(new CustomToolbar.OnStartIconClickListener() {
            @Override
            public void onStartIconClick() {
                goBackActivity(LoginActivity.this, MainActivity.class);
            }
        });
    }
}