package online.z0lk1n.android.photocollector.data.model;

import com.google.gson.annotations.SerializedName;

public class PhotoModel {

    @SerializedName("id")
    private final String id;

    @SerializedName("urls")
    private final PhotoUrls urls;

    public PhotoModel(String id, PhotoUrls urls) {
        this.id = id;
        this.urls = urls;
    }

    public String getId() {
        return id;
    }

    public PhotoUrls getUrls() {
        return urls;
    }
}
