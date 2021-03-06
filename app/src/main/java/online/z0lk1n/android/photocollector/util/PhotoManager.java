package online.z0lk1n.android.photocollector.util;

import android.widget.ImageView;

import java.io.File;

public interface PhotoManager {

    int calculateNumberOfColumns();

    int calculateWidthOfPhoto();

    int calculateHeightOfPhoto();

    int getOrientationPhoto(String filePath);

    void setPhoto(ImageView imageView, File file, int width, int height);

    void setPhoto(ImageView imageView, String url, int width, int height);
}
