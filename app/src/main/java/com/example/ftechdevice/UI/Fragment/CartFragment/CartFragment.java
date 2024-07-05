package com.example.ftechdevice.UI.Fragment.CartFragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftechdevice.API_Repository.CartAPI_Repository;
import com.example.ftechdevice.AppConfig.CustomView.CustomDialog.ErrorDialog;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.Model.CartModel;
import com.example.ftechdevice.Model.CartModule.CartDTO;
import com.example.ftechdevice.Model.CartModule.CartResponse;
import com.example.ftechdevice.Model.UserJWT;
import com.example.ftechdevice.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ftechdevice.UI.Activity.PaymentActivity.PaymentActivity;
import com.example.ftechdevice.UI.ShareViewModel.ShareViewModel;
import com.example.ftechdevice.Until.CartParser;
import com.example.ftechdevice.databinding.FragmentCartBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
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


    @Inject
    CartAPI_Repository cartAPIRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartAdapter = new CartAdapter();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        showPaymentDialog();
        callGetCarts();
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
            sharedViewModel.removeItem(item);
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
                    Calendar calendar = Calendar.getInstance();
                    Date currentTime = calendar.getTime();

                    Intent intent = new Intent(requireContext(), PaymentActivity.class);
                    intent.putExtra("amount", cartAdapter.getTotalItemsPrice() + 20000.0);
                    intent.putExtra("orderinfo", currentTime.toString());
                    startActivity(intent);
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
            Toast.makeText(requireContext(), "1", Toast.LENGTH_SHORT).show();
            binding.layoutEmptyCart.setVisibility(View.GONE);
            binding.layoutCart.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(requireContext(), "2", Toast.LENGTH_SHORT).show();
            binding.layoutEmptyCart.setVisibility(View.VISIBLE);
            binding.layoutCart.setVisibility(View.GONE);
        }
    }

    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }

    private void callGetCarts() {
       if (getUserFromJWT() != null) {
           UserJWT userJWT = getUserFromJWT();
           cartAPIRepository.getCarts("Bearer "+ userJWT.getAccessToken(), 0, 20,  "")
                   .enqueue(new Callback<List<CartResponse>>() {
                       @Override
                       public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                           if (response.isSuccessful()) {
                               Gson gson = new Gson();
                               String jsonResponse = gson.toJson(response.body());
                               List<CartModel> cartList = CartParser.parseCartResponse(jsonResponse);
                               sharedViewModel.updateCartItems(cartList);
                                cartAdapter.submitList(cartList);
                                observeViewModel();
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
        CartDTO cartDTO = new CartDTO(0, userJWT.getUserId(),product.getId(), 1);
        if (product != null && cartDTO!= null) {
           cartAPIRepository.addToCart("Bearer " + userJWT.getAccessToken(), cartDTO).enqueue(new Callback<CartResponse>() {
               @Override
               public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                   if (response.isSuccessful()) {
                       cartAdapter.addItem(product);
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


}