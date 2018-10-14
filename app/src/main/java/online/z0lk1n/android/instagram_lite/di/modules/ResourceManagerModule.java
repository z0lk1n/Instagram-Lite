package online.z0lk1n.android.instagram_lite.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.instagram_lite.util.ResourceManager;
import online.z0lk1n.android.instagram_lite.util.ResourceManagerImpl;

@Module
public class ResourceManagerModule {

    @Provides
    public ResourceManager provideResourceManager(Context context) {
        return new ResourceManagerImpl(context);
    }
}
