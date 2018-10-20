package online.z0lk1n.android.photocollector.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.photocollector.util.SchedulersProvider;
import online.z0lk1n.android.photocollector.util.SchedulersProviderImpl;

@Module
public class SchedulersModule {

    @Singleton
    @Provides
    public SchedulersProvider provideSchedulersProvider() {
        return new SchedulersProviderImpl();
    }
}
