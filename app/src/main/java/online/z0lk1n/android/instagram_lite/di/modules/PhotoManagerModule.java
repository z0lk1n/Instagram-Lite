package online.z0lk1n.android.instagram_lite.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;
import online.z0lk1n.android.instagram_lite.util.PhotoManagerImpl;

@Module
public class PhotoManagerModule {

    @Provides
    public PhotoManager providePhotoManager(Context context) {
        return new PhotoManagerImpl(context);
    }
}
