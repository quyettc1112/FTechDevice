package com.example.ftechdevice.UI.Fragment.HomeFragment;

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
public final class HomeFragment_MembersInjector implements MembersInjector<HomeFragment> {
  private final Provider<ProductAPI_Repository> productAPIRepositoryProvider;

  public HomeFragment_MembersInjector(
      Provider<ProductAPI_Repository> productAPIRepositoryProvider) {
    this.productAPIRepositoryProvider = productAPIRepositoryProvider;
  }

  public static MembersInjector<HomeFragment> create(
      Provider<ProductAPI_Repository> productAPIRepositoryProvider) {
    return new HomeFragment_MembersInjector(productAPIRepositoryProvider);
  }

  @Override
  public void injectMembers(HomeFragment instance) {
    injectProductAPIRepository(instance, productAPIRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.example.ftechdevice.UI.Fragment.HomeFragment.HomeFragment.productAPIRepository")
  public static void injectProductAPIRepository(HomeFragment instance,
      ProductAPI_Repository productAPIRepository) {
    instance.productAPIRepository = productAPIRepository;
  }
}
