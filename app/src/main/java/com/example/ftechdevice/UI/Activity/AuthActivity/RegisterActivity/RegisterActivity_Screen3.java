package com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;


import com.example.ftechdevice.API_Repository.UserAPI_Repository;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.Model.ModelRequestDTO.LoginRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.RegisterRequestDTO;
import com.example.ftechdevice.Model.ModelRespone.LoginResponse;
import com.example.ftechdevice.Model.ModelRespone.RegisterResponseDTO;
import com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivityScreen2;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.UI.ShareViewModel.RegisterViewModel;
import com.example.ftechdevice.databinding.ActivityRegisterScreen3Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.ftechdevice.R;

@AndroidEntryPoint
public class RegisterActivity_Screen3 extends BaseActivity {

    private ActivityRegisterScreen3Binding binding;
    private RegisterViewModel registerViewModel;
    FirebaseAuth mAuth;
    @Inject
    UserAPI_Repository userapiRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = ActivityRegisterScreen3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputValue();
    }

    private void inputValue() {
        binding.btnRegisterScreen3.setOnClickListener(v -> {
            if (checkFieldsNotNullOrEmpty() && checkPhoneNumber(binding.edtRegisterPhone.getText().toString())) {
                String email = getIntent().getStringExtra("intent_email");
                String password = getIntent().getStringExtra("intent_password");

                registerViewModel.updateEmail(email);
                registerViewModel.updatePassword(password);
                registerViewModel.updateRoleId(1);
                registerViewModel.updatePhone(binding.edtRegisterPhone.getText().toString());
                registerViewModel.updateUsername(binding.edtRegisterName.getText().toString());

//                if (binding.edtRegisterGender.getText().toString().equals("Nam")) {
//                    registerViewModel.updateGender(true);
//                } else {
//                    registerViewModel.updateGender(false);
//                }

                doRegister(email, password);


            }
        });

        binding.customToolbarScreen3.setOnStartIconClickListener(() -> {
            Intent intent = new Intent(this, RegisterActivity_Screen2.class);
            startActivity(intent);
            finish();
        });

        binding.edtRegisterDob.setOnClickListener(v -> showDatePickerDialog());

        binding.edtRegisterGender.setOnClickListener(v -> showGenderSelectionDialog());
    }

    private boolean checkFieldsNotNullOrEmpty() {
        String name = binding.edtRegisterName.getText().toString();
        String gender = binding.edtRegisterGender.getText().toString();
        String dob = binding.edtRegisterDob.getText().toString();
        String phone = binding.edtRegisterPhone.getText().toString();

        if (name.isEmpty()) {
            binding.edtRegisterName.setError("Tên không được để trống");
            return false;
        }
        if (gender.isEmpty()) {
            binding.edtRegisterGender.setError("Giới tính không được để trống");
            return false;
        }
        if (dob.isEmpty()) {
            binding.edtRegisterDob.setError("Ngày sinh không được để trống");
            return false;
        }
        if (phone.isEmpty()) {
            binding.edtRegisterPhone.setError("Số điện thoại không được để trống");
            return false;
        }
        return true;
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the date and set it to the EditText
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    binding.edtRegisterDob.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void showGenderSelectionDialog() {
        // Gender options
        String[] genderOptions = {"Nam", "Nữ", "Khác"};

        // Create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn Giới Tính");
        builder.setItems(genderOptions, (dialog, which) -> {
            // Set the selected value to EditText
            binding.edtRegisterGender.setText(genderOptions[which]);
        });

        // Show dialog
        builder.show();
    }

    private boolean checkPhoneNumber(String phone) {
        if (phone.length() == 10) {
            return true;
        } else {
            binding.edtRegisterPhone.setError("Nhập Số Điện Thoại Hợp Lệ");
            return false;
        }
    }

    private void doRegister(String email, String password) {
        binding.btnRegisterScreen3.setEnabled(false);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Đăng ký thanh công");
                        sendVerificationEmail();
                    } else {
                        binding.btnRegisterScreen3.setEnabled(true);
                        Log.d(TAG, "Đăng ký thất bại");
                    }
                })
                .addOnFailureListener(e -> {
                    binding.btnRegisterScreen3.setEnabled(true);
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        Log.d(TAG, "Email đã tồn tại");
                    } else {
                        Log.d(TAG, "Lỗi xảy ra, vui lòng thử lại");
                    }
                });
    }

    private void sendVerificationEmail() {
        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Email xác minh đã được gửi. Vui lòng xác minh email trước khi đăng nhập.");

                    // Đăng xuất người dùng hiện tại
                    mAuth.signOut();

                    // Chuyển người dùng đến màn hình đăng nhập
                    Intent intent = new Intent(this, LoginActivityScreen2.class);
                    Bundle myBundle =new Bundle();
                    myBundle.putString("GetEmail",registerViewModel.getRegisterDTO().getEmail());
                    myBundle.putString("GetPassword",registerViewModel.getRegisterDTO().getPassword());
                    myBundle.putString("GetPhone",registerViewModel.getRegisterDTO().getPhone());
                    myBundle.putString("GetUsername",registerViewModel.getRegisterDTO().getUsername());
                    myBundle.putInt("GetRole",registerViewModel.getRegisterDTO().getRoleId());
                    intent.putExtra("Res",myBundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    checkEmailVerificationAndRegister();
                } else {
                    binding.btnRegisterScreen3.setEnabled(true);
                    Log.d(TAG, "Gửi email xác minh thất bại");
                }
            });
        }
    }

    private void checkEmailVerificationAndRegister() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.reload().addOnCompleteListener(task -> {
                if (user.isEmailVerified()) {
                    // Email đã được xác minh, tiến hành đăng ký với server
                    if (registerViewModel != null) {
                        registerUser(registerViewModel.getRegisterDTO());
                    } else {
                        Log.d(TAG, "registerViewModel is null");
                    }
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
                            Toast.makeText(RegisterActivity_Screen3.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            // Tiếp tục với quá trình đăng nhập hoặc hành động khác
                        } else {
                            Log.d("RegisterUser", "Đăng ký thất bại với server: " + response.code() + " " + response.message());
                            Toast.makeText(RegisterActivity_Screen3.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponseDTO> call, Throwable t) {
                        Log.d("RegisterUser", "Lỗi kết nối: " + t.getMessage());
                        Toast.makeText(RegisterActivity_Screen3.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*private void callRegister() {
        userapiRepository.callRegister(registerViewModel.getRegisterDTO()).enqueue(new Callback<MessageRespone>() {
            @Override
            public void onResponse(Call<MessageRespone> call, Response<MessageRespone> response) {
                if (response.isSuccessful()) {
                    MessageRespone message = response.body();
                    Log.d("checkRespone", message.getMessage());
                    startActivity(new Intent(RegisterActivity_Screen3.this, MainActivity.class));
                    finish();
                } else {
                    Log.d("checkRespone", String.valueOf(response.code()));
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (errorBody != null) {
                        try {
                            Gson gson = new GsonBuilder()
                                    .registerTypeAdapter(List.class, new MessageAdapter())
                                    .create();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                            Log.d("checkRespone", errorResponse.getMessage().toString());
                            ErrorDialog errorDialog = new ErrorDialog(
                                    RegisterActivity_Screen3.this,
                                    "Message: " + String.join("\n", errorResponse.getMessage()) + "\n",
                                    "Quay Lại"
                            );
                            errorDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("checkRespone", "Lỗi khi chuyển đổi errorBody");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageRespone> call, Throwable t) {
                Log.d("checkRespone", t.getMessage());
            }
        });
    }*/
}