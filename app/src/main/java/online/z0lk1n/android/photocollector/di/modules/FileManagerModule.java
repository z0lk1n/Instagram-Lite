package online.z0lk1n.android.photocollector.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.photocollector.util.FileManager;
import online.z0lk1n.android.photocollector.util.FileManagerImpl;

@Module
public class FileManagerModule {

    @Singleton
    @Provides
    public FileManager provideFileManager(Context context)    {
        return new FileManagerImpl(context);
    }
}
