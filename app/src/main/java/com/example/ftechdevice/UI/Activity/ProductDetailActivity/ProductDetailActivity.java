package com.example.ftechdevice.UI.Activity.ProductDetailActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.ArrayList;
import dagger.hilt.android.AndroidEntryPoint;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.AppConfig.CustomView.CustomToolBar.CustomToolbar;
import com.example.ftechdevice.Common.CommonAdapter.ImageReleventAdapter;
import com.example.ftechdevice.Common.Constants.Constants;
import com.example.ftechdevice.Model.ImageReleventModel;
import com.example.ftechdevice.Model.ToyModel;
import com.example.ftechdevice.R;
import com.example.ftechdevice.databinding.ActivityProducDetailBinding;

@AndroidEntryPoint
public class ProductDetailActivity extends BaseActivity {

    private ActivityProducDetailBinding binding;
    private ToyModel toyModel;

    private ImageReleventAdapter imageReleventAdapter;
    private ArrayList<ImageReleventModel> listItemRelevent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProducDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpImageRelevent();
        backToPreviousActivity();
        bindDataProductDetail();
    }

    private void setUpImageRelevent() {
        imageReleventAdapter = new ImageReleventAdapter();
        getToyModelByIDFromConstants();

        if (toyModel != null) {
            listItemRelevent.add(new ImageReleventModel(0, toyModel.getListImage().get(0)));
            listItemRelevent.add(new ImageReleventModel(1, toyModel.getListImage().get(1)));
            listItemRelevent.add(new ImageReleventModel(2, toyModel.getListImage().get(2)));
            imageReleventAdapter.submitList(listItemRelevent);
            binding.rlImageRelevent.setAdapter(imageReleventAdapter);
            imageReleventAdapter.setOnItemImageClickListener(imageReleventModel -> binding.imToyImage.setImageResource(imageReleventModel.getImage()));
        }
    }

    private void backToPreviousActivity() {
        binding.customToolbar2.setOnStartIconClickListener(new CustomToolbar.OnStartIconClickListener() {
            @Override
            public void onStartIconClick() {
                finish();
            }
        });
    }

    private void getToyModelByIDFromConstants() {
        int id = getIntent().getIntExtra("product_id", 0);
        toyModel = Constants.getListToys().get(id);
    }

    private void bindDataProductDetail() {
        binding.tvProductPrice.setText(formatPrice(toyModel.getToyPrice()) + " VND");
        binding.tvProductCategory.setText(toyModel.getCategoryModel().getName());
        binding.tvProductName.setText(toyModel.getToyName());
        binding.tvProductDescription.setText(toyModel.getToyDescription());
        binding.imToyImage.setImageResource(toyModel.getToyImage());
    }

    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }
}