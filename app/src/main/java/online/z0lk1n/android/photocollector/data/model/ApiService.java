package online.z0lk1n.android.photocollector.data.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("photos/?client_id={id}")
    Single<List<PhotoItemNet>> getPhotoList(@Path("id") String id);
}
