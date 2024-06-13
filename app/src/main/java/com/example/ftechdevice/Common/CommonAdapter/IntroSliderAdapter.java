package com.example.ftechdevice.Common.CommonAdapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftechdevice.R;

import java.util.List;

public class IntroSliderAdapter extends RecyclerView.Adapter<IntroSliderAdapter.IntroSliderViewHolder> {

    private final List<IntroSlide> introSlides;
    private final Context context;

    public IntroSliderAdapter(List<IntroSlide> introSlides, Context context) {
        this.introSlides = introSlides;
        this.context = context;
    }

    public static class IntroSlide {
        private final String title;
        private final String description;
        private final int image;
        private final int imageDecor;

        public IntroSlide(String title, String description, int image, int imageDecor) {
            this.title = title;
            this.description = description;
            this.image = image;
            this.imageDecor = imageDecor;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getImage() {
            return image;
        }

        public int getImageDecor() {
            return imageDecor;
        }
    }

    public class IntroSliderViewHolder extends RecyclerView.ViewHolder {

        private final Animation topAnimation;
        private final Animation bottomAnimation;
        private final TextView textTitle;
        private final TextView textDescription;
        private final ImageView image;
        private final ImageView imageDecor;

        public IntroSliderViewHolder(@NonNull View view) {
            super(view);
            topAnimation = AnimationUtils.loadAnimation(context, R.anim.top_animation);
            bottomAnimation = AnimationUtils.loadAnimation(context, R.anim.bottom_animation);
            textTitle = view.findViewById(R.id.intro_title);
            textDescription = view.findViewById(R.id.intro_description);
            image = view.findViewById(R.id.intro_image);
            imageDecor = view.findViewById(R.id.intro_decor_splash);
        }

        public void bind(IntroSlide introSlide) {
            textTitle.setText(introSlide.getTitle());
            textDescription.setText(introSlide.getDescription());
            image.setImageResource(introSlide.getImage());
            image.startAnimation(topAnimation);
            imageDecor.setImageResource(introSlide.getImageDecor());
            imageDecor.startAnimation(bottomAnimation);
        }

        public void animation() {
            // Additional animations can be added here
        }
    }

    @NonNull
    @Override
    public IntroSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container, parent, false);
        return new IntroSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroSliderViewHolder holder, int position) {
        holder.bind(introSlides.get(position));
        holder.animation();
    }

    @Override
    public int getItemCount() {
        return introSlides.size();
    }
}
