package com.example.ftechdevice.UI.Fragment.ProfileFragment;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ftechdevice.API_Service.UserAPI_Service;
import com.example.ftechdevice.Common.IMG.RealPathUtil;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.Model.ModelRespone.FileUploadResponse;
import com.example.ftechdevice.Model.ModelRespone.UserResponseDTO;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivity;
import com.example.ftechdevice.UI.Activity.MapActivity.MapsActivity;
import com.example.ftechdevice.databinding.FragmentProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {


    private static final int SELECT_PICTURE = 200;
    private static final int MY_REQUEST_CODE = 10;

    private FragmentProfileBinding binding;
    private String email = null;
    private Uri selectedImageUri;

    @Inject
    UserAPI_Service userApiService;

    private ImageView dialogImagePreview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        setupViews();
        return binding.getRoot();
    }

    private void setupViews() {
        String accessToken = TokenManager.getAccessToken(requireContext());
        if (accessToken != null) {
            try {
                email = JWTDecoder.decodeJWT(accessToken).getString("email");
                getUserByEmail(accessToken, email);
                binding.LoginButton.setVisibility(View.GONE);
                binding.logout.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Log.d("ProfileFragment", "JWT email: " + email);
        } else {
            binding.logout.setVisibility(View.GONE);
            binding.LoginButton.setVisibility(View.VISIBLE);
        }

        binding.ivUserAvatar.setOnClickListener(v -> showUpdateDialog());
        binding.userName.setOnClickListener(v -> showUpdateDialog());
        binding.logout.setOnClickListener(v -> showLogoutDialog());
        binding.LoginButton.setOnClickListener(v -> showLoginDialog());
        binding.laIntentToFacebook.setOnClickListener(v -> intentToFacebook());
        binding.layoutMaps.setOnClickListener(v -> intentToMaps());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                OpenGallery();
            }
        }
    }

    private void checkPermissionsAndPickImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            OpenGallery();
            return;
        }
        if(checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            OpenGallery();
        } else{
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,MY_REQUEST_CODE);
        }
    }

    private void OpenGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }



    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Handle the Intent
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        selectedImageUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                            dialogImagePreview.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    // doSomeOperations();
                }
            }
    );



    private void getUserByEmail(String token, String email) {
        userApiService.getUserByEmail("Bearer " + token, email)
                .enqueue(new Callback<UserResponseDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<UserResponseDTO> call, @NonNull Response<UserResponseDTO> response) {
                        if (response.isSuccessful()) {
                            UserResponseDTO userResponse = response.body();
                            if (userResponse != null) {
                                String username = userResponse.getUsername();
                                String avatar = userResponse.getAvatar();

                                binding.userName.setText(username != null ? username : "No Name");

                                if (avatar != null) {
                                    try {
                                        Glide.with(requireContext())
                                                .load(avatar)
                                                .apply(new RequestOptions().error(R.drawable.ic_avatar))
                                                .into(binding.ivUserAvatar);
                                    } catch (Exception e) {
                                        Log.e("ProfileFragment", "Failed to set avatar", e);
                                    }
                                }
                            } else {
                                Log.d("ProfileFragment", "userResponse is null");
                            }
                        } else {
                            Log.d("ProfileFragment", "Failed to get user data: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserResponseDTO> call, @NonNull Throwable t) {
                        Log.e("ProfileFragment", "Failed to get user data", t);
                    }
                });
    }

    private void showLogoutDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_bottom_sheet_logout_ui, null);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                convertDpToPx(300)
        );
        view.setLayoutParams(layoutParams);

        // Gắn view vào dialog
        dialog.setContentView(view);

        // Tìm các view con và thiết lập sự kiện
        AppCompatButton buttonYes = view.findViewById(R.id.btn_logout_accept);
        AppCompatButton buttonNo = view.findViewById(R.id.btn_logout_cancle);

        buttonYes.setOnClickListener(v -> {
            TokenManager.clearAccessToken(requireContext());
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            requireActivity().startActivity(intent);
            dialog.dismiss();
            requireActivity().finish();
        });

        buttonNo.setOnClickListener(v -> {
            // Đóng dialog khi nhấn nút No
            dialog.dismiss();
        });

        // Hiển thị dialog
        dialog.show();
    }

    private void showLoginDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_login, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                convertDpToPx(300)
        );
        view.setLayoutParams(layoutParams);

        // Gắn view vào dialog
        dialog.setContentView(view);

        // Tìm các view con và thiết lập sự kiện
        AppCompatButton buttonYes = view.findViewById(R.id.btn_logint_accept);
        AppCompatButton buttonNo = view.findViewById(R.id.btn_login_cancle);

        buttonYes.setOnClickListener(v -> {

            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            requireActivity().startActivity(intent);
            dialog.dismiss();
            requireActivity().finish();
        });

        buttonNo.setOnClickListener(v -> {
            // Đóng dialog khi nhấn nút No
            dialog.dismiss();
        });

        // Hiển thị dialog
        dialog.show();
    }

    private void showUpdateDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_update_user, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                convertDpToPx(800)
        );
        view.setLayoutParams(layoutParams);
        dialog.setContentView(view);

        AppCompatButton buttonUpdate = view.findViewById(R.id.btn_update_accept);
        AppCompatButton buttonCancel = view.findViewById(R.id.btn_update_cancel);
        Button buttonSelectImage = view.findViewById(R.id.btn_select_image);
        dialogImagePreview = view.findViewById(R.id.ivUserAvatars);
        buttonSelectImage.setOnClickListener(v -> checkPermissionsAndPickImage());

        buttonUpdate.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                uploadFile();
            } else {
                Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void uploadFile() {
        String accessToken = TokenManager.getAccessToken(requireContext());
        Log.d("ProfileFragment", "accessToken: " + accessToken);
        if (accessToken == null) {
            Toast.makeText(requireContext(), "You need to be logged in to upload a file", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String RealPath = RealPathUtil.getRealPath(requireContext(), selectedImageUri);
            Log.d("ProfileFragment", "RealPath: " + RealPath);
            File file = new File(RealPath);
            Log.d("ProfileFragment", "file: " + file.getName());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            Log.d("ProfileFragment", "requestFile: " + requestFile);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("files", file.getName(), requestFile);

            userApiService.uploadFile("Bearer " + accessToken, filePart).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String responseBody = response.body().string();
                            Toast.makeText(requireContext(), "File uploaded successfully: " + responseBody, Toast.LENGTH_SHORT).show();
                            Log.d("ProfileFragment", "Response: " + responseBody);
                            getUserByEmail(accessToken, email);
                        } catch (IOException e) {
                            Log.e("ProfileFragment", "Error reading response body", e);
                            Toast.makeText(requireContext(), "Failed to read response body", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Failed to upload file", Toast.LENGTH_SHORT).show();
                        Log.e("ProfileFragment", "Upload File Error: " + response.code() + " " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.e("ProfileFragment", "Upload File Error: " + t.getMessage(), t);
                    Toast.makeText(requireContext(), "Failed to upload file", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error reading file", Toast.LENGTH_SHORT).show();
        }
    }
    private int convertDpToPx(int dp) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    private void intentToFacebook() {
        String facebookUrl = "https://www.facebook.com/AuroraToys68";
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);

        try {
            if (requireContext().getPackageManager() != null) {
                requireContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
            }
            Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
            facebookIntent.setData(uri);
        } catch (PackageManager.NameNotFoundException e) {
            facebookIntent.setData(Uri.parse(facebookUrl));
        }

        startActivity(facebookIntent);
    }

    private void intentToMaps() {
        startActivity(new Intent(requireContext(), MapsActivity.class));
    }
}
