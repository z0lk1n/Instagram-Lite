package online.z0lk1n.android.photocollector.data.model;

import com.google.gson.annotations.Expose;

public final class PhotoItem {
    @Expose
    private final String photoPath;

    public PhotoItem(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoPath() {
        return photoPath;
    }
}
