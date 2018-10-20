package online.z0lk1n.android.photocollector.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.photocollector.util.ResourceManager;
import online.z0lk1n.android.photocollector.util.ResourceManagerImpl;

@Module
public class ResourcesModule {

    @Provides
    public ResourceManager provideResourceManager(Context context) {
        return new ResourceManagerImpl(context);
    }
}
