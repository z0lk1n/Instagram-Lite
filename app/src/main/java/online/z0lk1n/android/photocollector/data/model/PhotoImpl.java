package online.z0lk1n.android.photocollector.data.model;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import online.z0lk1n.android.photocollector.util.ApiConst;

public class PhotoImpl implements Photo {

    private final ApiService api;

    public PhotoImpl(ApiService api) {
        this.api = api;
    }

    @Override
    public Single<List<PhotoItemNet>> getPhotoList(String id) {
        return api.getPhotoList(ApiConst.ACCESS_KEY)
                .subscribeOn(Schedulers.io());
    }
}
