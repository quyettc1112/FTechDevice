package com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity;

import com.example.ftechdevice.API_Repository.UserAPI_Repository;
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
public final class LoginActivityScreen2_MembersInjector implements MembersInjector<LoginActivityScreen2> {
  private final Provider<UserAPI_Repository> userapiRepositoryProvider;

  public LoginActivityScreen2_MembersInjector(
      Provider<UserAPI_Repository> userapiRepositoryProvider) {
    this.userapiRepositoryProvider = userapiRepositoryProvider;
  }

  public static MembersInjector<LoginActivityScreen2> create(
      Provider<UserAPI_Repository> userapiRepositoryProvider) {
    return new LoginActivityScreen2_MembersInjector(userapiRepositoryProvider);
  }

  @Override
  public void injectMembers(LoginActivityScreen2 instance) {
    injectUserapiRepository(instance, userapiRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivityScreen2.userapiRepository")
  public static void injectUserapiRepository(LoginActivityScreen2 instance,
      UserAPI_Repository userapiRepository) {
    instance.userapiRepository = userapiRepository;
  }
}
