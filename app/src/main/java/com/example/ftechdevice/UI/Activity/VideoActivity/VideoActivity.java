package com.example.ftechdevice.UI.Activity.VideoActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ftechdevice.API_Repository.YoutubeAPI_Repository;
import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.AppConfig.CustomView.CustomToolBar.CustomToolbar;
import com.example.ftechdevice.Common.Constants.Constants;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.databinding.ActivityVideoBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VideoActivity extends BaseActivity {

    private ActivityVideoBinding binding;
    private VideoAdapter videoAdapter;

    @Inject
    YoutubeAPI_Repository youtubeapiRepository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        videoAdapter = new VideoAdapter(Constants.getListVideos(), this, youtubeapiRepository);

        setContentView(binding.getRoot());
        setListVideoAdapter();
        backToMain();
    }

    private void backToMain() {

        binding.ctToolbar.setOnStartIconClickListener(new CustomToolbar.OnStartIconClickListener() {
            @Override
            public void onStartIconClick() {
                Intent intent = new Intent(VideoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setListVideoAdapter() {
        binding.rvVideos.setAdapter(videoAdapter);
        videoAdapter.setOnItemClickListener(video -> {
            Intent intent = new Intent(VideoActivity.this, VideoActivity_Screen2.class);
            intent.putExtra("VIDEO_ID", video.getId());
            startActivity(intent);
        });
    }
}