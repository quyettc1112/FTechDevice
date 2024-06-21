package com.example.ftechdevice.DI;

import com.example.ftechdevice.API_Service.ProductAPI_Service;
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
public final class APIModule_ProvideProductAPIFactory implements Factory<ProductAPI_Service> {
  private final Provider<String> baseUrlProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<OkHttpClient> clientProvider;

  public APIModule_ProvideProductAPIFactory(Provider<String> baseUrlProvider,
      Provider<Gson> gsonProvider, Provider<OkHttpClient> clientProvider) {
    this.baseUrlProvider = baseUrlProvider;
    this.gsonProvider = gsonProvider;
    this.clientProvider = clientProvider;
  }

  @Override
  public ProductAPI_Service get() {
    return provideProductAPI(baseUrlProvider.get(), gsonProvider.get(), clientProvider.get());
  }

  public static APIModule_ProvideProductAPIFactory create(Provider<String> baseUrlProvider,
      Provider<Gson> gsonProvider, Provider<OkHttpClient> clientProvider) {
    return new APIModule_ProvideProductAPIFactory(baseUrlProvider, gsonProvider, clientProvider);
  }

  public static ProductAPI_Service provideProductAPI(String baseUrl, Gson gson,
      OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(APIModule.provideProductAPI(baseUrl, gson, client));
  }
}
