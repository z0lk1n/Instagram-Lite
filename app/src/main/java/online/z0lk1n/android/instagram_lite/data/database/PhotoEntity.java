package online.z0lk1n.android.instagram_lite.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PhotoEntity {
    @PrimaryKey
    private String photoPath;
    private boolean isFavorites;
    private String Url;

}
