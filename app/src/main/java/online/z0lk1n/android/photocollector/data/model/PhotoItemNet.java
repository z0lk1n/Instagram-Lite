package online.z0lk1n.android.photocollector.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoItemNet {
    @Expose
    @SerializedName("urls")
    private final String[] urls;

    public PhotoItemNet(String[] urls) {
        this.urls = urls;
    }

    public String[] getUrls() {
        return urls;
    }
}
