// Generated by data binding compiler. Do not edit!
package com.example.ftechdevice.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.example.ftechdevice.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class CustomToolBarBinding extends ViewDataBinding {
  @NonNull
  public final ConstraintLayout clToolBar;

  @NonNull
  public final ImageView ivEndIcon;

  @NonNull
  public final ImageView ivStartIcon;

  @NonNull
  public final LinearLayout llEndIcon;

  @NonNull
  public final LinearLayout llStartIcon;

  @NonNull
  public final View toolBarShadow;

  @NonNull
  public final TextView tvStartText;

  @NonNull
  public final TextView tvTitle;

  protected CustomToolBarBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ConstraintLayout clToolBar, ImageView ivEndIcon, ImageView ivStartIcon,
      LinearLayout llEndIcon, LinearLayout llStartIcon, View toolBarShadow, TextView tvStartText,
      TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.clToolBar = clToolBar;
    this.ivEndIcon = ivEndIcon;
    this.ivStartIcon = ivStartIcon;
    this.llEndIcon = llEndIcon;
    this.llStartIcon = llStartIcon;
    this.toolBarShadow = toolBarShadow;
    this.tvStartText = tvStartText;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static CustomToolBarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.custom_tool_bar, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static CustomToolBarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<CustomToolBarBinding>inflateInternal(inflater, R.layout.custom_tool_bar, root, attachToRoot, component);
  }

  @NonNull
  public static CustomToolBarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.custom_tool_bar, null, false, component)
   */
  @NonNull
  @Deprecated
  public static CustomToolBarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<CustomToolBarBinding>inflateInternal(inflater, R.layout.custom_tool_bar, null, false, component);
  }

  public static CustomToolBarBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static CustomToolBarBinding bind(@NonNull View view, @Nullable Object component) {
    return (CustomToolBarBinding)bind(component, view, R.layout.custom_tool_bar);
  }
}
