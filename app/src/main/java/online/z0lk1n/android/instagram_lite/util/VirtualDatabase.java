package online.z0lk1n.android.instagram_lite.util;

import java.util.List;

import online.z0lk1n.android.instagram_lite.model.PhotoItem;

public final class VirtualDatabase {
    private static volatile VirtualDatabase instance;
    public List<PhotoItem> photoItemList;

    private VirtualDatabase(List<PhotoItem> photoItemList) {
        this.photoItemList = photoItemList;
    }

    public VirtualDatabase getInstance() {
        if (instance == null) {
            synchronized (VirtualDatabase.class) {
                if (instance == null) {
                    instance = new VirtualDatabase(photoItemList);
                }
            }
        }
        return instance;
    }
}
