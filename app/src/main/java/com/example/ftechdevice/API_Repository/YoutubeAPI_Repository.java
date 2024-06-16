package com.example.ftechdevice.API_Repository;


import com.example.ftechdevice.API_Service.YoutubeAPI_Service;
import com.example.ftechdevice.Model.ModelRespone.YouTubeVideoListResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.scopes.ActivityScoped;
import retrofit2.Call;

@ActivityScoped
public class YoutubeAPI_Repository {

    private final YoutubeAPI_Service youtubeapiService;

    @Inject
    public YoutubeAPI_Repository(YoutubeAPI_Service youtubeapiService) {
        this.youtubeapiService = youtubeapiService;
    }

    public Call<YouTubeVideoListResponse> getVideoList(String id, String apiKey, String part) {
        return youtubeapiService.getVideoList(id, apiKey, part);
    }
}