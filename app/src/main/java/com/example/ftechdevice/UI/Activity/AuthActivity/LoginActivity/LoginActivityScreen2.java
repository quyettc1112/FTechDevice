package com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ftechdevice.API_Repository.UserAPI_Repository;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.AppConfig.CustomView.CustomDialog.ErrorDialog;
import com.example.ftechdevice.AppConfig.CustomView.CustomToolBar.CustomToolbar;
import com.example.ftechdevice.Model.ModelRequestDTO.JWTObject;
import com.example.ftechdevice.Model.ModelRequestDTO.UserCretidentialDTO;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.UI.ShareViewModel.UserShareViewModel;
import com.example.ftechdevice.databinding.ActivityLoginScreen2Binding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class LoginActivityScreen2 extends BaseActivity {

    private ActivityLoginScreen2Binding binding;

    private UserShareViewModel userShareViewModel;

    @Inject
    UserAPI_Repository userapiRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreen2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        userShareViewModel = new ViewModelProvider(this).get(UserShareViewModel.class);

        backToLogin();
        getTextInputLogin();
        observeViewModel();
    }

    private void backToLogin() {
        binding.customToolbarLogin2.setOnStartIconClickListener(new CustomToolbar.OnStartIconClickListener() {
            @Override
            public void onStartIconClick() {
                goBackActivity(LoginActivityScreen2.this, LoginActivity.class);
            }
        });
    }

    private void getTextInputLogin() {
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString();
            String password = binding.edtPassword.getText().toString();
            if (checkValidNumber(email, password)) {
                userShareViewModel.updateUserCretidential(new UserCretidentialDTO(email, password));
                if (userShareViewModel.getUserCredentials().getValue() != null) {
                    callUserCretidential(userShareViewModel.getUserCretidentail());
                }
            }
        });
    }

    private boolean checkValidNumber(String email, String password) {
        boolean isValid = true;

        if (email.isEmpty()) {
            binding.edtEmail.setError("Email không được để trống");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.setError("Email không hợp lệ");
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.edtPassword.setError("Mật khẩu không được để trống");
            isValid = false;
        } else if (password.length() < 6) {
            binding.edtPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            isValid = false;
        }

        return isValid;
    }

    private void observeViewModel() {
        userShareViewModel.getUserCredentials().observe(this, new Observer<UserCretidentialDTO>() {
            @Override
            public void onChanged(UserCretidentialDTO userCretidential) {
                // Do something with the updated user credentials
            }
        });
    }

    private void callUserCretidential(UserCretidentialDTO userCretidentialDTO) {
        userapiRepository.getUserCretidential(userCretidentialDTO)
                .enqueue(new Callback<JWTObject>() {
                    @Override
                    public void onResponse(Call<JWTObject> call, Response<JWTObject> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            JWTObject jwtObject = response.body();
                            userShareViewModel.updateJWTToken(jwtObject);
                            startActivity(new Intent(LoginActivityScreen2.this, MainActivity.class));
                            finish();
                        } else {
                            handleErrorResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<JWTObject> call, Throwable t) {
                        Log.d("CheckResponseValue", t.getMessage());
                    }
                });
    }

    private void handleErrorResponse(Response<JWTObject> response) {
        if (response.code() == 404) {
            ErrorDialog errorDialog = new ErrorDialog(
                    this,
                    "Không tìm thấy tài khoản của bạn",
                    "Quay Lại"
            );
            errorDialog.show();
        } else if (response.code() == 400) {
            ErrorDialog errorDialog = new ErrorDialog(
                    this,
                    "Sai Mật Khẩu",
                    "Quay Lại"
            );
            errorDialog.show();
        }
        Log.d("CheckResponseValue", String.valueOf(response.code()));
    }
}