package online.z0lk1n.android.instagram_lite.data.repositories;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

public final class PhotoRepositoryImpl implements PhotoRepository {

    private static final String TAG = "PhotoRepositoryImpl";

    private static volatile PhotoRepositoryImpl instance = new PhotoRepositoryImpl();
    private List<PhotoItem> photoItemList;

    private PhotoRepositoryImpl() {
        this.photoItemList = new ArrayList<>();
    }

    @Contract(pure = true)
    public static synchronized PhotoRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new PhotoRepositoryImpl();
        }
        return instance;
    }

    @Override
    public String getPhotoPath(int position) {
        return photoItemList.get(position).getPhotoPath();
    }

    @Override
    public int getLastPhotoPosition() {
        return photoItemList.size() - 1;
    }

    @Override
    public void removePhoto(int position) {
        photoItemList.remove(position);
    }

    @Override
    public void addPhoto(String photoPath, boolean favorites) {
        photoItemList.add(new PhotoItem(photoPath, favorites));
    }

    @Override
    public void changeFavorites(int position, boolean favorites) {
        photoItemList.get(position).setFavorites(favorites);
    }

    @Contract(pure = true)
    @Override
    public List<PhotoItem> getPhotoList() {
        return photoItemList;
    }
}
