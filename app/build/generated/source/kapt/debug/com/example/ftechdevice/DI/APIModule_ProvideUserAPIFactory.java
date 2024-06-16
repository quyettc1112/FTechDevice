package com.example.ftechdevice.DI;

import com.example.ftechdevice.API_Service.UserAPI_Service;
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
public final class APIModule_ProvideUserAPIFactory implements Factory<UserAPI_Service> {
  private final Provider<String> baseUrlProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<OkHttpClient> clientProvider;

  public APIModule_ProvideUserAPIFactory(Provider<String> baseUrlProvider,
      Provider<Gson> gsonProvider, Provider<OkHttpClient> clientProvider) {
    this.baseUrlProvider = baseUrlProvider;
    this.gsonProvider = gsonProvider;
    this.clientProvider = clientProvider;
  }

  @Override
  public UserAPI_Service get() {
    return provideUserAPI(baseUrlProvider.get(), gsonProvider.get(), clientProvider.get());
  }

  public static APIModule_ProvideUserAPIFactory create(Provider<String> baseUrlProvider,
      Provider<Gson> gsonProvider, Provider<OkHttpClient> clientProvider) {
    return new APIModule_ProvideUserAPIFactory(baseUrlProvider, gsonProvider, clientProvider);
  }

  public static UserAPI_Service provideUserAPI(String baseUrl, Gson gson, OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(APIModule.provideUserAPI(baseUrl, gson, client));
  }
}
