package com.example.ftechdevice.UI.Fragment.CartFragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftechdevice.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ftechdevice.UI.ShareViewModel.ShareViewModel;
import com.example.ftechdevice.databinding.FragmentCartBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.util.List;


public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private ShareViewModel sharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartAdapter = new CartAdapter();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        cartAdapter.submitList(sharedViewModel.getCartItems().getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);

        binding.rlCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rlCart.setAdapter(cartAdapter);

        checkShowUI();
        setAddOrRemoveQuantity();
        observeViewModel();
        showPaymentDialog();
        return binding.getRoot();
    }

    private void setAddOrRemoveQuantity() {
        cartAdapter.setOnAddQuantityItemClickListener(item -> {
            Toast.makeText(requireContext(), "Add", Toast.LENGTH_SHORT).show();
            cartAdapter.addItem(item.getToyModel());
            return null;
        });

        cartAdapter.setOnRemoveQuantityItemClickListener(item -> {
            Toast.makeText(requireContext(), "Minus", Toast.LENGTH_SHORT).show();
            sharedViewModel.removeItem(item);
            checkShowUI();
            return null;
        });
    }

    private void observeViewModel() {
        sharedViewModel.getCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            cartAdapter.updateCartItems(cartItems);
            checkShowUI();
        });
    }

    private void showPaymentDialog() {
        binding.btnPayment.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
            View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_proceed_to_payment, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    convertDpToPx(430)
            ));

            TextView tong_tien_hang = view.findViewById(R.id.tv_tong_tien_hang);
            tong_tien_hang.setText(formatPrice(cartAdapter.getTotalItemsPrice()) + " VND");

            TextView tong_tien = view.findViewById(R.id.tv_tong_tien_thanh_toan);
            tong_tien.setText(formatPrice(cartAdapter.getTotalItemsPrice() + 20000.0) + " VND");

          /*  view.findViewById(R.id.btn_payment).setOnClickListener(v1 -> startActivity(new Intent(requireContext(), PaymentActivity.class)));*/

            dialog.setContentView(view);
            dialog.show();
        });
    }

    private int convertDpToPx(int dp) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    private void checkShowUI() {
        if (cartAdapter.getCurrentList().size() > 0) {
            binding.layoutEmptyCart.setVisibility(View.GONE);
            binding.layoutCart.setVisibility(View.VISIBLE);
        } else {
            binding.layoutEmptyCart.setVisibility(View.VISIBLE);
            binding.layoutCart.setVisibility(View.GONE);
        }
    }

    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }
}