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
public final class APIModule_ProvideUserBaseUrlFactory implements Factory<String> {
  @Override
  public String get() {
    return provideUserBaseUrl();
  }

  public static APIModule_ProvideUserBaseUrlFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provideUserBaseUrl() {
    return Preconditions.checkNotNullFromProvides(APIModule.provideUserBaseUrl());
  }

  private static final class InstanceHolder {
    private static final APIModule_ProvideUserBaseUrlFactory INSTANCE = new APIModule_ProvideUserBaseUrlFactory();
  }
}
