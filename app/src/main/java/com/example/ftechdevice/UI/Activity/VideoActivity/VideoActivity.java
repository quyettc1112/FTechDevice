package com.example.ftechdevice.UI.Activity.VideoActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.R;
import com.example.ftechdevice.databinding.ActivityVideoBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VideoActivity extends BaseActivity {

    private ActivityVideoBinding binding;
   // private VideoAdapter videoAdapter;

   /* @Inject
    YoutubeAPI_Repository youtubeapiRepository;
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* binding = ActivityVideoBinding.inflate(getLayoutInflater());
        videoAdapter = new VideoAdapter(Constant.getListVideos(), this, youtubeapiRepository);

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            WindowInsetsCompat systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setListVideoAdapter();
        backToMain();*/
    }

   /* private void backToMain() {
        binding.ctToolbar.setOnStartIconClickListener(v -> {
            Intent intent = new Intent(VideoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setListVideoAdapter() {
        binding.rvVideos.setAdapter(videoAdapter);
        videoAdapter.setOnItemClickListener(video -> {
            Intent intent = new Intent(VideoActivity.this, VideoActivity_Screen2.class);
            intent.putExtra("VIDEO_ID", video.getId());
            startActivity(intent);
        });
    }*/
}