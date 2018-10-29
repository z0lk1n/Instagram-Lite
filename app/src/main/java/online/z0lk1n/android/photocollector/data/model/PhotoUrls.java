package online.z0lk1n.android.photocollector.data.model;

import com.google.gson.annotations.SerializedName;

public class PhotoUrls {

    @SerializedName("raw")
    private final String raw;

    @SerializedName("full")
    private final String full;

    @SerializedName("regular")
    private final String regular;

    @SerializedName("small")
    private final String small;

    @SerializedName("thumb")
    private final String thumb;

    public PhotoUrls(String raw, String full, String regular, String small, String thumb) {
        this.raw = raw;
        this.full = full;
        this.regular = regular;
        this.small = small;
        this.thumb = thumb;
    }

    public String getRaw() {
        return raw;
    }

    public String getFull() {
        return full;
    }

    public String getRegular() {
        return regular;
    }

    public String getSmall() {
        return small;
    }

    public String getThumb() {
        return thumb;
    }
}
