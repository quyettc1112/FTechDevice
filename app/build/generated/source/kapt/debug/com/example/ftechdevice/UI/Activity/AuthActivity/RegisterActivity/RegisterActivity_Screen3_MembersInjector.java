package com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity;

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
public final class RegisterActivity_Screen3_MembersInjector implements MembersInjector<RegisterActivity_Screen3> {
  private final Provider<UserAPI_Repository> userapiRepositoryProvider;

  public RegisterActivity_Screen3_MembersInjector(
      Provider<UserAPI_Repository> userapiRepositoryProvider) {
    this.userapiRepositoryProvider = userapiRepositoryProvider;
  }

  public static MembersInjector<RegisterActivity_Screen3> create(
      Provider<UserAPI_Repository> userapiRepositoryProvider) {
    return new RegisterActivity_Screen3_MembersInjector(userapiRepositoryProvider);
  }

  @Override
  public void injectMembers(RegisterActivity_Screen3 instance) {
    injectUserapiRepository(instance, userapiRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity.RegisterActivity_Screen3.userapiRepository")
  public static void injectUserapiRepository(RegisterActivity_Screen3 instance,
      UserAPI_Repository userapiRepository) {
    instance.userapiRepository = userapiRepository;
  }
}
