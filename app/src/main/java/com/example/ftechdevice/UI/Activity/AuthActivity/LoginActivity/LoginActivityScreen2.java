package com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ftechdevice.API_Repository.UserAPI_Repository;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.AppConfig.CustomView.CustomDialog.ErrorDialog;
import com.example.ftechdevice.AppConfig.CustomView.CustomToolBar.CustomToolbar;
import com.example.ftechdevice.Common.ManagerUser.ManagerUser;
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
import com.example.ftechdevice.Until.MemoryData;
import com.example.ftechdevice.Until.MyProgressDialog;
import com.example.ftechdevice.databinding.ActivityLoginScreen2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class LoginActivityScreen2 extends BaseActivity {

    private static final String TAG = "LoginActivityScreen2";
    private static final int MIN_PASSWORD_LENGTH = 6;

    private ActivityLoginScreen2Binding binding;
    private UserShareViewModel userShareViewModel;
    private RegisterViewModel registerViewModel;
    private FirebaseAuth mAuth;
    private boolean isEmailVerificationInProgress = false;
    private ManagerUser managerUser;
    @Inject
    UserAPI_Repository userapiRepository;

    private DatabaseReference databaseReference;
    private MyProgressDialog progressDialog;
    private String FCMToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = ActivityLoginScreen2Binding.inflate(getLayoutInflater());
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(getString(R.string.database_url));
        setContentView(binding.getRoot());
        progressDialog = new MyProgressDialog(this);

        getFCMToken();
        initViewModels();
        updateRegisterViewModel();
        checkExistingToken();
        setupUI();
        observeViewModel();

    }
    private void attemptAutoLogin() {
        String email = ManagerUser.getEmail(this);
        String password = ManagerUser.getPassword(this);

        if (!email.isEmpty() && !password.isEmpty()) {
            binding.edtEmail.setText(email);
            binding.edtPassword.setText(password);

            LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);
            doLogin(loginRequestDTO);
        }else {
            return;
        }
    }

    private void initViewModels() {
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        userShareViewModel = new ViewModelProvider(this).get(UserShareViewModel.class);
    }



    private void updateRegisterViewModel() {
        Context context = this;  // Get the current context

        String email = ManagerUser.getEmail(context);
        String username = ManagerUser.getUsername(context);
        String password = ManagerUser.getPassword(context);
        String phone = ManagerUser.getPhone(context);

        if (email != null && !email.isEmpty()) {
            registerViewModel.updateEmail(email);
        } else {
            Log.e(TAG, "Email is null or empty");
        }

        if (username != null && !username.isEmpty()) {
            registerViewModel.updateUsername(username);
        } else {
            Log.e(TAG, "Username is null or empty");
        }

        if (password != null && !password.isEmpty()) {
            registerViewModel.updatePassword(password);
        } else {
            Log.e(TAG, "Password is null or empty");
        }

        registerViewModel.updateRoleId(1);

        if (phone != null && !phone.isEmpty()) {
            registerViewModel.updatePhone(phone);
        } else {
            Log.e(TAG, "Phone is null or empty");
        }

        RegisterRequestDTO registerRequestDTO = registerViewModel.getRegisterDTO();
        if (registerRequestDTO != null && registerRequestDTO.getEmail() != null) {
            Log.d(TAG, "Email from RegisterDTO: " + registerRequestDTO.getEmail());
        } else {
            Log.e(TAG, "RegisterRequestDTO or email is null");
        }
    }

    private void checkExistingToken() {
        if (TokenManager.isTokenValid(this)) {
            Log.d(TAG, "Valid token found: " + TokenManager.getAccessToken(this));
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void setupUI() {
        setupToolbar();
        setupLoginButton();
    }

    private void setupToolbar() {
        binding.customToolbarLogin2.setOnStartIconClickListener(new CustomToolbar.OnStartIconClickListener() {
            @Override
            public void onStartIconClick() {
                goBackActivity(LoginActivityScreen2.this, LoginActivity.class);
            }
        });
    }

    private void setupLoginButton() {
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString();
            String password = binding.edtPassword.getText().toString();
            if (validateInput(email, password)) {
                userShareViewModel.updateLoginCretidential(new LoginRequestDTO(email, password));
                doLogin(userShareViewModel.getloginCretidentail());
            }
        });
    }

    private boolean validateInput(String email, String password) {
        boolean isValid = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.setError("Email không hợp lệ");
            isValid = false;
        }

        if (password.isEmpty() || password.length() < MIN_PASSWORD_LENGTH) {
            binding.edtPassword.setError("Mật khẩu phải có ít nhất " + MIN_PASSWORD_LENGTH + " ký tự");
            isValid = false;
        }

        return isValid;
    }

    private void observeViewModel() {
        userShareViewModel.getUserCredentials().observe(this, new Observer<UserCretidentialDTO>() {
            @Override
            public void onChanged(UserCretidentialDTO userCretidential) {
                // Handle user credential changes if needed
            }
        });
    }

    private void doLogin(LoginRequestDTO loginRequestDTO) {

        mAuth.signInWithEmailAndPassword(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        handleSuccessfulFirebaseLogin(loginRequestDTO);
                    } else {
                        handleLoginFailure(task.getException());
                    }

                });
    }

    private void handleSuccessfulFirebaseLogin(LoginRequestDTO loginRequestDTO) {
        mAuth.getCurrentUser().reload().addOnCompleteListener(reloadTask -> {
            if (mAuth.getCurrentUser().isEmailVerified()) {
                RegisterRequestDTO registerDTO = registerViewModel.getRegisterDTO();

                if (registerDTO != null && registerDTO.getEmail() != null) {
                    // Đầu tiên, đăng ký người dùng
                    registerUser(registerDTO, () -> {
                        // Sau khi đăng ký thành công, tiến hành đăng nhập
                        loginUser(loginRequestDTO);
                    });
                } else {
                    // Nếu không có thông tin đăng ký, chỉ thực hiện đăng nhập
                    loginUser(loginRequestDTO);
                }
            } else {
                Toast.makeText(this, "Vui lòng xác minh email trước khi đăng nhập", Toast.LENGTH_SHORT).show();
                sendVerificationEmail();
                mAuth.signOut();
            }
        });
    }

    private void handleLoginFailure(Exception e) {
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            FirebaseAuthInvalidCredentialsException firebaseAuthException = (FirebaseAuthInvalidCredentialsException) e;
            String errorCode = firebaseAuthException.getErrorCode();
            switch (errorCode) {
                case "ERROR_INVALID_EMAIL":
                    binding.edtEmail.setError("Email không hợp lệ");
                    binding.edtEmail.requestFocus();
                    break;
                case "ERROR_WRONG_PASSWORD":
                    binding.edtPassword.setError("Mật khẩu không đúng");
                    binding.edtPassword.requestFocus();
                    break;
                default:
                    Toast.makeText(LoginActivityScreen2.this,
                            "Thông tin đăng nhập không hợp lệ: " + firebaseAuthException.getMessage(),
                            Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loginUser(LoginRequestDTO loginRequestDTO) {
        userapiRepository.loginUser(loginRequestDTO)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            handleSuccessfulLogin(response.body());
                        } else {
                            handleFailedLogin(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e(TAG, "Login failed: " + t.getMessage());
                        Toast.makeText(LoginActivityScreen2.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleSuccessfulLogin(LoginResponse loginResponse) {
        Log.d(TAG, "Login successful. Access Token: " + loginResponse.getAccessToken());
        TokenManager.saveAccessToken(this, loginResponse.getAccessToken());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void handleFailedLogin(Response<LoginResponse> response) {
        Log.d(TAG, "Login failed. Code: " + response.code() + ", Message: " + response.message());
        // Toast.makeText(this, "Đăng nhập thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
    }

    private void sendVerificationEmail() {
        if (mAuth.getCurrentUser() != null && !isEmailVerificationInProgress) {
            isEmailVerificationInProgress = true;
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> {
                isEmailVerificationInProgress = false;
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Email xác minh đã được gửi", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Không thể gửi email xác minh", task.getException());
                    // Toast.makeText(this, "Không thể gửi email xác minh", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void registerUser(RegisterRequestDTO registerRequestDTO, Runnable onSuccessCallback) {
        userapiRepository.registerUser(registerRequestDTO)
                .enqueue(new Callback<RegisterResponseDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<RegisterResponseDTO> call, @NonNull Response<RegisterResponseDTO> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "Đăng ký thành công với server");
                            //  Toast.makeText(LoginActivityScreen2.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            ManagerUser.clearUserInfo(LoginActivityScreen2.this);

                            // Gọi callback sau khi đăng ký thành công
                            registerFireBaseDataRealTime(registerRequestDTO);

                            onSuccessCallback.run();
                        } else {
                            Log.d(TAG, "Đăng ký thất bại. Code: " + response.code() + ", Message: " + response.message());
                            //   Toast.makeText(LoginActivityScreen2.this, "Đăng ký thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegisterResponseDTO> call, Throwable t) {
                        Log.e(TAG, "Lỗi kết nối: " + t.getMessage());
                        // Toast.makeText(LoginActivityScreen2.this, "Lỗi kết nối khi đăng ký", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void registerFireBaseDataRealTime (RegisterRequestDTO registerRequestDTO) {
        // Show progress dialog

        // Check if the user's mobile number already exists in the database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("users").hasChild(registerRequestDTO.getPhone())) {
                    Toast.makeText(LoginActivityScreen2.this, "Mobile already exists", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("users").child(registerRequestDTO.getPhone()).child("email").setValue(registerRequestDTO.getEmail());
                    databaseReference.child("users").child(registerRequestDTO.getPhone()).child("name").setValue(registerRequestDTO.getUsername());
                    databaseReference.child("users").child(registerRequestDTO.getPhone()).child("password").setValue(registerRequestDTO.getPassword());
                    databaseReference.child("users").child(registerRequestDTO.getPhone()).child("roleid").setValue(registerRequestDTO.getRoleId());
                    databaseReference.child("users").child(registerRequestDTO.getPhone()).child("FCMToken").setValue(FCMToken);
                    // Save user's mobile number for future login
                    MemoryData.saveMobile(registerRequestDTO.getPhone(), LoginActivityScreen2.this);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

                // Display a message for database error
                Toast.makeText(LoginActivityScreen2.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
             FCMToken = task.getResult();
             Log.d("CheckTokenCurrent", FCMToken.toString());
            } else FCMToken = "";
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel any ongoing asynchronous operations if necessary
    }
}