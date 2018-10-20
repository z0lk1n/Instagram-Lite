package online.z0lk1n.android.instagram_lite.data.model;

public final class PhotoItem {

    private final String photoPath;
    private boolean isFavorites;

    public PhotoItem(String photoPath) {
        this.photoPath = photoPath;
        this.isFavorites = false;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public boolean isFavorites() {
        return isFavorites;
    }

    public void setFavorites(boolean favorites) {
        isFavorites = favorites;
    }
}
