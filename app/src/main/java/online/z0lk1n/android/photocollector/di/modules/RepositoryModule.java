package online.z0lk1n.android.photocollector.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.photocollector.data.database.PhotoDatabase;
import online.z0lk1n.android.photocollector.data.repositories.PhotoRepository;
import online.z0lk1n.android.photocollector.data.repositories.PhotoRepositoryImpl;

@Module(includes = DatabaseModule.class)
public class RepositoryModule {

    @Singleton
    @Provides
    public PhotoRepository providePhotoRepository(PhotoDatabase photoDatabase) {
        return new PhotoRepositoryImpl(photoDatabase);
    }
}
