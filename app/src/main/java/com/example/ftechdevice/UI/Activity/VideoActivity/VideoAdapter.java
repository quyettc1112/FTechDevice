package com.example.ftechdevice.UI.Activity.VideoActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ftechdevice.API_Repository.YoutubeAPI_Repository;
import com.example.ftechdevice.Model.ModelRespone.YouTubeVideoListResponse;
import com.example.ftechdevice.Model.VideoModel;
import com.example.ftechdevice.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<VideoModel> videoList;
    private Context context;
    private YoutubeAPI_Repository youtubeapiRepository;
    private String apiKey;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(VideoModel videoModel);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public VideoAdapter(List<VideoModel> videoList, Context context, YoutubeAPI_Repository youtubeapiRepository) {
        this.videoList = videoList;
        this.context = context;
        this.youtubeapiRepository = youtubeapiRepository;
        this.apiKey = context.getString(R.string.API_YOUTUBE_KEY);
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView im_video;
        TextView tv_video_header;
        TextView tv_video_description;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            im_video = itemView.findViewById(R.id.im_video);
            tv_video_header = itemView.findViewById(R.id.tv_video_header);
            tv_video_description = itemView.findViewById(R.id.tv_video_description);
        }

        public void bind(VideoModel video, Context context, YoutubeAPI_Repository youtubeapiRepository, String apiKey) {
            callYoutubeVideoList(video.getId(), im_video, tv_video_header, tv_video_description, context, youtubeapiRepository, apiKey);
        }

        private void callYoutubeVideoList(String id, ImageView im_video, TextView tv_video_header, TextView tv_video_description,
                                          Context context, YoutubeAPI_Repository youtubeapiRepository, String apiKey) {
            youtubeapiRepository.getVideoList(id, apiKey, "snippet,contentDetails").enqueue(new Callback<YouTubeVideoListResponse>() {
                @Override
                public void onResponse(@NonNull Call<YouTubeVideoListResponse> call, @NonNull Response<YouTubeVideoListResponse> response) {
                    if (response.isSuccessful()) {
                        YouTubeVideoListResponse videoListResponse = response.body();
                        if (videoListResponse != null) {
                            YouTubeVideoListResponse.VideoItem videoListResponseItem = videoListResponse.getItems().get(0);
                            Glide.with(context)
                                    .load(videoListResponseItem.getSnippet().getThumbnails().getHighThumbnail().getUrl())
                                    .into(im_video);

                            tv_video_header.setText(videoListResponseItem.getSnippet().getTitle());
                            tv_video_description.setText(videoListResponseItem.getSnippet().getDescription());
                        } else {
                            Log.d("YouTubeApi", "Response body is null");
                        }
                    } else {
                        Log.d("YouTubeApi", "Response failed with code: " + response.code() + " and message: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<YouTubeVideoListResponse> call, @NonNull Throwable t) {
                    Log.d("YouTubeApi", "Call Failure");
                }
            });
        }
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_videos, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoModel video = videoList.get(position);
        holder.bind(video, context, youtubeapiRepository, apiKey);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(video);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    @Override
    public void onViewRecycled(@NonNull VideoViewHolder holder) {
        super.onViewRecycled(holder);
    }
}