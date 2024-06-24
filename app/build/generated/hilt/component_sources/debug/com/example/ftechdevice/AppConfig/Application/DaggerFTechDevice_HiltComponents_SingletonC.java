package com.example.ftechdevice.AppConfig.Application;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.ftechdevice.API_Repository.ProductAPI_Repository;
import com.example.ftechdevice.API_Repository.UserAPI_Repository;
import com.example.ftechdevice.API_Repository.VNPay_Repository;
import com.example.ftechdevice.API_Repository.YoutubeAPI_Repository;
import com.example.ftechdevice.API_Service.ProductAPI_Service;
import com.example.ftechdevice.API_Service.UserAPI_Service;
import com.example.ftechdevice.API_Service.VNPay_Service;
import com.example.ftechdevice.API_Service.YoutubeAPI_Service;
import com.example.ftechdevice.DI.APIModule;
import com.example.ftechdevice.DI.APIModule_ProvideBaseUrlFactory;
import com.example.ftechdevice.DI.APIModule_ProvideGsonFactory;
import com.example.ftechdevice.DI.APIModule_ProvideOkHttpClientFactory;
import com.example.ftechdevice.DI.APIModule_ProvideProductAPIFactory;
import com.example.ftechdevice.DI.APIModule_ProvideUserAPIFactory;
import com.example.ftechdevice.DI.APIModule_ProvideVNPayServiceFactory;
import com.example.ftechdevice.DI.APIModule_ProvideYoutubeAPIFactory;
import com.example.ftechdevice.DI.APIModule_ProvideYoutubeBaseUrlFactory;
import com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivity;
import com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivityScreen2;
import com.example.ftechdevice.UI.Activity.AuthActivity.LoginActivity.LoginActivityScreen2_MembersInjector;
import com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity.RegisterActivity;
import com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity.RegisterActivity_Screen2;
import com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity.RegisterActivity_Screen3;
import com.example.ftechdevice.UI.Activity.AuthActivity.RegisterActivity.RegisterActivity_Screen3_MembersInjector;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.example.ftechdevice.UI.Activity.PaymentActivity.PaymentActivity;
import com.example.ftechdevice.UI.Activity.PaymentActivity.PaymentActivity_MembersInjector;
import com.example.ftechdevice.UI.Activity.ProductDetailActivity.ProductDetailActivity;
import com.example.ftechdevice.UI.Activity.SplashActivity.SplashActivity;
import com.example.ftechdevice.UI.Activity.StartActivity.StartActivity;
import com.example.ftechdevice.UI.Activity.VideoActivity.VideoActivity;
import com.example.ftechdevice.UI.Activity.VideoActivity.VideoActivity_MembersInjector;
import com.example.ftechdevice.UI.Fragment.HomeFragment.HomeFragment;
import com.example.ftechdevice.UI.Fragment.HomeFragment.HomeFragment_MembersInjector;
import com.example.ftechdevice.UI.Fragment.ProductFragment.ProductFragment;
import com.example.ftechdevice.UI.Fragment.ProductFragment.ProductViewModel;
import com.example.ftechdevice.UI.Fragment.ProductFragment.ProductViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.ftechdevice.UI.ShareViewModel.RegisterViewModel;
import com.example.ftechdevice.UI.ShareViewModel.RegisterViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.ftechdevice.UI.ShareViewModel.ShareViewModel;
import com.example.ftechdevice.UI.ShareViewModel.ShareViewModel_HiltModules_KeyModule_ProvideFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class DaggerFTechDevice_HiltComponents_SingletonC {
  private DaggerFTechDevice_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static FTechDevice_HiltComponents.SingletonC create() {
    return new Builder().build();
  }

  public static final class Builder {
    private Builder() {
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder aPIModule(APIModule aPIModule) {
      Preconditions.checkNotNull(aPIModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule(
        HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule) {
      Preconditions.checkNotNull(hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule);
      return this;
    }

    public FTechDevice_HiltComponents.SingletonC build() {
      return new SingletonCImpl();
    }
  }

  private static final class ActivityRetainedCBuilder implements FTechDevice_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public FTechDevice_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl(singletonCImpl);
    }
  }

  private static final class ActivityCBuilder implements FTechDevice_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public FTechDevice_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements FTechDevice_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public FTechDevice_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements FTechDevice_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public FTechDevice_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements FTechDevice_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public FTechDevice_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements FTechDevice_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public FTechDevice_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements FTechDevice_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public FTechDevice_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends FTechDevice_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends FTechDevice_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public void injectHomeFragment(HomeFragment arg0) {
      injectHomeFragment2(arg0);
    }

    @Override
    public void injectProductFragment(ProductFragment arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }

    @CanIgnoreReturnValue
    private HomeFragment injectHomeFragment2(HomeFragment instance) {
      HomeFragment_MembersInjector.injectProductAPIRepository(instance, activityCImpl.productAPI_RepositoryProvider.get());
      return instance;
    }
  }

  private static final class ViewCImpl extends FTechDevice_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends FTechDevice_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private Provider<UserAPI_Repository> userAPI_RepositoryProvider;

    private Provider<VNPay_Repository> vNPay_RepositoryProvider;

    private Provider<YoutubeAPI_Repository> youtubeAPI_RepositoryProvider;

    private Provider<ProductAPI_Repository> productAPI_RepositoryProvider;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(activityParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final Activity activityParam) {
      this.userAPI_RepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UserAPI_Repository>(singletonCImpl, activityRetainedCImpl, activityCImpl, 0));
      this.vNPay_RepositoryProvider = DoubleCheck.provider(new SwitchingProvider<VNPay_Repository>(singletonCImpl, activityRetainedCImpl, activityCImpl, 1));
      this.youtubeAPI_RepositoryProvider = DoubleCheck.provider(new SwitchingProvider<YoutubeAPI_Repository>(singletonCImpl, activityRetainedCImpl, activityCImpl, 2));
      this.productAPI_RepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ProductAPI_Repository>(singletonCImpl, activityRetainedCImpl, activityCImpl, 3));
    }

    @Override
    public void injectLoginActivityScreen2(LoginActivityScreen2 arg0) {
      injectLoginActivityScreen22(arg0);
    }

    @Override
    public void injectLoginActivity(LoginActivity arg0) {
    }

    @Override
    public void injectRegisterActivity(RegisterActivity arg0) {
    }

    @Override
    public void injectRegisterActivity_Screen2(RegisterActivity_Screen2 arg0) {
    }

    @Override
    public void injectRegisterActivity_Screen3(RegisterActivity_Screen3 arg0) {
      injectRegisterActivity_Screen32(arg0);
    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public void injectPaymentActivity(PaymentActivity arg0) {
      injectPaymentActivity2(arg0);
    }

    @Override
    public void injectProductDetailActivity(ProductDetailActivity arg0) {
    }

    @Override
    public void injectSplashActivity(SplashActivity arg0) {
    }

    @Override
    public void injectStartActivity(StartActivity arg0) {
    }

    @Override
    public void injectVideoActivity(VideoActivity arg0) {
      injectVideoActivity2(arg0);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(ProductViewModel_HiltModules_KeyModule_ProvideFactory.provide(), RegisterViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ShareViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private LoginActivityScreen2 injectLoginActivityScreen22(LoginActivityScreen2 instance) {
      LoginActivityScreen2_MembersInjector.injectUserapiRepository(instance, userAPI_RepositoryProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RegisterActivity_Screen3 injectRegisterActivity_Screen32(
        RegisterActivity_Screen3 instance) {
      RegisterActivity_Screen3_MembersInjector.injectUserapiRepository(instance, userAPI_RepositoryProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private PaymentActivity injectPaymentActivity2(PaymentActivity instance) {
      PaymentActivity_MembersInjector.injectVnPayRepository(instance, vNPay_RepositoryProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private VideoActivity injectVideoActivity2(VideoActivity instance) {
      VideoActivity_MembersInjector.injectYoutubeapiRepository(instance, youtubeAPI_RepositoryProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ActivityCImpl activityCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ActivityCImpl activityCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.activityCImpl = activityCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.ftechdevice.API_Repository.UserAPI_Repository 
          return (T) new UserAPI_Repository(singletonCImpl.provideUserAPIProvider.get());

          case 1: // com.example.ftechdevice.API_Repository.VNPay_Repository 
          return (T) new VNPay_Repository(singletonCImpl.provideVNPayServiceProvider.get());

          case 2: // com.example.ftechdevice.API_Repository.YoutubeAPI_Repository 
          return (T) new YoutubeAPI_Repository(singletonCImpl.provideYoutubeAPIProvider.get());

          case 3: // com.example.ftechdevice.API_Repository.ProductAPI_Repository 
          return (T) new ProductAPI_Repository(singletonCImpl.provideProductAPIProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ViewModelCImpl extends FTechDevice_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<ProductViewModel> productViewModelProvider;

    private Provider<RegisterViewModel> registerViewModelProvider;

    private Provider<ShareViewModel> shareViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.productViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.registerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.shareViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
    }

    @Override
    public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, Provider<ViewModel>>of("com.example.ftechdevice.UI.Fragment.ProductFragment.ProductViewModel", ((Provider) productViewModelProvider), "com.example.ftechdevice.UI.ShareViewModel.RegisterViewModel", ((Provider) registerViewModelProvider), "com.example.ftechdevice.UI.ShareViewModel.ShareViewModel", ((Provider) shareViewModelProvider));
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.ftechdevice.UI.Fragment.ProductFragment.ProductViewModel 
          return (T) new ProductViewModel();

          case 1: // com.example.ftechdevice.UI.ShareViewModel.RegisterViewModel 
          return (T) new RegisterViewModel();

          case 2: // com.example.ftechdevice.UI.ShareViewModel.ShareViewModel 
          return (T) new ShareViewModel();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends FTechDevice_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends FTechDevice_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends FTechDevice_HiltComponents.SingletonC {
    private final SingletonCImpl singletonCImpl = this;

    private Provider<String> provideBaseUrlProvider;

    private Provider<Gson> provideGsonProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<UserAPI_Service> provideUserAPIProvider;

    private Provider<VNPay_Service> provideVNPayServiceProvider;

    private Provider<String> provideYoutubeBaseUrlProvider;

    private Provider<YoutubeAPI_Service> provideYoutubeAPIProvider;

    private Provider<ProductAPI_Service> provideProductAPIProvider;

    private SingletonCImpl() {

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideBaseUrlProvider = DoubleCheck.provider(new SwitchingProvider<String>(singletonCImpl, 1));
      this.provideGsonProvider = DoubleCheck.provider(new SwitchingProvider<Gson>(singletonCImpl, 2));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 3));
      this.provideUserAPIProvider = DoubleCheck.provider(new SwitchingProvider<UserAPI_Service>(singletonCImpl, 0));
      this.provideVNPayServiceProvider = DoubleCheck.provider(new SwitchingProvider<VNPay_Service>(singletonCImpl, 4));
      this.provideYoutubeBaseUrlProvider = DoubleCheck.provider(new SwitchingProvider<String>(singletonCImpl, 6));
      this.provideYoutubeAPIProvider = DoubleCheck.provider(new SwitchingProvider<YoutubeAPI_Service>(singletonCImpl, 5));
      this.provideProductAPIProvider = DoubleCheck.provider(new SwitchingProvider<ProductAPI_Service>(singletonCImpl, 7));
    }

    @Override
    public void injectFTechDevice(FTechDevice fTechDevice) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.ftechdevice.API_Service.UserAPI_Service 
          return (T) APIModule_ProvideUserAPIFactory.provideUserAPI(singletonCImpl.provideBaseUrlProvider.get(), singletonCImpl.provideGsonProvider.get(), singletonCImpl.provideOkHttpClientProvider.get());

          case 1: // @javax.inject.Named("base") java.lang.String 
          return (T) APIModule_ProvideBaseUrlFactory.provideBaseUrl();

          case 2: // com.google.gson.Gson 
          return (T) APIModule_ProvideGsonFactory.provideGson();

          case 3: // okhttp3.OkHttpClient 
          return (T) APIModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 4: // com.example.ftechdevice.API_Service.VNPay_Service 
          return (T) APIModule_ProvideVNPayServiceFactory.provideVNPayService(singletonCImpl.provideBaseUrlProvider.get(), singletonCImpl.provideGsonProvider.get(), singletonCImpl.provideOkHttpClientProvider.get());

          case 5: // com.example.ftechdevice.API_Service.YoutubeAPI_Service 
          return (T) APIModule_ProvideYoutubeAPIFactory.provideYoutubeAPI(singletonCImpl.provideYoutubeBaseUrlProvider.get(), singletonCImpl.provideGsonProvider.get(), singletonCImpl.provideOkHttpClientProvider.get());

          case 6: // @javax.inject.Named("youtube") java.lang.String 
          return (T) APIModule_ProvideYoutubeBaseUrlFactory.provideYoutubeBaseUrl();

          case 7: // com.example.ftechdevice.API_Service.ProductAPI_Service 
          return (T) APIModule_ProvideProductAPIFactory.provideProductAPI(singletonCImpl.provideBaseUrlProvider.get(), singletonCImpl.provideGsonProvider.get(), singletonCImpl.provideOkHttpClientProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
