package com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity;

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
import com.example.ftechdevice.Model.ModelRequestDTO.RegisterRequestDTO;
import com.example.ftechdevice.Model.ModelRespone.RegisterResponseDTO;
import com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivityScreen2;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.UI.ShareViewModel.RegisterViewModel;
import com.example.ftechdevice.Until.MemoryData;
import com.example.ftechdevice.Until.MyProgressDialog;
import com.example.ftechdevice.databinding.ActivityRegisterScreen3Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.ftechdevice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@AndroidEntryPoint
public class RegisterActivity_Screen3 extends BaseActivity {

    private ActivityRegisterScreen3Binding binding;
    private RegisterViewModel registerViewModel;
    FirebaseAuth mAuth;
    @Inject
    UserAPI_Repository userapiRepository;

    private DatabaseReference databaseReference; // Reference to the Firebase database
    private MyProgressDialog progressDialog;

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

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(getString(R.string.database_url));
        progressDialog = new MyProgressDialog(this);
        progressDialog.setCancelable(false);

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
        progressDialog.show();
        binding.btnRegisterScreen3.setEnabled(false);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendVerificationEmail();
                        registerUser(registerViewModel.getRegisterDTO());
                        registerOnFireBase(registerViewModel.getRegisterDTO());
                        //Intent intent = new Intent(this, LoginActivityScreen2.class);
                        //startActivity(intent);
                    } else {
                        binding.btnRegisterScreen3.setEnabled(true);
                        Toast.makeText(RegisterActivity_Screen3.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    binding.btnRegisterScreen3.setEnabled(true);
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(RegisterActivity_Screen3.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity_Screen3.this, "Lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(authResult -> {
                    // Check if email is verified
                    if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()) {
                        registerUser(registerViewModel.getRegisterDTO());
                    } else {
                        // If email is not verified, show a message
                        Toast.makeText(RegisterActivity_Screen3.this, "Vui lòng xác minh email trước khi đăng nhập", Toast.LENGTH_SHORT).show();
                        mAuth.signOut(); // Sign out the user as email is not verified
                    }
                });
    }

    private void registerOnFireBase(RegisterRequestDTO registerRequestDTO) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss(); // Hide the progress dialog

                if (snapshot.child("users").hasChild(registerRequestDTO.getPhone())) {
                    // Display a message if mobile number already exists
                    Toast.makeText(RegisterActivity_Screen3.this, "Mobile already exists", Toast.LENGTH_SHORT).show();
                } else {

                    // If mobile number doesn't exist, proceed with registration
                    Toast.makeText(RegisterActivity_Screen3.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                    // Save user details in the database
                    databaseReference.child("users").child(registerRequestDTO.getPhone()).child("email").setValue(registerRequestDTO.getEmail());
                    databaseReference.child("users").child(registerRequestDTO.getPhone()).child("name").setValue(registerRequestDTO.getUsername());
                    databaseReference.child("users").child(registerRequestDTO.getPhone()).child("password").setValue(registerRequestDTO.getPassword());
                    databaseReference.child("users").child(registerRequestDTO.getPhone()).child("roleid").setValue(registerRequestDTO.getRoleId());

                    // Save user's mobile number for future login
                    MemoryData.saveMobile(registerRequestDTO.getPhone(), RegisterActivity_Screen3.this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                // Display a message for database error
                Toast.makeText(RegisterActivity_Screen3.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendVerificationEmail() {
        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        binding.btnRegisterScreen3.setEnabled(true);
                        registerUser(registerViewModel.getRegisterDTO());
                        Toast.makeText(RegisterActivity_Screen3.this, "Email xác minh đã được gửi", Toast.LENGTH_SHORT).show();
                    } else {
                        binding.btnRegisterScreen3.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Gửi email xác minh thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void registerUser(RegisterRequestDTO registerRequestDTO) {
        userapiRepository.registerUser(registerRequestDTO)
                .enqueue(new Callback<RegisterResponseDTO>() {
                    @Override
                    public void onResponse(Call<RegisterResponseDTO> call, Response<RegisterResponseDTO> response) {
                        Log.d("test", response.code() + "" + response.message() + response.errorBody());
                        if (response.isSuccessful()) {
                            startActivity(new Intent(RegisterActivity_Screen3.this, MainActivity.class));
                            finish();
                        } else {
                            Log.d("Checklaue", response.code() + "" + response.message() + response.errorBody());
                            Toast.makeText(RegisterActivity_Screen3.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponseDTO> call, Throwable t) {
                        Log.d("Checklaue", t.toString());
                        Toast.makeText(RegisterActivity_Screen3.this, "Lỗi kết nối, vui lòng thử lại", Toast.LENGTH_SHORT).show();
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