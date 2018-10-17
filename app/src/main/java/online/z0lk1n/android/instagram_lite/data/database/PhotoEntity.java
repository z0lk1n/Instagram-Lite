package online.z0lk1n.android.instagram_lite.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"photo_path"}, unique = true)})
public class PhotoEntity {

    @PrimaryKey
    @ColumnInfo(name = "photo_path")
    private String photoPath;

    @ColumnInfo(name = "is_remote")
    private boolean isRemote;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    public PhotoEntity() {
    }

    public PhotoEntity(String photoPath, boolean isRemote, boolean isFavorite) {
        this.photoPath = photoPath;
        this.isRemote = isRemote;
        this.isFavorite = isFavorite;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public void setRemote(boolean remote) {
        isRemote = remote;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
