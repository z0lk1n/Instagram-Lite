package online.z0lk1n.android.instagram_lite.model;

import android.net.Uri;

public class PhotoItem {
    private long id;
    private Uri photo;
    private boolean isFavorites;

    public PhotoItem(Uri photo, boolean isFavorites) {
        this.photo = photo;
        this.isFavorites = isFavorites;
    }

    public Uri getPhoto() {
        return photo;
    }

    public boolean isFavorites() {
        return isFavorites;
    }
}
