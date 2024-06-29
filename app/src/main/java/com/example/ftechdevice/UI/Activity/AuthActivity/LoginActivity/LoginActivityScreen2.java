package com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.Model.ModelRequestDTO.JWTObject;
import com.example.ftechdevice.Model.ModelRequestDTO.LoginRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.RegisterRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.UserCretidentialDTO;
import com.example.ftechdevice.Model.ModelRespone.LoginResponse;
import com.example.ftechdevice.Model.ModelRespone.RegisterResponseDTO;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.UI.ShareViewModel.RegisterViewModel;
import com.example.ftechdevice.UI.ShareViewModel.UserShareViewModel;
import com.example.ftechdevice.databinding.ActivityLoginScreen2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class LoginActivityScreen2 extends BaseActivity {

    private ActivityLoginScreen2Binding binding;

    private UserShareViewModel userShareViewModel;

    private RegisterViewModel registerViewModel;
    FirebaseAuth mAuth;
    private boolean isEmailVerificationInProgress = false;
    @Inject
    UserAPI_Repository userapiRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = ActivityLoginScreen2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        userShareViewModel = new ViewModelProvider(this).get(UserShareViewModel.class);
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getBundleExtra("Res");
        registerViewModel.updateEmail(myBundle.getString("GetEmail"));
        registerViewModel.updatePassword(myBundle.getString("GetPassword"));
        registerViewModel.updatePhone(myBundle.getString("GetPhone"));
        registerViewModel.updateRoleId(1);

        RegisterRequestDTO registerRequestDTO = registerViewModel.getRegisterDTO();
        Log.d("AA",registerRequestDTO.getEmail().toString());
        if(TokenManager.getAccessToken(LoginActivityScreen2.this) !=null){
            Log.d("LL",TokenManager.getAccessToken(LoginActivityScreen2.this));
            startActivity(new Intent(LoginActivityScreen2.this, MainActivity.class));
        }
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
                userShareViewModel.updateLoginCretidential(new LoginRequestDTO(email, password));

                if (userShareViewModel.getloginCredentials().getValue() != null) {
                    doLogin(userShareViewModel.getloginCretidentail());
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

    ///// Nhan
    private void loginUser(LoginRequestDTO loginRequestDTO) {

        userapiRepository.loginUser(loginRequestDTO)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                        if (response.isSuccessful()) {
                            Log.d("CheckBBBB", response.body().getAccessToken().toString());
                            userShareViewModel.updateLoginCretidential(loginRequestDTO);
                            TokenManager.saveAccessToken(LoginActivityScreen2.this,response.body().getAccessToken());

                            startActivity(new Intent(LoginActivityScreen2.this, MainActivity.class));
                            finish();

                        } else  Log.d("Checklaue", response.code() +"" + response.message() + response.errorBody());
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Checklaue",t.toString());
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

    private void isEmailVerified() {
        if(mAuth.getCurrentUser()!=null){
            boolean isEmailVerified =mAuth.getCurrentUser().isEmailVerified();
            if(isEmailVerified){
                Toast.makeText(this,"Email is verifi", Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(this,"please verify your email address first", Toast.LENGTH_SHORT).show();
                sendVerificationEmail();
            }
        }
    }
    private void doLogin(LoginRequestDTO loginRequestDTO) {
        // Reset the flag each time login is attempted
        isEmailVerificationInProgress = false;

        mAuth.signInWithEmailAndPassword(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("CheckResponseValue", "signInWithEmail:success");
                            // Reload the user to get the latest email verification status
                            mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> reloadTask) {
                                    if (mAuth.getCurrentUser().isEmailVerified()) {
                                        loginUser(loginRequestDTO);  // Proceed with the backend login

                                        if (registerViewModel.getRegisterDTO() != null) {
                                            registerUser(registerViewModel.getRegisterDTO());
                                        }else{
                                            Log.d("LL",registerViewModel.getRegisterDTO().getEmail());
                                        }
                                    } else {
                                        Toast.makeText(LoginActivityScreen2.this, "Vui lòng xác minh email trước khi đăng nhập", Toast.LENGTH_SHORT).show();
                                        sendVerificationEmail();
                                        mAuth.signOut(); // Sign out the user since their email is not verified
                                    }
                                }
                            });
                        } else {
                            handleLoginFailure(task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        handleLoginFailure(e);
                    }
                });
    }

    private void handleLoginFailure(Exception e) {
        if (e instanceof FirebaseAuthInvalidCredentialsException) { // Means password is wrong
            binding.btnLogin.setEnabled(true);
            binding.edtPassword.setError("Invalid Password");
            binding.edtPassword.requestFocus();
        } else if (e instanceof FirebaseAuthInvalidUserException) { // Means email is not registered with us
            binding.btnLogin.setEnabled(true);
            binding.edtPassword.setError("Email Not Registered");
            binding.edtPassword.requestFocus();
        } else {
            Toast.makeText(LoginActivityScreen2.this, "Oops! Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendVerificationEmail() {
        if(mAuth.getCurrentUser() != null){
            isEmailVerificationInProgress = true;
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    isEmailVerificationInProgress = false;  // Reset the flag here
                    if(task.isSuccessful()) {
                        Toast.makeText(LoginActivityScreen2.this, "Email xác minh đã được gửi đến địa chỉ email của bạn", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Không thể gửi email xác minh", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // Thêm hàm này vào LoginActivityScreen2
    private void checkEmailVerificationAndRegister() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.reload().addOnCompleteListener(task -> {
                if (user.isEmailVerified()) {
                    // Email đã được xác minh, tiến hành đăng ký với server
                    registerUser(registerViewModel.getRegisterDTO());
                } else {
                    Toast.makeText(this, "Vui lòng xác minh email trước khi đăng nhập", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void registerUser(RegisterRequestDTO registerRequestDTO) {
        userapiRepository.registerUser(registerRequestDTO)
                .enqueue(new Callback<RegisterResponseDTO>() {
                    @Override
                    public void onResponse(Call<RegisterResponseDTO> call, Response<RegisterResponseDTO> response) {
                        if (response.isSuccessful()) {
                            Log.d("RegisterUser", "Đăng ký thành công với server");
                            Toast.makeText(LoginActivityScreen2.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            // Tiếp tục với quá trình đăng nhập
                        } else {
                            //Log.d("RegisterUser", "Đăng ký thất bại với server: " + response.code() + " " + response.message());
                            Toast.makeText(LoginActivityScreen2.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponseDTO> call, Throwable t) {
                        Log.d("RegisterUser", "Lỗi kết nối: " + t.getMessage());
                        Toast.makeText(LoginActivityScreen2.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}