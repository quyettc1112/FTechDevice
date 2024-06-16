package com.example.ftechdevice.API_Repository;

import com.example.ftechdevice.API_Service.UserAPI_Service;
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
public final class UserAPI_Repository_Factory implements Factory<UserAPI_Repository> {
  private final Provider<UserAPI_Service> userapiServiceProvider;

  public UserAPI_Repository_Factory(Provider<UserAPI_Service> userapiServiceProvider) {
    this.userapiServiceProvider = userapiServiceProvider;
  }

  @Override
  public UserAPI_Repository get() {
    return newInstance(userapiServiceProvider.get());
  }

  public static UserAPI_Repository_Factory create(
      Provider<UserAPI_Service> userapiServiceProvider) {
    return new UserAPI_Repository_Factory(userapiServiceProvider);
  }

  public static UserAPI_Repository newInstance(UserAPI_Service userapiService) {
    return new UserAPI_Repository(userapiService);
  }
}
