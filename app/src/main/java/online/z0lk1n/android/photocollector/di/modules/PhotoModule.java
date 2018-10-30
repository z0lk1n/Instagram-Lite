package online.z0lk1n.android.photocollector.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.photocollector.data.model.ApiService;
import online.z0lk1n.android.photocollector.data.model.Photos;
import online.z0lk1n.android.photocollector.data.model.PhotosImpl;

@Module(includes = ApiModule.class)
public class PhotoModule {

    @Singleton
    @Provides
    public Photos providePhotos(ApiService api)   {
        return new PhotosImpl(api);
    }
}
