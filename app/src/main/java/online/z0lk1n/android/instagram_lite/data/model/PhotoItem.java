package online.z0lk1n.android.instagram_lite.data.model;

import org.jetbrains.annotations.Contract;

public final class PhotoItem {

    private final String photoPath;
    private boolean isFavorites;

    public PhotoItem(String photoPath, boolean isFavorites) {
        this.photoPath = photoPath;
        this.isFavorites = isFavorites;
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
