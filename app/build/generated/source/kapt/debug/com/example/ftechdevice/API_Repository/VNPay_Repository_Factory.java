package com.example.ftechdevice.API_Repository;

import com.example.ftechdevice.API_Service.VNPay_Service;
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
public final class VNPay_Repository_Factory implements Factory<VNPay_Repository> {
  private final Provider<VNPay_Service> vnPayServiceProvider;

  public VNPay_Repository_Factory(Provider<VNPay_Service> vnPayServiceProvider) {
    this.vnPayServiceProvider = vnPayServiceProvider;
  }

  @Override
  public VNPay_Repository get() {
    return newInstance(vnPayServiceProvider.get());
  }

  public static VNPay_Repository_Factory create(Provider<VNPay_Service> vnPayServiceProvider) {
    return new VNPay_Repository_Factory(vnPayServiceProvider);
  }

  public static VNPay_Repository newInstance(VNPay_Service vnPayService) {
    return new VNPay_Repository(vnPayService);
  }
}
