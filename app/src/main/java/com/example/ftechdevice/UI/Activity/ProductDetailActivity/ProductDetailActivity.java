package com.example.ftechdevice.UI.Activity.ProductDetailActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.ftechdevice.API_Repository.CartAPI_Repository;
import com.example.ftechdevice.API_Repository.ProductAPI_Repository;
import com.example.ftechdevice.AppConfig.CustomView.CustomToolBar.CustomToolbar;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.Model.CartModule.CartModel;
import com.example.ftechdevice.Model.CartModule.CartDTO;
import com.example.ftechdevice.Model.CartModule.CartResponse;
import com.example.ftechdevice.Model.ModelRespone.ProductReponse;
import com.example.ftechdevice.Model.ProductModel;
import com.example.ftechdevice.Model.UserJWT;
import com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivity;
import com.example.ftechdevice.UI.ShareViewModel.ShareViewModel;
import com.example.ftechdevice.Until.MyProgressDialog;
import com.example.ftechdevice.databinding.ActivityProducDetailBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProducDetailBinding binding;
    private ProductModel productModel;
    @Inject
    ProductAPI_Repository productAPIRepository;

    @Inject
    CartAPI_Repository cartAPIRepository;

    private ShareViewModel shareViewModel;


    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProducDetailBinding.inflate(getLayoutInflater());
        shareViewModel = new ViewModelProvider(this).get(ShareViewModel.class);
        progressDialog = new MyProgressDialog(this);
        setContentView(binding.getRoot());
        getProductModelByIDFromAPI();
        backToPreviousActivity();
        callAddToCart();
    }

    private void getProductModelByIDFromAPI() {
        int id = getIntent().getIntExtra("product_id", 0);
        Log.d("ID", String.valueOf(id));
        progressDialog.show();
        productAPIRepository.getProductById(id).enqueue(new Callback<ProductReponse>() {
            @Override
            public void onResponse(Call<ProductReponse> call, Response<ProductReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    ProductReponse productResponse = response.body();
                    Log.d("ProductDetailActivity", "Full Product Response: " + productResponse.toString());
                    productModel = mapProductToProductModel(productResponse);
                    bindDataProductDetaill();
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Failed to load product data", Toast.LENGTH_LONG).show();
                    Log.d("ProductDetailActivity", response.message());
                    Log.d("ProductDetailActivity", String.valueOf(id));
                    Log.d("ProductDetailActivity", String.valueOf(response.code()));
                    Log.d("ProductDetailActivity", String.valueOf(response.errorBody()));

                }
            }
            @Override
            public void onFailure(Call<ProductReponse> call, Throwable t) {
                Log.e("ProductDetailActivity", "API call failed: " + t.getMessage());
                Toast.makeText(ProductDetailActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void bindDataProductDetaill() {
        if (productModel == null) {
            Log.e("ProductDetailActivity", "bindDataProductDetaill: productModel is null");
            return;
        }

        binding.tvProductPrice.setText(formatPrice(productModel.getPrice()) + " VND");

        if (productModel.getProductCategory() != null) {
            binding.tvProductCategory.setText(productModel.getProductCategory().getName());
        } else {
            binding.tvProductCategory.setText("No category");
            Log.w("ProductDetailActivity", "Product category is null");
        }

        binding.tvProductName.setText(productModel.getName());
        binding.tvProductDescription.setText(productModel.getDescription());

        Glide.with(binding.imToyImage.getContext())
                .load(productModel.getImageUrl())
                .into(binding.imToyImage);
    }
    private void backToPreviousActivity() {
        binding.customToolbar2.setOnStartIconClickListener(new CustomToolbar.OnStartIconClickListener() {
            @Override
            public void onStartIconClick() {
                // Lấy danh sách hiện tại từ ShareViewModel
                List<CartModel> cartItems = shareViewModel.getCartItems().getValue();

                // Tạo một Intent để trả về dữ liệu
                Intent resultIntent = new Intent();
                resultIntent.putExtra("cart_items", new Gson().toJson(cartItems)); // Convert list to JSON string

                // Đặt kết quả và kết thúc Activity
                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });
    }
    private ProductModel mapProductToProductModel(ProductReponse productResponse) {
        ProductModel pModel = new ProductModel();

        if (productResponse != null) {
            pModel.setId(productResponse.getId());
            pModel.setName(productResponse.getName());
            pModel.setPrice(productResponse.getPrice());
            pModel.setDescription(productResponse.getDescription());
            pModel.setImageUrl(productResponse.getImageUrl());
            pModel.setQuantity(productResponse.getQuantity());
            pModel.setIsActive(productResponse.getActive());
            pModel.setProductCategory(productResponse.getProductCategory());

            if (productResponse.getProductCategory() != null) {
                pModel.setProductCategory(productResponse.getProductCategory());
            } else {
                Log.w("ProductDetailActivity", "Product category is null for product: " + productResponse.getName());
            }
        } else {
            Log.e("ProductDetailActivity", "ProductResponse is null");
        }

        return pModel;
    }


    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }

    private void callAddToCart() {
        binding.fbtnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserFromJWT() == null) {
                    Toast.makeText(ProductDetailActivity.this, "Bạn cần Đăng Nhập để tiếp tục", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProductDetailActivity.this, LoginActivity.class));
                } else {
                    UserJWT userJWT = getUserFromJWT();
                    CartDTO cartDTO = new CartDTO(0, userJWT.getUserId(), productModel.getId(), 1);
                    callAddProductToCart(userJWT.getAccessToken(), cartDTO, productModel);
                }
            }
        });

    }

    private UserJWT getUserFromJWT() {
        String accessToken = TokenManager.getAccessToken(ProductDetailActivity.this);
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

    private void callAddProductToCart(String token, CartDTO cartDTO, ProductModel p ) {
        progressDialog.show();
        cartAPIRepository.addToCart("Bearer " + token, cartDTO).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(ProductDetailActivity.this, "Add Sản Phẩm Vào Giỏ Hàng Thành Công", Toast.LENGTH_SHORT).show();
                    Log.d("CheckCartRespone",String.valueOf(response.body().getId()));
                    CartResponse.Product product = new CartResponse.Product(
                            p.getId(),
                            p.getName(),
                            p.getDescription(),
                            p.getPrice(),
                            p.getQuantity(),
                            p.getImageUrl(),
                            p.getIsActive(),
                            p.getProductCategory()
                    );
                    shareViewModel.addItem(CartModel.create(response.body().getId(),product, 1));



                } else {
                    Log.d("CheckCartRespone", String.valueOf(response.body()));
                    Log.d("CheckCartRespone", String.valueOf(response.code()));
                    Log.d("CheckCartRespone", String.valueOf(response.message()));
                    Log.d("CheckCartRespone", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

                Log.d("CheckCartRespone", String.valueOf(t.getMessage()));
            }
        });


    }

}
