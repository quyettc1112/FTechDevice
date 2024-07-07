package com.example.ftechdevice.UI.Activity.VideoActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.ftechdevice.AppConfig.BaseConfig.BaseActivity;
import com.example.ftechdevice.databinding.ActivityVideoScreen2Binding;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;

import dagger.hilt.android.AndroidEntryPoint;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

@AndroidEntryPoint
public class VideoActivity_Screen2 extends BaseActivity {

    private ActivityVideoScreen2Binding binding;
    private YouTubePlayer youtubePlayer;
    private boolean isFullScreen = false;
    private String videoIDIntent;
    private boolean isPlayerReady = false;

    // Cấu hình OnBackPressed
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (isFullScreen) {
                youtubePlayer.toggleFullscreen();
            } else {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoScreen2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thêm callback để xử lý khi nhấn nút Back
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        // Thêm đối tượng YouTubePlayer vào Lifecycle để quản lý vòng đời
        getLifecycle().addObserver(binding.wvVideoIframes);

        // Lấy ID video từ Intent
        videoIDIntent = getIntent().getStringExtra("VIDEO_ID");

        // Thiết lập listener cho sự kiện fullscreen của YouTubePlayerView
        binding.wvVideoIframes.addFullscreenListener(new FullscreenListener() {
            @Override
            public void onEnterFullscreen(@NonNull View view, @NonNull Function0<Unit> function0) {
                if (!isFullScreen) {
                    isFullScreen = true;
                    binding.flFullScrenContainer.setVisibility(View.VISIBLE);
                    binding.flFullScrenContainer.addView(view);

                    // Ẩn thanh điều hướng của hệ thống khi vào chế độ fullscreen
                    WindowInsetsControllerCompat insetsController = new WindowInsetsControllerCompat(getWindow(), view);
                    insetsController.hide(WindowInsetsCompat.Type.systemBars());
                    insetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

                    // Chỉ định màn hình xoay ngang khi chế độ hiện tại không phải là LANDSCAPE
                    if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    }
                }
            }

            @Override
            public void onExitFullscreen() {
                if (isFullScreen) {
                    isFullScreen = false;
                    binding.flFullScrenContainer.setVisibility(View.GONE);
                    binding.flFullScrenContainer.removeAllViews();

                    // Hiện lại thanh điều hướng của hệ thống khi thoát fullscreen
                    WindowInsetsControllerCompat insetsController = new WindowInsetsControllerCompat(getWindow(), binding.wvVideoIframes);
                    insetsController.show(WindowInsetsCompat.Type.systemBars());

                    // Chỉ định màn hình xoay dọc khi chế độ hiện tại không phải là PORTRAIT
                    if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                    }
                }
            }
        });

        // Thiết lập listener cho YouTubePlayer để load video khi sẵn sàng
        AbstractYouTubePlayerListener youtubeListener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youtubePlayer = youTubePlayer;
                isPlayerReady = true;
                String videoId = videoIDIntent;
                youTubePlayer.loadVideo(videoId, 0f); // Load video with initial time 0f
            }
        };

        // Tạo các tùy chọn cho IFramePlayer
        IFramePlayerOptions iFramePlayerOption = new IFramePlayerOptions.Builder()
                .controls(1)
                .fullscreen(1)
                .build();

        // Khởi tạo YouTubePlayerView và thiết lập các tùy chọn
        binding.wvVideoIframes.setEnableAutomaticInitialization(false);
        binding.wvVideoIframes.initialize(youtubeListener, iFramePlayerOption);

        // Thiết lập lại hành động khi xoay màn hình
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            if (isFullScreen) {
                // Nếu đang ở chế độ fullscreen, ẩn thanh điều hướng khi có sự thay đổi về cửa sổ
                WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), binding.flFullScrenContainer);
                controller.hide(WindowInsetsCompat.Type.systemBars());
            }
            return insets.consumeSystemWindowInsets();
        });

        // Thiết lập hành động khi nhấn vào nút quay lại trên CustomToolbar
        binding.ctToolbarVideoActivity.setOnStartIconClickListener(() -> {
            startActivity(new Intent(VideoActivity_Screen2.this, VideoActivity.class));

        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Xử lý khi xoay màn hình
        if (isPlayerReady && youtubePlayer != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && !isFullScreen) {
                youtubePlayer.toggleFullscreen();
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT && isFullScreen) {
                youtubePlayer.toggleFullscreen();
            }
        }
    }
}
