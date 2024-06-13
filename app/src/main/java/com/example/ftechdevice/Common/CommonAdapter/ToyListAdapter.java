package com.example.ftechdevice.Common.CommonAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftechdevice.Model.ToyModel;
import com.example.ftechdevice.R;

import java.text.DecimalFormat;
import java.util.List;

public class ToyListAdapter extends RecyclerView.Adapter<ToyListAdapter.ToyListViewHolder> {

    private final List<ToyModel> toys;
    private OnItemClickListener onItemClickListener;
    private OnItemCartClickListener onItemCartClickListener;

    public ToyListAdapter(List<ToyModel> toys) {
        this.toys = toys;
    }

    public interface OnItemClickListener {
        void onItemClick(ToyModel toy);
    }

    public interface OnItemCartClickListener {
        void onItemCartClick(ToyModel toy);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemCartClickListener(OnItemCartClickListener listener) {
        this.onItemCartClickListener = listener;
    }

    @NonNull
    @Override
    public ToyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_toy_list, parent, false);
        return new ToyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToyListViewHolder holder, int position) {
        ToyModel toy = toys.get(position);
        holder.bind(toy);
    }

    @Override
    public int getItemCount() {
        return toys.size();
    }

    public class ToyListViewHolder extends RecyclerView.ViewHolder {

        private final ImageView toyImageView;
        private final TextView tvToyPrice;
        private final TextView tvToyName;
        private final TextView tvStarRating;
        private final LinearLayout laAddToCarts;

        public ToyListViewHolder(@NonNull View view) {
            super(view);
            toyImageView = view.findViewById(R.id.im_toy_image);
            tvToyPrice = view.findViewById(R.id.tv_toy_price);
            tvToyName = view.findViewById(R.id.tv_toy_name);
            tvStarRating = view.findViewById(R.id.tv_star_rating);
            laAddToCarts = view.findViewById(R.id.la_addToCarts);
        }

        public void bind(ToyModel toy) {
            toyImageView.setImageResource(toy.getToyImage());
            tvToyPrice.setText(formatPrice(toy.getToyPrice()) + " VND");
            tvToyName.setText(toy.getToyName());
            tvStarRating.setText("(" + toy.getToyRating() + ")");
            laAddToCarts.setOnClickListener(v -> {
                if (onItemCartClickListener != null) {
                    onItemCartClickListener.onItemCartClick(toy);
                }
            });
        }

        private String formatPrice(double price) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            return formatter.format(price);
        }
    }
}
