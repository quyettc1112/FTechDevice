package com.example.ftechdevice.UI.Fragment.ProductFragment;

import static android.app.Activity.RESULT_OK;

import androidx.lifecycle.ViewModelProvider;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftechdevice.API_Repository.CartAPI_Repository;
import com.example.ftechdevice.API_Repository.ProductAPI_Repository;
import com.example.ftechdevice.Common.CommonAdapter.CategoryOptionAdapter;
import com.example.ftechdevice.Common.CommonAdapter.CategoryOptionInteraction;
import com.example.ftechdevice.Common.CommonAdapter.ProductListAdapterBase;
import com.example.ftechdevice.Common.Constants.Constants;
import com.example.ftechdevice.Common.TokenManger.TokenManager;
import com.example.ftechdevice.JWT.JWTDecoder;
import com.example.ftechdevice.Model.CartModule.CartModel;
import com.example.ftechdevice.Model.CartModule.CartDTO;
import com.example.ftechdevice.Model.CartModule.CartResponse;
import com.example.ftechdevice.Model.ModelRespone.ProductReponse;
import com.example.ftechdevice.Model.ProductModel;
import com.example.ftechdevice.Model.UserJWT;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.UI.Activity.ProductDetailActivity.ProductDetailActivity;
import com.example.ftechdevice.UI.ShareViewModel.ShareViewModel;
import com.example.ftechdevice.Until.MyProgressDialog;
import com.example.ftechdevice.databinding.ActivityMainBinding;
import com.example.ftechdevice.databinding.FragmentProductBinding;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@AndroidEntryPoint
public class ProductFragment extends Fragment implements CategoryOptionInteraction , CategoryOptionAdapter.CategoryOptionInteraction {
    private FragmentProductBinding binding;
    private CategoryOptionAdapter categoryAdapter;
    private ProductViewModel productListViewModel;
    private ShareViewModel sharedViewModel;
    private ProductListAdapterBase pproductListAdapter;
    private List<ProductModel> productList = new ArrayList<>();
    private Integer maxRecords = 10;
    private Integer categoryId = 0;
    private Integer pageNo = 0;
    @Inject
    ProductAPI_Repository productAPIRepository;

    @Inject
    CartAPI_Repository cartAPIRepository;

    private MyProgressDialog myProgressDialogl;

    public static final int REQUEST_CODE_PRODUCT_DETAIL = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryAdapter = new CategoryOptionAdapter(Constants.getListString(), this);

        productListViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        pproductListAdapter = new ProductListAdapterBase();


        productListViewModel.setProductList(productList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        myProgressDialogl = new MyProgressDialog(requireContext());
        setCateRecycleView();
        setProductListAdapter();
        callProductAPI();
        observeViewModel();
        searchItem();
        showFillterDialog();
        sharedViewModel.getCategoryId().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer categoryId_Input) {
                maxRecords = 10;
                categoryId = categoryId_Input;
                callProductAPI();
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PRODUCT_DETAIL && resultCode == RESULT_OK) {
            String cartItemsJson = data.getStringExtra("cart_items");
            if (cartItemsJson != null) {
                Type cartListType = new TypeToken<List<CartModel>>() {}.getType();
                List<CartModel> cartItems = new Gson().fromJson(cartItemsJson, cartListType);
                if (cartItems != null && !cartItems.isEmpty()) {
                    sharedViewModel.addItems(cartItems);
                }
            } else {
                Log.d("checkcartintedetha", cartItemsJson + " Null");
            }
        }
    }

    private void setProductListAdapter() {
        binding.rvToys.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastCompletelyVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();

                if (lastCompletelyVisibleItem == totalItemCount - 1) {
                    maxRecords += ((pageNo + 1) * maxRecords <= pproductListAdapter.getItemCount()) ? maxRecords : 0;

                    Log.d("Page no and child count", pageNo.toString() + pproductListAdapter.getItemCount());
                    callProductAPI();
                }
            }
        });

        binding.rvToys.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvToys.setAdapter(pproductListAdapter);

        // Item Click Product Detail
        pproductListAdapter.setItemOnClickListener(p -> {
            Intent intent = new Intent(requireContext(), ProductDetailActivity.class);
            intent.putExtra("product_id", p.getId());
            //requireContext().startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE_PRODUCT_DETAIL);
        });

        pproductListAdapter.setOnItemCartClickListener(p -> {
            if (getUserFromJWT() == null) {
                Toast.makeText(requireContext(), "Bạn cần Đăng Nhập để tiếp tục", Toast.LENGTH_SHORT).show();
                MainActivity activity = (MainActivity) getActivity();
                ActivityMainBinding mainBinding = activity.binding;
                mainBinding.vp2Main.setCurrentItem(3, true);
            } else {
                UserJWT userJWT = getUserFromJWT();
                CartDTO cartDTO = new CartDTO(0, userJWT.getUserId(), p.getId(), 1);
                callAddProductToCart(userJWT.getAccessToken(), cartDTO, p);


            }
        });
    }

