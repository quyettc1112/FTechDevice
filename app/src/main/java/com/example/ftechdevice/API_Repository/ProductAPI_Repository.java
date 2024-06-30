package com.example.ftechdevice.API_Repository;

import com.example.ftechdevice.API_Service.ProductAPI_Service;
import com.example.ftechdevice.Model.ModelRespone.ProductReponse;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import retrofit2.Call;

@ActivityScoped
public class ProductAPI_Repository {
    private final ProductAPI_Service productapiService;

    @Inject
    public ProductAPI_Repository(ProductAPI_Service productapiService) {
        this.productapiService = productapiService;
    }
    public Call<ProductReponse> getProductList(int pageNo, int pageSize, String productName, int categoryId) {
        return productapiService.getAllProduct(pageNo, pageSize, productName, categoryId);
    }

    public  Call<ProductReponse> getProductById(int id ){
        return productapiService.getProductById(id);
    }
}
