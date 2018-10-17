package online.z0lk1n.android.instagram_lite.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.instagram_lite.data.database.PhotoDatabase;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    public PhotoDatabase providePhotoDatabase(Context context) {
        return Room.databaseBuilder(context, PhotoDatabase.class, "photo_db").build();
    }
}
