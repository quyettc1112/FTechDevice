// Generated by view binder compiler. Do not edit!
package com.example.ftechdevice.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.airbnb.lottie.LottieAnimationView;
import com.example.ftechdevice.AppConfig.CustomView.CustomToolBar.CustomToolbar;
import com.example.ftechdevice.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterScreen3Binding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final LottieAnimationView anime;

  @NonNull
  public final AppCompatButton btnRegisterScreen3;

  @NonNull
  public final CustomToolbar customToolbarScreen3;

  @NonNull
  public final EditText edtRegisterDob;

  @NonNull
  public final EditText edtRegisterGender;

  @NonNull
  public final EditText edtRegisterName;

  @NonNull
  public final EditText edtRegisterPhone;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView textView2;

  private ActivityRegisterScreen3Binding(@NonNull ConstraintLayout rootView,
      @NonNull LottieAnimationView anime, @NonNull AppCompatButton btnRegisterScreen3,
      @NonNull CustomToolbar customToolbarScreen3, @NonNull EditText edtRegisterDob,
      @NonNull EditText edtRegisterGender, @NonNull EditText edtRegisterName,
      @NonNull EditText edtRegisterPhone, @NonNull ConstraintLayout main,
      @NonNull TextView textView2) {
    this.rootView = rootView;
    this.anime = anime;
    this.btnRegisterScreen3 = btnRegisterScreen3;
    this.customToolbarScreen3 = customToolbarScreen3;
    this.edtRegisterDob = edtRegisterDob;
    this.edtRegisterGender = edtRegisterGender;
    this.edtRegisterName = edtRegisterName;
    this.edtRegisterPhone = edtRegisterPhone;
    this.main = main;
    this.textView2 = textView2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterScreen3Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterScreen3Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register_screen3, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterScreen3Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.anime;
      LottieAnimationView anime = ViewBindings.findChildViewById(rootView, id);
      if (anime == null) {
        break missingId;
      }

      id = R.id.btn_register_screen3;
      AppCompatButton btnRegisterScreen3 = ViewBindings.findChildViewById(rootView, id);
      if (btnRegisterScreen3 == null) {
        break missingId;
      }

      id = R.id.customToolbar_screen3;
      CustomToolbar customToolbarScreen3 = ViewBindings.findChildViewById(rootView, id);
      if (customToolbarScreen3 == null) {
        break missingId;
      }

      id = R.id.edt_register_dob;
      EditText edtRegisterDob = ViewBindings.findChildViewById(rootView, id);
      if (edtRegisterDob == null) {
        break missingId;
      }

      id = R.id.edt_register_gender;
      EditText edtRegisterGender = ViewBindings.findChildViewById(rootView, id);
      if (edtRegisterGender == null) {
        break missingId;
      }

      id = R.id.edt_register_name;
      EditText edtRegisterName = ViewBindings.findChildViewById(rootView, id);
      if (edtRegisterName == null) {
        break missingId;
      }

      id = R.id.edt_register_phone;
      EditText edtRegisterPhone = ViewBindings.findChildViewById(rootView, id);
      if (edtRegisterPhone == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      return new ActivityRegisterScreen3Binding((ConstraintLayout) rootView, anime,
          btnRegisterScreen3, customToolbarScreen3, edtRegisterDob, edtRegisterGender,
          edtRegisterName, edtRegisterPhone, main, textView2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
