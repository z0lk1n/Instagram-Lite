package online.z0lk1n.android.instagram_lite.data.repositories;

import java.util.List;

import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

public interface PhotoRepository {

    String getFileName(int position);

    List<PhotoItem> getPhotoList();

    int getLastPhotoPosition();

    void removePhoto(int position);

    void addPhoto(String fileName);

    void changeFavorites(int position, boolean favorites);
}
