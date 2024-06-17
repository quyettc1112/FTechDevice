package com.example.ftechdevice.UI.Fragment.ProductFragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftechdevice.Common.CommonAdapter.CategoryOptionAdapter;
import com.example.ftechdevice.Common.CommonAdapter.CategoryOptionInteraction;
import com.example.ftechdevice.Common.CommonAdapter.ToyListAdapterBase;
import com.example.ftechdevice.Common.Constants.Constants;
import com.example.ftechdevice.Model.CartModel;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.ProductDetailActivity.ProductDetailActivity;
import com.example.ftechdevice.UI.ShareViewModel.ShareViewModel;
import com.example.ftechdevice.databinding.FragmentProductBinding;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.List;

public class ProductFragment extends Fragment implements CategoryOptionInteraction , CategoryOptionAdapter.CategoryOptionInteraction{


    private FragmentProductBinding binding;
    private CategoryOptionAdapter categoryAdapter;
    private ToyListAdapterBase toyListAdapter;
    private ProductViewModel productListViewModel;
    private ShareViewModel sharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryAdapter = new CategoryOptionAdapter(Constants.getListString(), this);

        productListViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productListViewModel.setToyList(Constants.getListToys());

        toyListAdapter = new ToyListAdapterBase();
        toyListAdapter.submitList(productListViewModel.getCurrentToyList().getValue());

        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        setCateRecycleView();
        setToyListAdapter();
        observeViewModel();
        searchItem();
        clickPopularProduct();
        showFillterDialog();
        return binding.getRoot();
    }

    private void setCateRecycleView() {
        binding.myRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void setActive(int position) {
        CategoryOptionAdapter.CateOptionViewHolder viewHolder = (CategoryOptionAdapter.CateOptionViewHolder) binding.myRecyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            viewHolder.setActiveItem();
        }
    }

    private void setToyListAdapter() {
        binding.rvToys.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvToys.setAdapter(toyListAdapter);

        // Item Click Product Detail
        toyListAdapter.setItemOnClickListener(toy -> {
            Toast.makeText(getContext(), "Clicked: " + toy.getToyName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireContext(), ProductDetailActivity.class);
            intent.putExtra("product_id", toy.getId());
            requireContext().startActivity(intent);
        });

        // Add To Cart Click
        toyListAdapter.setOnItemCartClickListener(toy -> sharedViewModel.addItem(CartModel.create(toy, 1)));
    }

    private void searchItem() {
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                productListViewModel.setCurrentSearchValue(query);
                productListViewModel.filterToyList(query);
            }
        });
    }

    private void observeViewModel() {
        productListViewModel.getCurrentToyList().observe(getViewLifecycleOwner(), toyList -> toyListAdapter.submitList(toyList));
    }

    private void clickPopularProduct() {
        categoryAdapter.setOnItemClickListenerID(position -> {
            productListViewModel.setCurrentPopular(position);
            productListViewModel.filterToyList(productListViewModel.getCurrentSearchLiveData().getValue());
        });
    }

    private void showFillterDialog() {
        binding.imFillter.setOnClickListener(v -> {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_fillter_toys, null);
            dialogView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    convertDpToPx(670)
            ));
            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
            dialog.setContentView(dialogView);

            // Turn Off Dialog
            dialogView.findViewById(R.id.im_dissmissDialog).setOnClickListener(view -> dialog.dismiss());

            // Initialize TextViews
            TextView tv_g_all = dialogView.findViewById(R.id.tv_g_all);
            TextView tv_g_nam = dialogView.findViewById(R.id.tv_g_nam);
            TextView tv_g_nu = dialogView.findViewById(R.id.tv_g_nu);

            // Set OnClickListener for each TextView
            tv_g_all.setOnClickListener(onClickListener);
            tv_g_nam.setOnClickListener(onClickListener);
            tv_g_nu.setOnClickListener(onClickListener);

            dialog.show();
        });
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ViewGroup dialogView = (ViewGroup) view.getParent();
            TextView textViewTatCa = dialogView.findViewById(R.id.tv_g_all);
            TextView textViewNam = dialogView.findViewById(R.id.tv_g_nam);
            TextView textViewNu = dialogView.findViewById(R.id.tv_g_nu);

            // Set all TextViews to inactive
            setInactive(textViewTatCa);
            setInactive(textViewNam);
            setInactive(textViewNu);

            setActive((TextView) view);
        }
    };

    private void setActive(TextView textView) {
        textView.setTextColor(Color.WHITE);
        // Set background tint (requires API level 21+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(textView.getContext(), R.color.redPrimary)));
        }
    }

    private void setInactive(TextView textView) {
        textView.setTextColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView.setBackgroundTintList(null);
        }
    }

    private int convertDpToPx(int dp) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }
}