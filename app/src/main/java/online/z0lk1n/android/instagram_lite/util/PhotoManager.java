package online.z0lk1n.android.instagram_lite.util;

import android.content.Context;
import android.support.media.ExifInterface;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import online.z0lk1n.android.instagram_lite.R;

public final class PhotoManager {

    public static int calculateNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    public static int calculateWidthOfPhoto(Context context, int columns) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / columns;
    }

    public static int calculateHeightOfPhoto(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static int getOrientationPhoto(String filePath) {
        ExifInterface exif = null;
        int orientation = 0;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exif != null) {
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
        }
        return orientation;
    }

    public static void setPhoto(ImageView imageView, File file, int width, int height) {
        Picasso.get()
                .load(file)
                .resize(width, height)
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_broken_image)
                .into(imageView);
    }
}