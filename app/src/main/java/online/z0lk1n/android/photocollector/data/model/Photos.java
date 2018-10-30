package online.z0lk1n.android.photocollector.data.model;

import java.util.List;

import io.reactivex.Single;

public interface Photos {
    Single<List<PhotoModel>> getPhotos(String id);
}
