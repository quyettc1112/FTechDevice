package com.example.ftechdevice.Common.CommonAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftechdevice.R;

import java.text.DecimalFormat;
import java.util.List;

public class VideoMainAdapter extends RecyclerView.Adapter<VideoMainAdapter.CourseMainViewHolder> {

    private final List<Course> courseList;
    private OnItemClickListener onItemClick;

    public interface OnItemClickListener {
        void onItemClick(Course course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClick = listener;
    }

    public VideoMainAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    public static class Course {
        private final int image;
        private final String title;
        private final double originalPrice;
        private final double discountedPrice;
        private final double rating;
        private final int reviews;

        public Course(int image, String title, double originalPrice, double discountedPrice, double rating, int reviews) {
            this.image = image;
            this.title = title;
            this.originalPrice = originalPrice;
            this.discountedPrice = discountedPrice;
            this.rating = rating;
            this.reviews = reviews;
        }

        public int getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public double getOriginalPrice() {
            return originalPrice;
        }

        public double getDiscountedPrice() {
            return discountedPrice;
        }

        public double getRating() {
            return rating;
        }

        public int getReviews() {
            return reviews;
        }
    }

    public class CourseMainViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;

        // Uncomment these lines if you want to include them
        /*
        private final TextView originalPrice;
        private final TextView discountedPrice;
        private final RatingBar rating;
        private final TextView reviews;
        */

        public CourseMainViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.iv_course_main);
            title = view.findViewById(R.id.tv_course_title_main);

            // Uncomment these lines if you want to include them
            /*
            originalPrice = view.findViewById(R.id.tv_course_originalPrice_main);
            discountedPrice = view.findViewById(R.id.tv_course_discountedPrice_main);
            rating = view.findViewById(R.id.ratingbar_main);
            reviews = view.findViewById(R.id.tv_numRating_main);
            */
        }

        public void bind(Course course) {
            image.setImageResource(course.getImage());
            title.setText(course.getTitle());

            // Uncomment these lines if you want to include them
            /*
            originalPrice.setText(formatPrice(course.getOriginalPrice()) + " VND");
            SpannableString spannableString = new SpannableString(originalPrice.getText());
            spannableString.setSpan(new StrikethroughSpan(), 0, originalPrice.getText().length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
            originalPrice.setText(spannableString);

            discountedPrice.setText(formatPrice(course.getDiscountedPrice()) + " VND");
            rating.setRating((float) course.getRating());
            reviews.setText("(" + course.getReviews() + ")");
            */
        }

        private String formatPrice(double price) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            return formatter.format(price);
        }
    }

    @NonNull
    @Override
    public CourseMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_course, parent, false);
        return new CourseMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseMainViewHolder holder, int position) {
        holder.bind(courseList.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.onItemClick(courseList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
}