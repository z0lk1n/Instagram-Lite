package online.z0lk1n.android.instagram_lite.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepository;
import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepositoryImpl;

@Module(includes = DatabaseModule.class)
public class RepositoryModule {

    @Singleton
    @Provides
    public PhotoRepository providePhotoRepository() {
        return new PhotoRepositoryImpl();
    }
}
