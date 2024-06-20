// Generated by view binder compiler. Do not edit!
package com.example.ftechdevice.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.ftechdevice.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RecycleItemCourseBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView ivCourseMain;

  @NonNull
  public final MaterialCardView loContent;

  @NonNull
  public final TextView tvCourseTitleMain;

  private RecycleItemCourseBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView ivCourseMain, @NonNull MaterialCardView loContent,
      @NonNull TextView tvCourseTitleMain) {
    this.rootView = rootView;
    this.ivCourseMain = ivCourseMain;
    this.loContent = loContent;
    this.tvCourseTitleMain = tvCourseTitleMain;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RecycleItemCourseBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RecycleItemCourseBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.recycle_item_course, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RecycleItemCourseBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.iv_course_main;
      ImageView ivCourseMain = ViewBindings.findChildViewById(rootView, id);
      if (ivCourseMain == null) {
        break missingId;
      }

      id = R.id.lo_content;
      MaterialCardView loContent = ViewBindings.findChildViewById(rootView, id);
      if (loContent == null) {
        break missingId;
      }

      id = R.id.tv_course_title_main;
      TextView tvCourseTitleMain = ViewBindings.findChildViewById(rootView, id);
      if (tvCourseTitleMain == null) {
        break missingId;
      }

      return new RecycleItemCourseBinding((ConstraintLayout) rootView, ivCourseMain, loContent,
          tvCourseTitleMain);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
