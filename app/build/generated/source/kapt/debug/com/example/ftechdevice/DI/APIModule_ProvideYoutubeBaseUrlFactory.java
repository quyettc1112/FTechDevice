package com.example.ftechdevice.DI;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class APIModule_ProvideYoutubeBaseUrlFactory implements Factory<String> {
  @Override
  public String get() {
    return provideYoutubeBaseUrl();
  }

  public static APIModule_ProvideYoutubeBaseUrlFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provideYoutubeBaseUrl() {
    return Preconditions.checkNotNullFromProvides(APIModule.provideYoutubeBaseUrl());
  }

  private static final class InstanceHolder {
    private static final APIModule_ProvideYoutubeBaseUrlFactory INSTANCE = new APIModule_ProvideYoutubeBaseUrlFactory();
  }
}
