package online.z0lk1n.android.photocollector.data.model;

import java.util.List;

import io.reactivex.Single;
import online.z0lk1n.android.photocollector.data.database.PhotoEntity;

public interface Photos {
    Single<List<PhotoEntity>> getPhotos(String id);
}
