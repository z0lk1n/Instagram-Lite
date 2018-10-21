package online.z0lk1n.android.photocollector.data.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET
    Single<List<PhotoItem>> getPhoto(String url);
}
