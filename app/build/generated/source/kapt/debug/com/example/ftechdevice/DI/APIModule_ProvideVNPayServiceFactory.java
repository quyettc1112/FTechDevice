package com.example.ftechdevice.DI;

import com.example.ftechdevice.API_Service.VNPay_Service;
import com.google.gson.Gson;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("javax.inject.Named")
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
public final class APIModule_ProvideVNPayServiceFactory implements Factory<VNPay_Service> {
  private final Provider<String> baseUrlProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<OkHttpClient> clientProvider;

  public APIModule_ProvideVNPayServiceFactory(Provider<String> baseUrlProvider,
      Provider<Gson> gsonProvider, Provider<OkHttpClient> clientProvider) {
    this.baseUrlProvider = baseUrlProvider;
    this.gsonProvider = gsonProvider;
    this.clientProvider = clientProvider;
  }

  @Override
  public VNPay_Service get() {
    return provideVNPayService(baseUrlProvider.get(), gsonProvider.get(), clientProvider.get());
  }

  public static APIModule_ProvideVNPayServiceFactory create(Provider<String> baseUrlProvider,
      Provider<Gson> gsonProvider, Provider<OkHttpClient> clientProvider) {
    return new APIModule_ProvideVNPayServiceFactory(baseUrlProvider, gsonProvider, clientProvider);
  }

  public static VNPay_Service provideVNPayService(String baseUrl, Gson gson, OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(APIModule.provideVNPayService(baseUrl, gson, client));
  }
}
