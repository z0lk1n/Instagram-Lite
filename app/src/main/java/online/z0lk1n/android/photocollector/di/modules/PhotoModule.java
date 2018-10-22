package online.z0lk1n.android.photocollector.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.photocollector.data.model.ApiService;
import online.z0lk1n.android.photocollector.data.model.Photo;
import online.z0lk1n.android.photocollector.data.model.PhotoImpl;

@Module(includes = ApiModule.class)
public class PhotoModule {

    @Singleton
    @Provides
    public Photo providePhoto(ApiService api)   {
        return new PhotoImpl(api);
    }
}
