package com.example.ftechdevice.API_Service;

import com.example.ftechdevice.Model.ModelRespone.YouTubeVideoListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeAPI_Service {

    @GET("videos")
    Call<YouTubeVideoListResponse> getVideoList(
            @Query("id") String id,
            @Query("key") String apiKey,
            @Query("part") String part
    );
}
