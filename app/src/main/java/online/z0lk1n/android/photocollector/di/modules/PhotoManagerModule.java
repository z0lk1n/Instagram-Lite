package online.z0lk1n.android.photocollector.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.photocollector.util.PhotoManager;
import online.z0lk1n.android.photocollector.util.PhotoManagerImpl;

@Module
public class PhotoManagerModule {

    @Provides
    public PhotoManager providePhotoManager(Context context) {
        return new PhotoManagerImpl(context);
    }
}
