package com.example.ftechdevice.UI.Activity.ProductDetailActivity;

import com.example.ftechdevice.API_Repository.ProductAPI_Repository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ProductDetailActivity_MembersInjector implements MembersInjector<ProductDetailActivity> {
  private final Provider<ProductAPI_Repository> productAPIRepositoryProvider;

  public ProductDetailActivity_MembersInjector(
      Provider<ProductAPI_Repository> productAPIRepositoryProvider) {
    this.productAPIRepositoryProvider = productAPIRepositoryProvider;
  }

  public static MembersInjector<ProductDetailActivity> create(
      Provider<ProductAPI_Repository> productAPIRepositoryProvider) {
    return new ProductDetailActivity_MembersInjector(productAPIRepositoryProvider);
  }

  @Override
  public void injectMembers(ProductDetailActivity instance) {
    injectProductAPIRepository(instance, productAPIRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.example.ftechdevice.UI.Activity.ProductDetailActivity.ProductDetailActivity.productAPIRepository")
  public static void injectProductAPIRepository(ProductDetailActivity instance,
      ProductAPI_Repository productAPIRepository) {
    instance.productAPIRepository = productAPIRepository;
  }
}