//    private void callProductAPI(int categoryId) {
//        productAPIRepository.getProductList(pageNo, maxRecords, "", categoryId).enqueue(new Callback<ProductReponse>() {
//            @Override
//            public void onResponse(Call<ProductReponse> call, Response<ProductReponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<ProductModel> products = response.body().getContent();
//                    if (products != null) {
//                        productListViewModel.setProductList(products);
//                        pproductListAdapter.submitList(products);
//                        Log.d("Check value", "Products size: " + products.size());
//                    }
//                } else {
//                    Log.d("ProductFragment", "Response code: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductReponse> call, Throwable t) {
//                Log.d("ProductFragment", t.getMessage());
//            }
//        });
//    }

    private void callProductAPI() {
        Log.d(categoryId.toString(), "Category Id");
        productAPIRepository.getProductList(pageNo, maxRecords, "", categoryId).enqueue(new Callback<ProductReponse>() {
            @Override
            public void onResponse(Call<ProductReponse> call, Response<ProductReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductModel> products = null;

                    try {
                        products = response.body().getContent();
                    }
                    catch (Exception e) {
                        products = null;
                    }

                    if (products != null) {
                        productListViewModel.setProductList(products);
                        pproductListAdapter.submitList(products);
                        Log.d("Check value", "Products size: " + products.size());
                    }
                } else {
                    Log.d("ProductFragment", "Response code: " + response.code());
                }
            }

            @Override

            public void onFailure(Call<ProductReponse> call, Throwable t) {
                Log.d("ProductFragment", t.getMessage());
            }
        });
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
        productListViewModel.getFilteredProductList().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                pproductListAdapter.submitList(products);
                Log.d("ProductListAdapter", "Number of items: " + products.size());
            }
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

            // Initialize category options
            de.hdodenhof.circleimageview.CircleImageView llLaptop = dialogView.findViewById(R.id.llLaptopp);
            de.hdodenhof.circleimageview.CircleImageView llDienthoai = dialogView.findViewById(R.id.llDienthoaii);
            de.hdodenhof.circleimageview.CircleImageView llBanphim = dialogView.findViewById(R.id.llBanphimm);
            de.hdodenhof.circleimageview.CircleImageView llManhinh = dialogView.findViewById(R.id.llManhinhh);

            // Initialize price options
            TextView tvPrice1 = dialogView.findViewById(R.id.tv_price1);
            TextView tvPrice2 = dialogView.findViewById(R.id.tv_price2);
            TextView tvPrice3 = dialogView.findViewById(R.id.tv_price3);
            TextView tvPrice4 = dialogView.findViewById(R.id.tv_price4);

            // Set OnClickListener for category options
            llLaptop.setOnClickListener(onCategoryClickListener);
            llDienthoai.setOnClickListener(onCategoryClickListener);
            llBanphim.setOnClickListener(onCategoryClickListener);
            llManhinh.setOnClickListener(onCategoryClickListener);

            // Set OnClickListener for price options
            tvPrice1.setOnClickListener(onPriceClickListener);
            tvPrice2.setOnClickListener(onPriceClickListener);
            tvPrice3.setOnClickListener(onPriceClickListener);
            tvPrice4.setOnClickListener(onPriceClickListener);

            // Save filter button
            dialogView.findViewById(R.id.btn_saveFillter).setOnClickListener(v1 -> {
                // Get selected category and price range
                int selectedCategoryId = getSelectedCategoryId(dialogView);
                int[] selectedPriceRange = getSelectedPriceRange(dialogView);

                // Call API to filter products
                callFilterProductAPI(selectedCategoryId, selectedPriceRange[0], selectedPriceRange[1]);
                dialog.dismiss();
            });

            dialog.show();
        });
    }

    private final View.OnClickListener onCategoryClickListener = view -> {
        // Handle category selection
        resetCategorySelection(view.getRootView());
        setActive(view);
    };

    private final View.OnClickListener onPriceClickListener = view -> {
        // Handle price selection
        resetPriceSelection(view.getRootView());
        setActive(view);
    };

    private void resetCategorySelection(View rootView) {
        setInactive(rootView.findViewById(R.id.llLaptopp));
        setInactive(rootView.findViewById(R.id.llDienthoaii));
        setInactive(rootView.findViewById(R.id.llBanphimm));
        setInactive(rootView.findViewById(R.id.llManhinhh));
    }

    private void resetPriceSelection(View rootView) {
        setInactive(rootView.findViewById(R.id.tv_price1));
        setInactive(rootView.findViewById(R.id.tv_price2));
        setInactive(rootView.findViewById(R.id.tv_price3));
        setInactive(rootView.findViewById(R.id.tv_price4));
    }

    private int getSelectedCategoryId(View rootView) {
        if (isViewActive(rootView.findViewById(R.id.llLaptopp))) return 1;
        if (isViewActive(rootView.findViewById(R.id.llDienthoaii))) return 2;
        if (isViewActive(rootView.findViewById(R.id.llBanphimm))) return 3;
        if (isViewActive(rootView.findViewById(R.id.llManhinhh))) return 4;
        return 0; // Default to all categories
    }

    private int[] getSelectedPriceRange(View rootView) {
        if (isViewActive(rootView.findViewById(R.id.tv_price1))) return new int[]{0, 1000000};
        if (isViewActive(rootView.findViewById(R.id.tv_price2))) return new int[]{0, 5000000};
        if (isViewActive(rootView.findViewById(R.id.tv_price3))) return new int[]{0, 10000000};
        if (isViewActive(rootView.findViewById(R.id.tv_price4))) return new int[]{0, 50000000};
        return new int[]{0, Integer.MAX_VALUE};
    }

    private boolean isViewActive(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            return textView.getCurrentTextColor() == Color.WHITE;
        } else if (view instanceof de.hdodenhof.circleimageview.CircleImageView) {
            return view.getBackgroundTintList() != null && view.getBackgroundTintList().getDefaultColor() == ContextCompat.getColor(view.getContext(), R.color.redPrimary);
        }
        return false;
    }

    private void callFilterProductAPI(int categoryId, int minPrice, int maxPrice) {
        productAPIRepository.getFilterProductList(categoryId, minPrice, maxPrice).enqueue(new Callback<ProductReponse>() {
            @Override
            public void onResponse(Call<ProductReponse> call, Response<ProductReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductModel> products = response.body().getContent();
                    if (products != null) {
                        products.sort(Comparator.comparingInt(ProductModel::getPrice));
                        productListViewModel.setProductList(products);
                        pproductListAdapter.submitList(products);
                        Log.d("FilterAPI", "Products size: " + products.size());
                    }
                } else {
                    Log.d("FilterAPI", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ProductReponse> call, Throwable t) {
                Log.d("FilterAPI", t.getMessage());
            }
        });
    }

    private void setActive(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(Color.WHITE);
            // Set background tint (requires API level 21+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(textView.getContext(), R.color.redPrimary)));
            }
        } else if (view instanceof de.hdodenhof.circleimageview.CircleImageView) {
            view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.redPrimary)));
        }
    }

    private void setInactive(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textView.setBackgroundTintList(null);
            }
        } else if (view instanceof de.hdodenhof.circleimageview.CircleImageView) {
            view.setBackgroundTintList(null);
        }
    }

    private int convertDpToPx(int dp) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    private void callAddProductToCart(String token, CartDTO cartDTO, ProductModel p ) {
        myProgressDialogl.show();
        cartAPIRepository.addToCart("Bearer " + token, cartDTO).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    myProgressDialogl.dismiss();
                    Toast.makeText(requireContext(), "Add Sản Phẩm Vào Giỏ Hàng Thành Công", Toast.LENGTH_SHORT).show();
                    Log.d("CheckCartRespone",String.valueOf(response.body().getId()));
                    CartResponse.Product product = new CartResponse.Product(
                            p.getId(),
                            p.getName(),
                            p.getDescription(),
                            p.getPrice(),
                            p.getQuantity(),
                            p.getImageUrl(),
                            p.getIsActive(),
                            p.getProductCategory()
                    );
                    sharedViewModel.addItem(CartModel.create(response.body().getId(),product, 1));



                } else {
                    Log.d("CheckCartRespone", String.valueOf(response.body()));
                    Log.d("CheckCartRespone", String.valueOf(response.code()));
                    Log.d("CheckCartRespone", String.valueOf(response.message()));
                    Log.d("CheckCartRespone", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

                Log.d("CheckCartRespone", String.valueOf(t.getMessage()));
            }
        });


    }

    private UserJWT getUserFromJWT() {
        String accessToken = TokenManager.getAccessToken(requireContext());
        if (accessToken != null) {
            try {
                JSONObject decodedPayload = JWTDecoder.decodeJWT(accessToken);

                UserJWT user = new UserJWT();
                user.setAccessToken(accessToken);
                user.setSubject(decodedPayload.getString("sub"));
                user.setEmail(decodedPayload.getString("email"));
                user.setUserId(decodedPayload.getInt("userId"));
                user.setRoleName(decodedPayload.getString("RoleName"));
                user.setPhone(decodedPayload.getString("phone"));
                user.setIssuedAt(decodedPayload.getLong("iat"));
                user.setExpiration(decodedPayload.getLong("exp"));

                return user;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }


}