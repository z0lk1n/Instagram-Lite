package online.z0lk1n.android.instagram_lite.data.repositories;

import java.util.List;

import io.reactivex.Single;
import online.z0lk1n.android.instagram_lite.data.database.PhotoDAO;
import online.z0lk1n.android.instagram_lite.data.database.PhotoDatabase;
import online.z0lk1n.android.instagram_lite.data.database.PhotoEntity;

public final class PhotoRepositoryImpl implements PhotoRepository {

    private final PhotoDAO db;

    public PhotoRepositoryImpl(PhotoDatabase photoDatabase) {
        this.db = photoDatabase.photoDAO();
    }

    @Override
    public void removePhoto(PhotoEntity photoEntity) {
        db.delete(photoEntity);
    }

    @Override
    public void addPhoto(PhotoEntity photoEntity) {
        db.insert(photoEntity);
    }

    @Override
    public void changeFavorites(PhotoEntity photoEntity) {
        db.update(photoEntity);
    }

    @Override
    public Single<List<PhotoEntity>> getPhotoList() {
        return db.getAllPhoto();
    }
}
