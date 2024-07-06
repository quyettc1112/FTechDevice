package com.example.ftechdevice.UI.Fragment.HomeFragment;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ftechdevice.API_Repository.ProductAPI_Repository;
import com.example.ftechdevice.API_Repository.UserAPI_Repository;
import com.example.ftechdevice.Common.CommonAdapter.CategoryOptionAdapter;
import com.example.ftechdevice.Common.CommonAdapter.CategoryOptionInteraction;
import com.example.ftechdevice.Common.CommonAdapter.ProductListAdapter;
import com.example.ftechdevice.Common.CommonAdapter.ToyListAdapter;
import com.example.ftechdevice.Common.CommonAdapter.VideoMainAdapter;
import com.example.ftechdevice.Common.Constants.Constants;
import com.example.ftechdevice.Model.ModelRespone.ProductReponse;
import com.example.ftechdevice.Model.ProductModel;
import com.example.ftechdevice.Model.ToyModel;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.ChatModule.ChatActivity.ChatActivity;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.UI.Activity.ProductDetailActivity.ProductDetailActivity;
import com.example.ftechdevice.UI.Activity.VideoActivity.VideoActivity;
import com.example.ftechdevice.UI.ShareViewModel.ShareViewModel;
import com.example.ftechdevice.Until.BottomMarginItemDecoration;
import com.example.ftechdevice.Until.NonScrollableGridLayoutManager;
import com.example.ftechdevice.databinding.ActivityMainBinding;
import com.example.ftechdevice.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@AndroidEntryPoint
public class HomeFragment extends Fragment implements CategoryOptionInteraction, CategoryOptionAdapter.CategoryOptionInteraction {

    private FragmentHomeBinding binding;
    private VideoMainAdapter videoAdapter;
    private CategoryOptionAdapter cateOptionAdapter;
    private ToyListAdapter toyListAdapter;
    private ProductListAdapter productListAdapter;
    private Integer pageNo = 0, pageSize = 10;
    @Inject
    ProductAPI_Repository productAPIRepository;
    private List<ProductModel> productList = new ArrayList<ProductModel>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoAdapter = new VideoMainAdapter(Constants.getListCourse());
        cateOptionAdapter = new CategoryOptionAdapter(Constants.getListString(), this);
        toyListAdapter = new ToyListAdapter(Constants.getListToys());
        productListAdapter = new ProductListAdapter(productList);
        toyListAdapter.setOnItemCartClickListener(cartModel -> {
            Toast.makeText(requireContext(), cartModel.getToyName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        callProductAPI();
        setUpVideoMainRecycleView();
        setIndicator();
        setCurrentIndicator(0);
        setRecycleCateOption();;
        intentToVideoActivity();
        intentToChatActivity();

        MainActivity activity = (MainActivity) getActivity();
        ActivityMainBinding mainBinding = activity.binding;
        setupCategoryClickListeners(mainBinding);



        return binding.getRoot();
    }
    private void setupCategoryClickListeners(ActivityMainBinding mainBinding) {
        binding.llLaptop.setOnClickListener(v -> handleCategoryClick(mainBinding, 1));
        binding.llDienthoai.setOnClickListener(v -> handleCategoryClick(mainBinding, 2));
        binding.llBanphim.setOnClickListener(v -> handleCategoryClick(mainBinding, 3));
        binding.llManhinh.setOnClickListener(v -> handleCategoryClick(mainBinding, 4));
    }

    private void handleCategoryClick(ActivityMainBinding mainBinding, int categoryId) {
        ShareViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        sharedViewModel.setCategoryId(categoryId);
        mainBinding.vp2Main.setCurrentItem(1, true);
    }

    private void setRecycleCateOption() {
        binding.myRecyclerView.setAdapter(cateOptionAdapter);
    }

    private void setRecycleProductList() {
        binding.rvToys.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastCompletelyVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();

                if (lastCompletelyVisibleItem == totalItemCount - 1) {
                    pageSize += ((pageNo + 1) * pageSize <= productListAdapter.getItemCount()) ? pageSize : 0;

                    Log.d("Scroll to end", "On Scroll");
                    callProductAPI();
                }
            }
        });

        binding.rvToys.setLayoutManager(new NonScrollableGridLayoutManager(requireContext(), 2));
        binding.rvToys.setAdapter(productListAdapter);

        int bottomMarginInPx = convertDpToPx(20);
        binding.rvToys.addItemDecoration(new BottomMarginItemDecoration(bottomMarginInPx));
        int itemCount = productListAdapter.getItemCount();
        int rowCount = (itemCount % 2 == 0) ? itemCount / 2 : (itemCount / 2) + 1;
        int newHeight = rowCount * convertDpToPx(300) + (rowCount - 1) * bottomMarginInPx;

        // Set new height for RecyclerView
        ViewGroup.LayoutParams layoutParams = binding.rvToys.getLayoutParams();
        layoutParams.height = newHeight;
        binding.rvToys.setLayoutParams(layoutParams);

        productListAdapter.setOnItemClickListener(new ProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductModel product) {
                Intent intent = new Intent(requireContext(), ProductDetailActivity.class);
                intent.putExtra("product_id", product.getId());
                requireContext().startActivity(intent);
            }
        });
    }

    private void setUpVideoMainRecycleView() {
        binding.rvVideo.setAdapter(videoAdapter);

        binding.rvVideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                setCurrentIndicator(firstVisibleItemPosition);
            }
        });
    }

    private void setIndicator() {
        int itemCount = videoAdapter.getItemCount();
        ImageView[] indicator = new ImageView[itemCount];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(15, 0, 15, 0);
        for (int i = 0; i < itemCount; i++) {
            indicator[i] = new ImageView(requireContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    requireContext(), R.drawable.indicator_inactive));
            indicator[i].setLayoutParams(layoutParams);
            binding.indicatorContainer.addView(indicator[i]);
        }
    }

    private void setCurrentIndicator(int index) {
        int childCount = binding.indicatorContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.indicatorContainer.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        requireContext(), R.drawable.indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        requireContext(), R.drawable.indicator_inactive));
            }
        }
    }

    @Override
    public void setActive(int position) {
        RecyclerView.ViewHolder viewHolder = binding.myRecyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder instanceof CategoryOptionAdapter.CateOptionViewHolder) {
            ((CategoryOptionAdapter.CateOptionViewHolder) viewHolder).setActiveItem();
        }
    }

    private int convertDpToPx(int dp) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    private void intentToVideoActivity() {
        binding.tvSeeAllVideo.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), VideoActivity.class));
        });
    }

    private void callProductAPI(){
        productAPIRepository.getProductList(pageNo, pageSize, "", 0).enqueue(new Callback<ProductReponse>() {
            @Override
            public void onResponse(Call<ProductReponse> call, Response<ProductReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductModel> products = response.body().getContent();
                    Log.d("Check value", "Products size: " + (products != null ? products.size() : 0));
                    if (products != null) {
                        productList = products;
                        productListAdapter = new ProductListAdapter(productList);
                        setRecycleProductList();
                    }
                } else {
                    Log.d("Check value", "Response code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ProductReponse> call, Throwable t) {
                Log.d("Check value", t.getMessage());
            }
        });
    }

    private void intentToChatActivity() {
        binding.imNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), ChatActivity.class));
            }
        });

    }


}