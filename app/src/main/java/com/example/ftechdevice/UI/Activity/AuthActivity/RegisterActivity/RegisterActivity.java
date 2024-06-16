package com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivity;
import com.example.ftechdevice.UI.ShareViewModel.RegisterViewModel;
import com.example.ftechdevice.databinding.ActivityRegisterBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.api.ApiException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;

    // Declare for Google Sign-in Option
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions googleSignInOption;

    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        initializedGoogle();
        backToLogin();
        showEmailInfo();
        nextToRegisterScreen2();
    }

    private void initializedGoogle() {
        googleSignInOption = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption);
    }

    private void backToLogin() {
      //  binding.customToolbar3.setOnStartIconClickListener(v -> goBackActivity(this, LoginActivity.class));
    }

    private void showEmailInfo() {
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, 42141);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42141) {
            if (data != null) { // Kiểm tra xem có dữ liệu trả về không
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                if (task.isSuccessful()) {
                    handleSignInResult(task);
                } else {
                    Toast.makeText(this, "No email selected", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Log.d("Request Code", "Received unexpected request code: " + requestCode);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String email = account.getEmail();
                binding.edtRegisterEmail.setText(email);
            } else {
                // Trường hợp này xảy ra khi không có tài khoản nào được chọn
                Toast.makeText(this, "Login failed or no account selected", Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            // Bắt lỗi khi không có tài khoản nào được chọn hoặc có sự cố khác
            Toast.makeText(this, "Error signing in: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    private void nextToRegisterScreen2() {
        binding.btnRegister.setOnClickListener(v -> {
            String email = binding.edtRegisterEmail.getText().toString();
            if (isValidEmail(email)) {
                Intent intent = new Intent(RegisterActivity.this, RegisterActivity_Screen2.class);
                intent.putExtra("intent_email", email);
                registerViewModel.updateEmail(email);
                startActivity(intent);
            } else {
                binding.edtRegisterEmail.setError("Email không hợp lệ");
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}