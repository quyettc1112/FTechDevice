package com.example.ftechdevice.UI.Fragment.CartFragment;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftechdevice.API_Repository.CartAPI_Repository;
import com.example.ftechdevice.AppConfig.CustomView.CustomDialog.ErrorDialog;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.Model.CartModule.CartModel;
import com.example.ftechdevice.Model.CartModule.CartDTO;
import com.example.ftechdevice.Model.CartModule.CartResponse;
import com.example.ftechdevice.Model.UserJWT;
import com.example.ftechdevice.R;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ftechdevice.UI.Activity.PaymentActivity.PaymentActivity;
import com.example.ftechdevice.UI.ShareViewModel.ShareViewModel;
import com.example.ftechdevice.Until.CartParser;
import com.example.ftechdevice.Until.MyProgressDialog;
import com.example.ftechdevice.databinding.FragmentCartBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private ShareViewModel sharedViewModel;

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private  BiometricManager biometricManage;


    @Inject
    CartAPI_Repository cartAPIRepository;

    private MyProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartAdapter = new CartAdapter();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        biometricManage = BiometricManager.from(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        showPaymentDialog();
        checkShowUI();
        callGetAllCarts();
        //checkShowUI();
        checkBiometricAvailability();
        progressDialog = new MyProgressDialog(requireContext());
        binding.rlCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rlCart.setAdapter(cartAdapter);

        setAddOrRemoveQuantity();
        return binding.getRoot();
    }

    private void setAddOrRemoveQuantity() {
        cartAdapter.setOnAddQuantityItemClickListener(item -> {
            callAddMoreQuantity(item.getProduct());
            return null;
        });

        cartAdapter.setOnRemoveQuantityItemClickListener(item -> {
            //sharedViewModel.removeItem(item);
            UserJWT userJWT  = getUserFromJWT();
            if (userJWT != null) {
                CartDTO cartDTO = new CartDTO(item.getId(),userJWT.getUserId(), item.getProduct().getId(), item.getQuantity());
                if (cartDTO.getQuantity() > 1 && cartDTO.getQuantity() != 0) {
                    cartDTO.setQuantity(cartDTO.getQuantity() - 1);
                    callMinusQuantity(cartDTO.getId(), cartDTO, item);
                } else {
                    callDeleteCart(item.getId(), item);
                }

            }

            Log.d("checkCartID info", String.valueOf(item.getId()));
        //    Log.d("checkCartID info", String.valueOf(item.getProduct()));
            Log.d("checkCartID info", String.valueOf(item.getQuantity()));
            checkShowUI();
            return null;
        });
    }

    private void observeViewModel() {
        sharedViewModel.getCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            cartAdapter.updateCartItems(cartItems);
            checkShowUI();
        });
    }

    private void showPaymentDialog() {
        callGetAllCarts();
        binding.btnPayment.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
            View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_proceed_to_payment, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    convertDpToPx(430)
            ));
            TextView tong_tien_hang = view.findViewById(R.id.tv_tong_tien_hang);
            tong_tien_hang.setText(formatPrice(cartAdapter.getTotalItemsPrice()) + " VND");

            TextView tong_tien = view.findViewById(R.id.tv_tong_tien_thanh_toan);
            tong_tien.setText(formatPrice(cartAdapter.getTotalItemsPrice() + 20000.0) + " VND");

            view.findViewById(R.id.btn_payment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    authenticateUser();
                }
            });
            dialog.setContentView(view);
            dialog.show();
        });
    }

    private int convertDpToPx(int dp) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    private void checkShowUI() {
        if (cartAdapter.getCurrentList().size() > 0) {
            binding.layoutEmptyCart.setVisibility(View.GONE);
            binding.layoutCart.setVisibility(View.VISIBLE);
        } else {
            binding.layoutEmptyCart.setVisibility(View.VISIBLE);
            binding.layoutCart.setVisibility(View.GONE);
        }
    }

    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }

    private void callGetAllCarts() {
       if (getUserFromJWT() != null) {
           UserJWT userJWT = getUserFromJWT();

           cartAPIRepository.getCarts("Bearer "+ userJWT.getAccessToken(), 0, 20,  "")
                   .enqueue(new Callback<List<CartResponse>>() {

                       @Override
                       public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                           progressDialog.show();
                           if (response.isSuccessful()) {
                               progressDialog.dismiss();
                               Gson gson = new Gson();
                               String jsonResponse = gson.toJson(response.body());
                               List<CartModel> cartList = CartParser.parseCartResponse(jsonResponse);
                               sharedViewModel.updateCartItems(cartList);
                               cartAdapter.submitList(cartList);
                               observeViewModel();
                               checkShowUI();
                           } else  {
                               ErrorDialog e = new ErrorDialog(
                                       requireContext(),
                                       String.valueOf(response.code())
                               );
                               e.show();
                           }
                       }
                       @Override
                       public void onFailure(Call<List<CartResponse>> call, Throwable t) {
                           Log.d("checkCartRespone",String.valueOf(t.getMessage()));
                       }
                   });
       }



    }
    private UserJWT getUserFromJWT() {
        String accessToken = TokenManager.getAccessToken(requireContext());
        if (accessToken != null) {
            try {
                JSONObject decodedPayload = JWTDecoder.decodeJWT(accessToken);
                UserJWT user = new UserJWT();
                user.setAccessToken(accessToken);
                user.setSubject(decodedPayload.getString("sub"));
                user.setEmail(decodedPayload.getString("email"));
                user.setUserId(decodedPayload.getInt("userId"));
                user.setRoleName(decodedPayload.getString("RoleName"));
                user.setPhone(decodedPayload.getString("phone"));
                user.setIssuedAt(decodedPayload.getLong("iat"));
                user.setExpiration(decodedPayload.getLong("exp"));
                return user;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("CheckOnResume", "ok");

    }

    private void callAddMoreQuantity (CartResponse.Product product) {
        UserJWT userJWT = getUserFromJWT();
        progressDialog.show();
        CartDTO cartDTO = new CartDTO(0, userJWT.getUserId(),product.getId(), 1);
        if (product != null && cartDTO!= null) {
           cartAPIRepository.addToCart("Bearer " + userJWT.getAccessToken(), cartDTO).enqueue(new Callback<CartResponse>() {
               @Override
               public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                   if (response.isSuccessful()) {
                       progressDialog.dismiss();
                       cartAdapter.addItem(product);
                       Log.d("callAddMoreQuantity", "Add More Successfull");
                   } else {
                       ErrorDialog e = new ErrorDialog(
                               requireContext(),
                               String.valueOf(response.code())
                       );
                       e.show();
                   }
               }
               @Override
               public void onFailure(Call<CartResponse> call, Throwable t) {
                   ErrorDialog e = new ErrorDialog(
                           requireContext(),
                           String.valueOf(t.getMessage())
                   );
                   e.show();
               }
           });
        }
    }

    private void callMinusQuantity(int id, CartDTO cartDTO, CartModel item) {
        UserJWT userJWT = getUserFromJWT();
        if (cartDTO != null && userJWT.getAccessToken() != null) {
            cartAPIRepository.putCart("Bearer "+ userJWT.getAccessToken(), id, cartDTO)
                    .enqueue(new Callback<CartResponse>() {
                        @Override
                        public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                            if (response.isSuccessful()) {
                                Log.d("Minus Quantity Success", "Minus Quantity Success");
                                sharedViewModel.removeItem(item);
                            } else {
                                ErrorDialog er = new ErrorDialog(
                                        requireContext(),
                                        String.valueOf(response.code()) + String.valueOf(response.message()) + String.valueOf(response.errorBody())
                                );
                                er.show();
                            }
                        }
                        @Override
                        public void onFailure(Call<CartResponse> call, Throwable t) {
                            ErrorDialog er = new ErrorDialog(
                                    requireContext(),
                                    String.valueOf(t.getMessage())
                            );
                            er.show();
                        }
                    });
        }


    }

    private void callDeleteCart(int id, CartModel item) {
        UserJWT userJWT = getUserFromJWT();
        progressDialog.show();
        if (userJWT != null) {
            cartAPIRepository.deleteCartById("Bearer " + userJWT.getAccessToken(), id)
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("callDeleteCart", "Delete Cart Successful");
                                progressDialog.dismiss();
                                sharedViewModel.removeItem(item);
                            } else {
                                ErrorDialog er = new ErrorDialog(
                                        requireContext(),
                                        String.valueOf(response.code())
                                );
                                er.show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });
        }
    }

    private void checkBiometricAvailability() {
        BiometricManager biometricManager = BiometricManager.from(requireContext());
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL | BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                // Device can authenticate with biometrics
                // Initialize biometric authentication
                initializeBiometricAuthentication();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                // No biometric features available on this device
                // Show an appropriate message to the user
                Toast.makeText(requireContext(), "Biometric authentication is not available on this device.", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                // Biometric features are currently unavailable
                // Show an appropriate message to the user
                Toast.makeText(requireContext(), "Biometric features are currently unavailable. Please try again later.", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // The user hasn't associated any biometric credentials with their account
                // Show a message directing the user to set up biometric credentials
                Toast.makeText(requireContext(), "No biometric credentials found. Please set up biometrics in your device settings.", Toast.LENGTH_SHORT).show();
                // Optionally, direct the user to the biometric settings
                Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL);
                startActivity(enrollIntent);
                break;

            default:
                // Handle any other potential errors
                Toast.makeText(requireContext(), "Biometric authentication is not supported.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initializeBiometricAuthentication() {
        biometricPrompt = new BiometricPrompt(this, ContextCompat.getMainExecutor(requireContext()), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // Handle the error
                Toast.makeText(requireContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                onBiometricAuthenticationSuccess();
                Toast.makeText(requireContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // Handle the failure
                Toast.makeText(requireContext(), "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric demo")
                .setSubtitle("Authenticate using biometrics")
                .setNegativeButtonText("Cancel")
                .build();
    }

    private void authenticateUser() {
        if (biometricPrompt != null && promptInfo != null) {
            biometricPrompt.authenticate(promptInfo);
        } else {
            Toast.makeText(requireContext(), "Biometric authentication is not properly initialized.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onBiometricAuthenticationSuccess() {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        Intent intent = new Intent(requireContext(), PaymentActivity.class);
        intent.putExtra("amount", cartAdapter.getTotalItemsPrice() + 20000.0);
        intent.putExtra("orderinfo", currentTime.toString());
        intent.putParcelableArrayListExtra("list_cart_model", new ArrayList<>(sharedViewModel.getCartItems().getValue()));
        startActivity(intent);
    }




}