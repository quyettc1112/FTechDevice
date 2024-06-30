package com.example.ftechdevice.UI.Activity.MainActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.AppConfig.CustomView.CustomBottomNav.NiceBottomBar;
import com.example.ftechdevice.Common.CommonAdapter.FragmentAdapter;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.ChatModule.ChatActivity.ChatActivity;
import com.example.ftechdevice.UI.Fragment.CartFragment.CartFragment;
import com.example.ftechdevice.UI.Fragment.HomeFragment.HomeFragment;
import com.example.ftechdevice.UI.Fragment.ProductFragment.ProductFragment;
import com.example.ftechdevice.UI.Fragment.ProfileFragment.ProfileFragment;
import com.example.ftechdevice.UI.ShareViewModel.ShareViewModel;
import com.example.ftechdevice.databinding.ActivityMainBinding;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {

    private ShareViewModel sharedViewModel;
    public ActivityMainBinding binding;
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedViewModel = new ViewModelProvider(this).get(ShareViewModel.class);

        setUpFragmentWithViewPager();
        setUpBottomNav();
    }

    private void setUpFragmentWithViewPager() {
        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(new HomeFragment());
        listFragment.add(new ProductFragment());
        listFragment.add(new CartFragment());
        listFragment.add(new ProfileFragment());

        fragmentAdapter = new FragmentAdapter(this, listFragment);
        binding.vp2Main.setAdapter(fragmentAdapter);
        binding.vp2Main.setUserInputEnabled(false);
        binding.vp2Main.setOffscreenPageLimit(4);

        // Set up change listener
        binding.vp2Main.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.niceBottomNav.setActiveItem(position);
                super.onPageSelected(position);
            }
        });

        floatButtonHandle();
    }

    private void setUpBottomNav() {
        binding.niceBottomNav.setOnItemSelectedListener(new NiceBottomBar.OnItemSelectedListener() {

            @Override
            public void onItemSelect(int index) {
                binding.vp2Main.setCurrentItem(index, true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setMessage("Bạn muốn thoát khỏi ứng dụng ?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }


    private void floatButtonHandle() {
        binding.fabPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0356970686"));
                startActivity(intent);
            }
        });


        binding.fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });

    }
}