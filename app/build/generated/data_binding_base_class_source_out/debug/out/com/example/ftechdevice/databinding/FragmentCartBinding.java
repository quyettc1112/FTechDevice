// Generated by view binder compiler. Do not edit!
package com.example.ftechdevice.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.airbnb.lottie.LottieAnimationView;
import com.example.ftechdevice.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentCartBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final AppCompatButton btnPayment;

  @NonNull
  public final LinearLayout layoutCart;

  @NonNull
  public final LinearLayout layoutEmptyCart;

  @NonNull
  public final LottieAnimationView ltEmptyCart;

  @NonNull
  public final RecyclerView rlCart;

  private FragmentCartBinding(@NonNull LinearLayout rootView, @NonNull AppCompatButton btnPayment,
      @NonNull LinearLayout layoutCart, @NonNull LinearLayout layoutEmptyCart,
      @NonNull LottieAnimationView ltEmptyCart, @NonNull RecyclerView rlCart) {
    this.rootView = rootView;
    this.btnPayment = btnPayment;
    this.layoutCart = layoutCart;
    this.layoutEmptyCart = layoutEmptyCart;
    this.ltEmptyCart = ltEmptyCart;
    this.rlCart = rlCart;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentCartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentCartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_cart, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentCartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_payment;
      AppCompatButton btnPayment = ViewBindings.findChildViewById(rootView, id);
      if (btnPayment == null) {
        break missingId;
      }

      id = R.id.layout_cart;
      LinearLayout layoutCart = ViewBindings.findChildViewById(rootView, id);
      if (layoutCart == null) {
        break missingId;
      }

      id = R.id.layout_empty_cart;
      LinearLayout layoutEmptyCart = ViewBindings.findChildViewById(rootView, id);
      if (layoutEmptyCart == null) {
        break missingId;
      }

      id = R.id.lt_empty_cart;
      LottieAnimationView ltEmptyCart = ViewBindings.findChildViewById(rootView, id);
      if (ltEmptyCart == null) {
        break missingId;
      }

      id = R.id.rl_cart;
      RecyclerView rlCart = ViewBindings.findChildViewById(rootView, id);
      if (rlCart == null) {
        break missingId;
      }

      return new FragmentCartBinding((LinearLayout) rootView, btnPayment, layoutCart,
          layoutEmptyCart, ltEmptyCart, rlCart);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
