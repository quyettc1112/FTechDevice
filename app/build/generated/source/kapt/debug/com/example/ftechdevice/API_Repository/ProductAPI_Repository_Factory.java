package com.example.ftechdevice.API_Repository;

import com.example.ftechdevice.API_Service.ProductAPI_Service;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("dagger.hilt.android.scopes.ActivityScoped")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ProductAPI_Repository_Factory implements Factory<ProductAPI_Repository> {
  private final Provider<ProductAPI_Service> productapiServiceProvider;

  public ProductAPI_Repository_Factory(Provider<ProductAPI_Service> productapiServiceProvider) {
    this.productapiServiceProvider = productapiServiceProvider;
  }

  @Override
  public ProductAPI_Repository get() {
    return newInstance(productapiServiceProvider.get());
  }

  public static ProductAPI_Repository_Factory create(
      Provider<ProductAPI_Service> productapiServiceProvider) {
    return new ProductAPI_Repository_Factory(productapiServiceProvider);
  }

  public static ProductAPI_Repository newInstance(ProductAPI_Service productapiService) {
    return new ProductAPI_Repository(productapiService);
  }
}
