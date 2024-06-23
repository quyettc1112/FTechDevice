package com.example.ftechdevice.UI.Fragment.ProductFragment;

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
public final class ProductFragment_MembersInjector implements MembersInjector<ProductFragment> {
  private final Provider<ProductAPI_Repository> productAPIRepositoryProvider;

  public ProductFragment_MembersInjector(
      Provider<ProductAPI_Repository> productAPIRepositoryProvider) {
    this.productAPIRepositoryProvider = productAPIRepositoryProvider;
  }

  public static MembersInjector<ProductFragment> create(
      Provider<ProductAPI_Repository> productAPIRepositoryProvider) {
    return new ProductFragment_MembersInjector(productAPIRepositoryProvider);
  }

  @Override
  public void injectMembers(ProductFragment instance) {
    injectProductAPIRepository(instance, productAPIRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.example.ftechdevice.UI.Fragment.ProductFragment.ProductFragment.productAPIRepository")
  public static void injectProductAPIRepository(ProductFragment instance,
      ProductAPI_Repository productAPIRepository) {
    instance.productAPIRepository = productAPIRepository;
  }
}
