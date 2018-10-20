package online.z0lk1n.android.photocollector.data.model;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET
    Single<PhotoItem> getPhoto(String url);
}
