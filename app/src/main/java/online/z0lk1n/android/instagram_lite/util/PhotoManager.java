package online.z0lk1n.android.instagram_lite.util;

import android.widget.ImageView;

import java.io.File;

public interface PhotoManager {

    int calculateNumberOfColumns();

    int calculateWidthOfPhoto();

    int calculateHeightOfPhoto();

    int getOrientationPhoto(String filePath);

    void setPhoto(ImageView imageView, File file, int width, int height);
}
