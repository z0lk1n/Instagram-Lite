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
    @ColumnInfo(name = "favorites")
    private boolean isFavorites;
    @ColumnInfo(name = "url")
    private String Url;

    public PhotoEntity() {
    }

    public PhotoEntity(String photoPath, boolean isFavorites, String url) {
        this.photoPath = photoPath;
        this.isFavorites = isFavorites;
        Url = url;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean isFavorites() {
        return isFavorites;
    }

    public void setFavorites(boolean favorites) {
        isFavorites = favorites;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
