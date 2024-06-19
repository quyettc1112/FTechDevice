// Generated by view binder compiler. Do not edit!
package com.example.ftechdevice.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.ftechdevice.AppConfig.CustomView.CustomToolBar.CustomToolbar;
import com.example.ftechdevice.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProfileBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CircleImageView ivUserAvatar;

  @NonNull
  public final LinearLayout laIntentToFacebook;

  @NonNull
  public final LinearLayout layoutMaps;

  @NonNull
  public final LinearLayout layoutSetting;

  @NonNull
  public final AppCompatButton logout;

  @NonNull
  public final CustomToolbar toolblarCustome;

  @NonNull
  public final TextView userName;

  private FragmentProfileBinding(@NonNull LinearLayout rootView,
      @NonNull CircleImageView ivUserAvatar, @NonNull LinearLayout laIntentToFacebook,
      @NonNull LinearLayout layoutMaps, @NonNull LinearLayout layoutSetting,
      @NonNull AppCompatButton logout, @NonNull CustomToolbar toolblarCustome,
      @NonNull TextView userName) {
    this.rootView = rootView;
    this.ivUserAvatar = ivUserAvatar;
    this.laIntentToFacebook = laIntentToFacebook;
    this.layoutMaps = layoutMaps;
    this.layoutSetting = layoutSetting;
    this.logout = logout;
    this.toolblarCustome = toolblarCustome;
    this.userName = userName;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ivUserAvatar;
      CircleImageView ivUserAvatar = ViewBindings.findChildViewById(rootView, id);
      if (ivUserAvatar == null) {
        break missingId;
      }

      id = R.id.la_intent_to_facebook;
      LinearLayout laIntentToFacebook = ViewBindings.findChildViewById(rootView, id);
      if (laIntentToFacebook == null) {
        break missingId;
      }

      id = R.id.layout_maps;
      LinearLayout layoutMaps = ViewBindings.findChildViewById(rootView, id);
      if (layoutMaps == null) {
        break missingId;
      }

      id = R.id.layoutSetting;
      LinearLayout layoutSetting = ViewBindings.findChildViewById(rootView, id);
      if (layoutSetting == null) {
        break missingId;
      }

      id = R.id.logout;
      AppCompatButton logout = ViewBindings.findChildViewById(rootView, id);
      if (logout == null) {
        break missingId;
      }

      id = R.id.toolblarCustome;
      CustomToolbar toolblarCustome = ViewBindings.findChildViewById(rootView, id);
      if (toolblarCustome == null) {
        break missingId;
      }

      id = R.id.userName;
      TextView userName = ViewBindings.findChildViewById(rootView, id);
      if (userName == null) {
        break missingId;
      }

      return new FragmentProfileBinding((LinearLayout) rootView, ivUserAvatar, laIntentToFacebook,
          layoutMaps, layoutSetting, logout, toolblarCustome, userName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
