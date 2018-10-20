package online.z0lk1n.android.photocollector.data.repositories;

import java.util.List;

import io.reactivex.Single;
import online.z0lk1n.android.photocollector.data.database.PhotoEntity;

public interface PhotoRepository {

    Single<List<PhotoEntity>> getPhotoList();

    void removePhoto(PhotoEntity photoEntity);

    void addPhoto(PhotoEntity photoEntity);

    void changeFavorites(PhotoEntity photoEntity);
}
