package com.example.ftechdevice.API_Service;

import com.example.ftechdevice.Model.ModelRespone.ProductReponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductAPI_Service {

    @GET("/api/product")
    Call<ProductReponse> getAllProduct(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize,
            @Query("productName") String productName,
            @Query("categoryId") int categoryId
    );

    @GET("/api/product/{id}")
    Call<ProductReponse> getProductById(
            @Path("id") int id
    );

    @GET("/api/product")
    Call<ProductReponse> getFilterProduct(
            @Query("categoryId") int categoryId,
            @Query("minPrice") int minPrice,
            @Query("maxPrice") int maxPrice

    );
}
