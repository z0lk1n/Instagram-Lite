package online.z0lk1n.android.instagram_lite.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PhotoEntity.class}, version = 1)
public abstract class PhotoDatabase extends RoomDatabase {

    public abstract PhotoDAO photoDAO();
}
