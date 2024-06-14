package com.example.ftechdevice.DI;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class APIModule_ProvideConnectionTimeoutFactory implements Factory<Integer> {
  @Override
  public Integer get() {
    return provideConnectionTimeout();
  }

  public static APIModule_ProvideConnectionTimeoutFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static int provideConnectionTimeout() {
    return APIModule.provideConnectionTimeout();
  }

  private static final class InstanceHolder {
    private static final APIModule_ProvideConnectionTimeoutFactory INSTANCE = new APIModule_ProvideConnectionTimeoutFactory();
  }
}
