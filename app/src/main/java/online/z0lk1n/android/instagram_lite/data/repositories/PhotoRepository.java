package online.z0lk1n.android.instagram_lite.data.repositories;

import java.util.List;

import io.reactivex.Single;
import online.z0lk1n.android.instagram_lite.data.database.PhotoEntity;

public interface PhotoRepository {

    String getPhotoPath(int position);

    Single<List<PhotoEntity>> getPhotoList();

    int getLastPhotoPosition();

    void removePhoto(int position);

    void addPhoto(String fileName);

    void changeFavorites(int position, boolean favorites);
}
