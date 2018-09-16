package online.z0lk1n.android.instagram_lite.data.repositories;

import java.util.List;

import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

public interface PhotoRepository {

    String getPhotoPath(int position);

    List<PhotoItem> getPhotoList();

    int getLastPhotoPosition();

    void removePhoto(int position);

    void addPhoto(String photoPath, boolean favorites);

    void changeFavorites(int position, boolean favorites);
}
