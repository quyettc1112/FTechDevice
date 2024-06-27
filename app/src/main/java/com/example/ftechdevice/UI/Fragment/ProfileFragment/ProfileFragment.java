package com.example.ftechdevice.UI.Fragment.ProfileFragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.ftechdevice.API_Repository.UserAPI_Repository;
import com.example.ftechdevice.API_Service.UserAPI_Service;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.Model.ModelRespone.UserResponseDTO;
import com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivity;
import com.example.ftechdevice.UI.Activity.MapActivity.MapsActivity;
import com.example.ftechdevice.databinding.FragmentProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.example.ftechdevice.R;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    String email = null;

    @Inject
    UserAPI_Service userApiService;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String accessToken = TokenManager.getAccessToken(requireContext());

        if(accessToken != null) {
            JSONObject decodedPayload = JWTDecoder.decodeJWT(accessToken);
            try {
                email = decodedPayload.getString("email");
                getUserByEmail(accessToken,email);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Log.d("JWT", email);
        }
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.logout.setOnClickListener(v -> showLogoutDialog());



        intentToFaceBook();
        intentToMaps();

        return binding.getRoot();
    }

    private void getUserByEmail(String token, String email) {
        userApiService.getUserByEmail("Bearer " + token, email)
                .enqueue(new Callback<UserResponseDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<UserResponseDTO> call, @NonNull Response<UserResponseDTO> response) {
                        if (response.isSuccessful()) {
                            UserResponseDTO userResponse = response.body();
                            if (userResponse != null && binding != null) {
                                if(userResponse.getFullName() != null) {
                                    binding.userName.setText(userResponse.getFullName());
                                }else{
                                    binding.userName.setText("No Name");
                                }
                            } else {

                                Log.d("check null","empty");
                            }
                        } else {

                            Log.d("check fall","Failed to get user data");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserResponseDTO> call, @NonNull Throwable t) {

                        Log.d("check fall",t.getMessage());
                    }
                });
    }
    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            Log.e("Error", "Context is null, cannot show toast: " + message);
        }
    }
    private void intentToFaceBook() {
        binding.laIntentToFacebook.setOnClickListener(v -> {
            String facebookUrl = "https://www.facebook.com/AuroraToys68";
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);

            try {
                // Kiểm tra xem Facebook có được cài đặt hay không
                if (requireContext().getPackageManager() != null) {
                    requireContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                }
                Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                facebookIntent.setData(uri);
            } catch (PackageManager.NameNotFoundException e) {
                // Nếu Facebook không được cài đặt, mở bằng trình duyệt
                facebookIntent.setData(Uri.parse(facebookUrl));
            }
            startActivity(facebookIntent);
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

    private int convertDpToPx(int dp) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    private void intentToMaps() {
        binding.layoutMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), MapsActivity.class));
            }
        });


    }


}