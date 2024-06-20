package com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.ShareViewModel.RegisterViewModel;
import com.example.ftechdevice.databinding.ActivityRegisterScreen2Binding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterActivity_Screen2 extends BaseActivity {

    private RegisterViewModel registerViewModel ;
    private boolean isPasswordMatched = false;
    private ActivityRegisterScreen2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterScreen2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.customToolbarScreen2.setOnStartIconClickListener(() -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnRegisterScreen2.setOnClickListener(v -> {
            if (!isPasswordMatched) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                String email = getIntent().getStringExtra("intent_email");
                Intent intent = new Intent(this, RegisterActivity_Screen3.class);
                intent.putExtra("intent_email", email);
                intent.putExtra("intent_password", binding.edtRegisterPasswordConfirm.getText().toString());
                registerViewModel.updatePassword(binding.edtRegisterPasswordConfirm.getText().toString());
                startActivity(intent);
            }
        });

        binding.edtRegisterPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = binding.edtRegisterPassword.getText().toString();
                String confirmPassword = s.toString();
                checkPasswordsMatch(password, confirmPassword);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void checkPasswordsMatch(String password, String confirmPassword) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\\$%^&+=!])(?=\\S+$).{8,}$";
        boolean matches = password.matches(passwordPattern);

        if (!matches) {
            binding.edtRegisterPassword.setError("Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất 1 ký tự in hoa, 1 số và 1 ký tự đặc biệt");
            isPasswordMatched = false;
            return;
        }

        if (password.equals(confirmPassword)) {
            isPasswordMatched = true;
            binding.edtRegisterPasswordConfirm.setBackgroundResource(R.drawable.background_custome_input);
        } else {
            isPasswordMatched = false;
            binding.edtRegisterPasswordConfirm.setBackgroundResource(R.drawable.background_custome_input_error);
        }
    }
}