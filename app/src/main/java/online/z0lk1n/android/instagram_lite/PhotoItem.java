package online.z0lk1n.android.instagram_lite;

import android.graphics.Bitmap;

public class PhotoItem {
    private long id;
    private String path;
    private Bitmap photo;
    private boolean isFavorites;

    public PhotoItem(String path, Bitmap photo, boolean isFavorites) {
        this.id = id;
        this.path = path;
        this.photo = photo;
        this.isFavorites = isFavorites;
    }

    public long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public boolean isFavorites() {
        return isFavorites;
    }
}
