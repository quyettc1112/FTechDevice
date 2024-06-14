package com.example.ftechdevice.UI.ShareViewModel;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class ShareViewModel_Factory implements Factory<ShareViewModel> {
  @Override
  public ShareViewModel get() {
    return newInstance();
  }

  public static ShareViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ShareViewModel newInstance() {
    return new ShareViewModel();
  }

  private static final class InstanceHolder {
    private static final ShareViewModel_Factory INSTANCE = new ShareViewModel_Factory();
  }
}
