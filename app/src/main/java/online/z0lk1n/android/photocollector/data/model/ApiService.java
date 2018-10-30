package online.z0lk1n.android.photocollector.data.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("photos/")
    Single<List<PhotoModel>> getPhotos(@Query("client_id") String clientId);
}
