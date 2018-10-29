package online.z0lk1n.android.photocollector.data.model;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class PhotosImpl implements Photos {

    private final ApiService api;

    public PhotosImpl(ApiService api) {
        this.api = api;
    }

    @Override
    public Single<List<PhotoModel>> getPhotos(String clientId) {
        return api.getPhotos(clientId).subscribeOn(Schedulers.io());
    }
}
