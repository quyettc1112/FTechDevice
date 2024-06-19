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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.ftechdevice.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogProceedToPaymentBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton btnPayment;

  @NonNull
  public final ConstraintLayout constraintLayout4;

  @NonNull
  public final LinearLayout linearLayout15;

  @NonNull
  public final LinearLayout linearLayout16;

  @NonNull
  public final LinearLayout linearLayout17;

  @NonNull
  public final LinearLayout linearLayout18;

  @NonNull
  public final LinearLayout linearLayout19;

  @NonNull
  public final TextView tvChietKhau;

  @NonNull
  public final TextView tvPhiGiaoHang;

  @NonNull
  public final TextView tvTongTienHang;

  @NonNull
  public final TextView tvTongTienThanhToan;

  private DialogProceedToPaymentBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatButton btnPayment, @NonNull ConstraintLayout constraintLayout4,
      @NonNull LinearLayout linearLayout15, @NonNull LinearLayout linearLayout16,
      @NonNull LinearLayout linearLayout17, @NonNull LinearLayout linearLayout18,
      @NonNull LinearLayout linearLayout19, @NonNull TextView tvChietKhau,
      @NonNull TextView tvPhiGiaoHang, @NonNull TextView tvTongTienHang,
      @NonNull TextView tvTongTienThanhToan) {
    this.rootView = rootView;
    this.btnPayment = btnPayment;
    this.constraintLayout4 = constraintLayout4;
    this.linearLayout15 = linearLayout15;
    this.linearLayout16 = linearLayout16;
    this.linearLayout17 = linearLayout17;
    this.linearLayout18 = linearLayout18;
    this.linearLayout19 = linearLayout19;
    this.tvChietKhau = tvChietKhau;
    this.tvPhiGiaoHang = tvPhiGiaoHang;
    this.tvTongTienHang = tvTongTienHang;
    this.tvTongTienThanhToan = tvTongTienThanhToan;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogProceedToPaymentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogProceedToPaymentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_proceed_to_payment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogProceedToPaymentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_payment;
      AppCompatButton btnPayment = ViewBindings.findChildViewById(rootView, id);
      if (btnPayment == null) {
        break missingId;
      }

      id = R.id.constraintLayout4;
      ConstraintLayout constraintLayout4 = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout4 == null) {
        break missingId;
      }

      id = R.id.linearLayout15;
      LinearLayout linearLayout15 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout15 == null) {
        break missingId;
      }

      id = R.id.linearLayout16;
      LinearLayout linearLayout16 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout16 == null) {
        break missingId;
      }

      id = R.id.linearLayout17;
      LinearLayout linearLayout17 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout17 == null) {
        break missingId;
      }

      id = R.id.linearLayout18;
      LinearLayout linearLayout18 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout18 == null) {
        break missingId;
      }

      id = R.id.linearLayout19;
      LinearLayout linearLayout19 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout19 == null) {
        break missingId;
      }

      id = R.id.tv_chiet_khau;
      TextView tvChietKhau = ViewBindings.findChildViewById(rootView, id);
      if (tvChietKhau == null) {
        break missingId;
      }

      id = R.id.tv_phi_giao_hang;
      TextView tvPhiGiaoHang = ViewBindings.findChildViewById(rootView, id);
      if (tvPhiGiaoHang == null) {
        break missingId;
      }

      id = R.id.tv_tong_tien_hang;
      TextView tvTongTienHang = ViewBindings.findChildViewById(rootView, id);
      if (tvTongTienHang == null) {
        break missingId;
      }

      id = R.id.tv_tong_tien_thanh_toan;
      TextView tvTongTienThanhToan = ViewBindings.findChildViewById(rootView, id);
      if (tvTongTienThanhToan == null) {
        break missingId;
      }

      return new DialogProceedToPaymentBinding((ConstraintLayout) rootView, btnPayment,
          constraintLayout4, linearLayout15, linearLayout16, linearLayout17, linearLayout18,
          linearLayout19, tvChietKhau, tvPhiGiaoHang, tvTongTienHang, tvTongTienThanhToan);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
