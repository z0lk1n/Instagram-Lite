package online.z0lk1n.android.photocollector.data.model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import online.z0lk1n.android.photocollector.data.database.PhotoEntity;

public class PhotosImpl implements Photos {

    private final ApiService api;

    public PhotosImpl(ApiService api) {
        this.api = api;
    }

    @Override
    public Single<List<PhotoEntity>> getPhotos(String clientId) {
        return api.getPhotos(clientId)
                .subscribeOn(Schedulers.io())
                .map(photoModels -> {
                    List<PhotoEntity> photoEntities = new ArrayList<>();
                    for (PhotoModel photo : photoModels) {
                        photoEntities.add(new PhotoEntity(photo.getUrls().getRegular(), true, false));
                    }
                    return photoEntities;
                });
    }
}
