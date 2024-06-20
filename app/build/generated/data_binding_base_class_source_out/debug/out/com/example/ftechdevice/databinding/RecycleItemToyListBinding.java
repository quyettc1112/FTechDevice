// Generated by view binder compiler. Do not edit!
package com.example.ftechdevice.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.ftechdevice.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RecycleItemToyListBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final CircleImageView cimHeart;

  @NonNull
  public final ImageView imToyImage;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final LinearLayout laAddToCarts;

  @NonNull
  public final TextView tvStarRating;

  @NonNull
  public final TextView tvToyName;

  @NonNull
  public final TextView tvToyPrice;

  private RecycleItemToyListBinding(@NonNull ConstraintLayout rootView, @NonNull CardView cardView,
      @NonNull CircleImageView cimHeart, @NonNull ImageView imToyImage,
      @NonNull ImageView imageView3, @NonNull LinearLayout laAddToCarts,
      @NonNull TextView tvStarRating, @NonNull TextView tvToyName, @NonNull TextView tvToyPrice) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.cimHeart = cimHeart;
    this.imToyImage = imToyImage;
    this.imageView3 = imageView3;
    this.laAddToCarts = laAddToCarts;
    this.tvStarRating = tvStarRating;
    this.tvToyName = tvToyName;
    this.tvToyPrice = tvToyPrice;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RecycleItemToyListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RecycleItemToyListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.recycle_item_toy_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RecycleItemToyListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardView;
      CardView cardView = ViewBindings.findChildViewById(rootView, id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.cim_heart;
      CircleImageView cimHeart = ViewBindings.findChildViewById(rootView, id);
      if (cimHeart == null) {
        break missingId;
      }

      id = R.id.im_toy_image;
      ImageView imToyImage = ViewBindings.findChildViewById(rootView, id);
      if (imToyImage == null) {
        break missingId;
      }

      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.la_addToCarts;
      LinearLayout laAddToCarts = ViewBindings.findChildViewById(rootView, id);
      if (laAddToCarts == null) {
        break missingId;
      }

      id = R.id.tv_star_rating;
      TextView tvStarRating = ViewBindings.findChildViewById(rootView, id);
      if (tvStarRating == null) {
        break missingId;
      }

      id = R.id.tv_toy_name;
      TextView tvToyName = ViewBindings.findChildViewById(rootView, id);
      if (tvToyName == null) {
        break missingId;
      }

      id = R.id.tv_toy_price;
      TextView tvToyPrice = ViewBindings.findChildViewById(rootView, id);
      if (tvToyPrice == null) {
        break missingId;
      }

      return new RecycleItemToyListBinding((ConstraintLayout) rootView, cardView, cimHeart,
          imToyImage, imageView3, laAddToCarts, tvStarRating, tvToyName, tvToyPrice);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
