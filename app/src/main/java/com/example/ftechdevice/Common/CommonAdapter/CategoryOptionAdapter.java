package com.example.ftechdevice.Common.CommonAdapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftechdevice.R;

import java.util.List;
public class CategoryOptionAdapter extends RecyclerView.Adapter<CategoryOptionAdapter.CateOptionViewHolder> {

    private final List<CategoryString> items;
    private final CategoryOptionInteraction interaction;
    private OnItemClickListenerID onItemClickListenerID;
    private int activePosition = 0;

    public CategoryOptionAdapter(List<CategoryString> items, CategoryOptionInteraction interaction) {
        this.items = items;
        this.interaction = interaction;
    }

    public interface OnItemClickListenerID {
        void onItemClick(int position);
    }

    public void setOnItemClickListenerID(OnItemClickListenerID listener) {
        this.onItemClickListenerID = listener;
    }

    @NonNull
    @Override
    public CateOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new CateOptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CateOptionViewHolder holder, int position) {
        boolean isActive = position == activePosition;
        CategoryString item = items.get(position);
        holder.bind(item, isActive);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int dpToPx(int dp, Context context) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public class CateOptionViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        public CateOptionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
        }

        public void bind(CategoryString content, boolean isActive) {
            title.setText(content.getTitle());
            if (isActive) {
                setActiveItem();
            } else {
                setInactiveItem();
            }
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                notifyItemChanged(activePosition);
                activePosition = getAdapterPosition();
                notifyItemChanged(activePosition);
                interaction.setActive(getAdapterPosition());
                if (onItemClickListenerID != null) {
                    onItemClickListenerID.onItemClick(getAdapterPosition());
                }
            });
        }

        public void setActiveItem() {
            title.setTextColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                title.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(title.getContext(), R.color.redPrimary)));
            }
        }

        public void setInactiveItem() {
            title.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                title.setBackgroundTintList(null);
            }
        }
    }

    public static class CategoryString {
        private final String title;

        public CategoryString(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    public interface CategoryOptionInteraction {
        void setActive(int position);
    }
}
