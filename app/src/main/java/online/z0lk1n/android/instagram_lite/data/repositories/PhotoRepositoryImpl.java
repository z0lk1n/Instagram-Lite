package online.z0lk1n.android.instagram_lite.data.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import online.z0lk1n.android.instagram_lite.data.database.PhotoDAO;
import online.z0lk1n.android.instagram_lite.data.database.PhotoDatabase;
import online.z0lk1n.android.instagram_lite.data.database.PhotoEntity;
import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

public final class PhotoRepositoryImpl implements PhotoRepository {

    private static volatile PhotoRepositoryImpl instance = new PhotoRepositoryImpl();
    private List<PhotoItem> photoItemList;
    private PhotoDAO photoDAO;

    @Inject PhotoDatabase db;

    private PhotoRepositoryImpl() {
        this.photoItemList = new ArrayList<>();
        this.photoDAO = db.photoDAO();
    }

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
    public void addPhoto(String fileName) {
        photoItemList.add(new PhotoItem(fileName));
    }

    @Override
    public void changeFavorites(int position, boolean favorites) {
        photoItemList.get(position).setFavorites(favorites);
    }

    @Override
    public Single<List<PhotoEntity>> getPhotoList() {
        return photoDAO.getAllPhoto();
    }
}
