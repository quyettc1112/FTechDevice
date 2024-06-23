package com.example.ftechdevice.UI.Activity.ProductDetailActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ftechdevice.API_Repository.ProductAPI_Repository;
import com.example.ftechdevice.AppConfig.CustomView.CustomToolBar.CustomToolbar;
import com.example.ftechdevice.Model.ModelRespone.ProductReponse;
import com.example.ftechdevice.Model.ProductModel;
import com.example.ftechdevice.databinding.ActivityProducDetailBinding;

import java.text.DecimalFormat;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProducDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getProductModelByIDFromAPI();
        backToPreviousActivity();
    }

    private void getProductModelByIDFromAPI() {
        int id = getIntent().getIntExtra("product_id", 0);
        Log.d("ID", String.valueOf(id));
        productAPIRepository.getProductById("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmciLCJlbWFpbCI6ImFkbWluMUBnbWFpbC5jb20iLCJ1c2VySWQiOjEsIlJvbGVOYW1lIjoiQURNSU4iLCJpYXQiOjE3MTkxNTExNTQsImV4cCI6MTcxOTIzNzU1NH0.Jo1tfdoawII6H2hKn239xXJE8SY_iplSQE6JGm2UX-0"
                , id).enqueue(new Callback<ProductReponse>() {
            @Override
            public void onResponse(Call<ProductReponse> call, Response<ProductReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
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
                finish();
            }
        });
    }
    private ProductModel mapProductToProductModel(ProductReponse productResponse) {
        ProductModel pModel = new ProductModel();

        if (productResponse != null) {
            pModel.setName(productResponse.getName());
            pModel.setPrice(productResponse.getPrice());
            pModel.setDescription(productResponse.getDescription());
            pModel.setImageUrl(productResponse.getImageUrl());

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
}
