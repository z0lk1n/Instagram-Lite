package online.z0lk1n.android.instagram_lite.data.model;

import org.jetbrains.annotations.Contract;

public final class PhotoItem {

    private final String photoPath;
    private boolean isFavorites;

    public PhotoItem(String photoPath) {
        this.photoPath = photoPath;
        this.isFavorites = false;
    }

    @Contract(pure = true)
    public String getPhotoPath() {
        return photoPath;
    }

    @Contract(pure = true)
    public boolean isFavorites() {
        return isFavorites;
    }

    public void setFavorites(boolean favorites) {
        isFavorites = favorites;
    }
}
