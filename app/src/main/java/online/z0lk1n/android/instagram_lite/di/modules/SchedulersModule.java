package online.z0lk1n.android.instagram_lite.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.instagram_lite.util.SchedulersProvider;
import online.z0lk1n.android.instagram_lite.util.SchedulersProviderImpl;

@Module
public class SchedulersModule {

    @Singleton
    @Provides
    public SchedulersProvider provideSchedulersProvider() {
        return new SchedulersProviderImpl();
    }
}
