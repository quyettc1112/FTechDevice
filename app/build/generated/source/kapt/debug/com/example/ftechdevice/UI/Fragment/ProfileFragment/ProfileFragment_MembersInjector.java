package com.example.ftechdevice.UI.Fragment.ProfileFragment;

import com.example.ftechdevice.API_Service.UserAPI_Service;
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
public final class ProfileFragment_MembersInjector implements MembersInjector<ProfileFragment> {
  private final Provider<UserAPI_Service> userApiServiceProvider;

  public ProfileFragment_MembersInjector(Provider<UserAPI_Service> userApiServiceProvider) {
    this.userApiServiceProvider = userApiServiceProvider;
  }

  public static MembersInjector<ProfileFragment> create(
      Provider<UserAPI_Service> userApiServiceProvider) {
    return new ProfileFragment_MembersInjector(userApiServiceProvider);
  }

  @Override
  public void injectMembers(ProfileFragment instance) {
    injectUserApiService(instance, userApiServiceProvider.get());
  }

  @InjectedFieldSignature("com.example.ftechdevice.UI.Fragment.ProfileFragment.ProfileFragment.userApiService")
  public static void injectUserApiService(ProfileFragment instance,
      UserAPI_Service userApiService) {
    instance.userApiService = userApiService;
  }
}
