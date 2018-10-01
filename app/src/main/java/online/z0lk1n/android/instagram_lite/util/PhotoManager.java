package online.z0lk1n.android.instagram_lite.util;

import android.net.Uri;
import android.widget.ImageView;

public interface PhotoManager {

    int calculateNumberOfColumns();

    int calculateWidthOfPhoto();

    int calculateHeightOfPhoto();

    int getOrientationPhoto(String filePath);

    void setPhoto(ImageView imageView, Uri uri, int width, int height);
}
