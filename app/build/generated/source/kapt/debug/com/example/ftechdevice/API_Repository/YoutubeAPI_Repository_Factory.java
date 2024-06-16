package com.example.ftechdevice.API_Repository;

import com.example.ftechdevice.API_Service.YoutubeAPI_Service;
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
public final class YoutubeAPI_Repository_Factory implements Factory<YoutubeAPI_Repository> {
  private final Provider<YoutubeAPI_Service> youtubeapiServiceProvider;

  public YoutubeAPI_Repository_Factory(Provider<YoutubeAPI_Service> youtubeapiServiceProvider) {
    this.youtubeapiServiceProvider = youtubeapiServiceProvider;
  }

  @Override
  public YoutubeAPI_Repository get() {
    return newInstance(youtubeapiServiceProvider.get());
  }

  public static YoutubeAPI_Repository_Factory create(
      Provider<YoutubeAPI_Service> youtubeapiServiceProvider) {
    return new YoutubeAPI_Repository_Factory(youtubeapiServiceProvider);
  }

  public static YoutubeAPI_Repository newInstance(YoutubeAPI_Service youtubeapiService) {
    return new YoutubeAPI_Repository(youtubeapiService);
  }
}
