package online.z0lk1n.android.instagram_lite.model;

public class PhotoItem {
    private static final String TAG = "PhotoItem";
    private long id;
    private String photoPath;
    private boolean isFavorites;

    public PhotoItem(String photoPath, boolean isFavorites) {
        this.photoPath = photoPath;
        this.isFavorites = isFavorites;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public boolean isFavorites() {
        return isFavorites;
    }
}
