package com.example.ftechdevice.UI.Activity.StartActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.Common.CommonAdapter.IntroSliderAdapter;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.databinding.ActivityStartBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StartActivity extends BaseActivity {

    private ActivityStartBinding binding;
    private final IntroSliderAdapter introSliderAdapter = new IntroSliderAdapter(
            Arrays.asList(
                    new IntroSliderAdapter.IntroSlide(
                            "Aurora Toy-Store",
                            "Bắt đầu hành trình khám phá thế giới đồ chơi dành cho trẻ cùng Aurora Toy-Store",
                            R.drawable.ic_material_start_1,
                            R.drawable.decor_splash
                    ),
                    new IntroSliderAdapter.IntroSlide(
                            "Kiến Thức Phong Phú",
                            "Khám phá thế giới đồ chơi đặc biệt dành cho trẻ tự kỷ và tìm hiểu sâu hơn về tự kỷ cùng Aurora Toy-Store",
                            R.drawable.ic_material_start_2,
                            R.drawable.decor_splash2
                    ),
                    new IntroSliderAdapter.IntroSlide(
                            "Trải Nghiệm Mọi Lúc, Mọi Nơi",
                            "Lan toả câu chuyện cộng đồng về sự phát triển tiềm năng của trẻ cùng Aurora Toy-Store",
                            R.drawable.ic_material_start_3,
                            R.drawable.image_decor_rm_bg
                    )
            ), this
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setIntroSlider();
        setIndicator();
        setCurrentIndicator(0);
        callEventChangeViewPager();
        clickButtonNext();
    }

    private void clickButtonNext() {
        binding.btnNext.setOnClickListener(v -> {
            if (binding.introSliderViewpager.getCurrentItem() + 1 < introSliderAdapter.getItemCount()) {
                binding.introSliderViewpager.setCurrentItem(binding.introSliderViewpager.getCurrentItem() + 1);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void callEventChangeViewPager() {
        binding.introSliderViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
    }

    private void setIntroSlider() {
        binding.introSliderViewpager.setAdapter(introSliderAdapter);
    }

    private void setIndicator() {
        ImageView[] indicator = new ImageView[introSliderAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(15, 0, 15, 0);
        for (int i = 0; i < indicator.length; i++) {
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(
                    ContextCompat.getDrawable(
                            getApplicationContext(),
                            R.drawable.indicator_inactive
                    )
            );
            indicator[i].setLayoutParams(layoutParams);
            binding.indicatorContainer.addView(indicator[i]);
        }
    }

    private void setCurrentIndicator(int index) {
        int childCount = binding.indicatorContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.indicatorContainer.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.indicator_active
                        )
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.indicator_inactive
                        )
                );
            }
        }
    }
}