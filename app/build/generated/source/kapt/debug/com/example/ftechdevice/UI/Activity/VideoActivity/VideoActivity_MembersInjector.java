package com.example.ftechdevice.UI.Activity.VideoActivity;

import com.example.ftechdevice.API_Repository.YoutubeAPI_Repository;
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
public final class VideoActivity_MembersInjector implements MembersInjector<VideoActivity> {
  private final Provider<YoutubeAPI_Repository> youtubeapiRepositoryProvider;

  public VideoActivity_MembersInjector(
      Provider<YoutubeAPI_Repository> youtubeapiRepositoryProvider) {
    this.youtubeapiRepositoryProvider = youtubeapiRepositoryProvider;
  }

  public static MembersInjector<VideoActivity> create(
      Provider<YoutubeAPI_Repository> youtubeapiRepositoryProvider) {
    return new VideoActivity_MembersInjector(youtubeapiRepositoryProvider);
  }

  @Override
  public void injectMembers(VideoActivity instance) {
    injectYoutubeapiRepository(instance, youtubeapiRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.example.ftechdevice.UI.Activity.VideoActivity.VideoActivity.youtubeapiRepository")
  public static void injectYoutubeapiRepository(VideoActivity instance,
      YoutubeAPI_Repository youtubeapiRepository) {
    instance.youtubeapiRepository = youtubeapiRepository;
  }
}
