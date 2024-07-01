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
import androidx.lifecycle.ViewModelProvider;

import com.example.ftechdevice.API_Repository.UserAPI_Repository;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.Common.ManagerUser.ManagerUser;
import com.example.ftechdevice.Model.ModelRequestDTO.RegisterRequestDTO;
import com.example.ftechdevice.Model.ModelRespone.RegisterResponseDTO;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivityScreen2;
import com.example.ftechdevice.UI.ShareViewModel.RegisterViewModel;
import com.example.ftechdevice.databinding.ActivityRegisterScreen3Binding;
import com.google.android.gms.tasks.OnFailureListener;
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
                        Log.d(TAG, "Đăng ký thành công");
                        sendVerificationEmail();
                    } else {
                        binding.btnRegisterScreen3.setEnabled(true);
                        //      Log.d(TAG, "Đăng ký thất bại");
                    }
                })
                .addOnFailureListener(e -> {
                    binding.btnRegisterScreen3.setEnabled(true);
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "Lỗi xảy ra, vui lòng thử lại");
                    }
                });
    }

    private void sendVerificationEmail() {
        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Toast.makeText(this, "Email xác minh đã được gửi. Vui lòng xác minh email trước khi đăng nhập.", Toast.LENGTH_SHORT).show();

                    // Đăng xuất người dùng hiện tại
                    mAuth.signOut();

                    // Lưu thông tin người dùng trước khi đăng xuất
                    RegisterRequestDTO registerDTO = registerViewModel.getRegisterDTO();
                    if (registerDTO != null && registerDTO.getEmail() != null) {
                        ManagerUser.saveUserInfo(
                                this,  // Truyền context hiện tại
                                registerDTO.getEmail(),
                                registerDTO.getPassword(),
                                binding.edtRegisterPhone.getText().toString(),
                                registerDTO.getUsername(),
                                registerDTO.getRoleId()
                        );
                    } else {
                        Log.e(TAG, "RegisterDTO hoặc trường email bị null");
                    }

                    Intent intent = new Intent(this, LoginActivityScreen2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    binding.btnRegisterScreen3.setEnabled(true);
                    //  Toast.makeText(this, "Gửi email xác minh thất bại.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




}
