package online.z0lk1n.android.photocollector.di.modules;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.photocollector.data.model.Photos;

@Module
public class TestPhotosModule {

    @Singleton
    @Provides
    public Photos providePhotos() {
        return Mockito.mock(Photos.class);
    }
}
