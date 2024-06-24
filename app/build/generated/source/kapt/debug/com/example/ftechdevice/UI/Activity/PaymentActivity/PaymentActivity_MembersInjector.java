package com.example.ftechdevice.UI.Activity.PaymentActivity;

import com.example.ftechdevice.API_Repository.VNPay_Repository;
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
public final class PaymentActivity_MembersInjector implements MembersInjector<PaymentActivity> {
  private final Provider<VNPay_Repository> vnPayRepositoryProvider;

  public PaymentActivity_MembersInjector(Provider<VNPay_Repository> vnPayRepositoryProvider) {
    this.vnPayRepositoryProvider = vnPayRepositoryProvider;
  }

  public static MembersInjector<PaymentActivity> create(
      Provider<VNPay_Repository> vnPayRepositoryProvider) {
    return new PaymentActivity_MembersInjector(vnPayRepositoryProvider);
  }

  @Override
  public void injectMembers(PaymentActivity instance) {
    injectVnPayRepository(instance, vnPayRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.example.ftechdevice.UI.Activity.PaymentActivity.PaymentActivity.vnPayRepository")
  public static void injectVnPayRepository(PaymentActivity instance,
      VNPay_Repository vnPayRepository) {
    instance.vnPayRepository = vnPayRepository;
  }
}
