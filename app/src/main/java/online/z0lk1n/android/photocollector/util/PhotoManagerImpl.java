package online.z0lk1n.android.photocollector.util;

import android.content.Context;
import android.support.media.ExifInterface;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import online.z0lk1n.android.photocollector.R;

public final class PhotoManagerImpl implements PhotoManager {

    private Context context;

    public PhotoManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public int calculateNumberOfColumns() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    @Override
    public int calculateWidthOfPhoto() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / calculateNumberOfColumns();
    }

    @Override
    public int calculateHeightOfPhoto() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    @Override
    public int getOrientationPhoto(String filePath) {
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

    @Override
    public void setPhoto(ImageView imageView, File file, int width, int height) {
        Picasso.get()
                .load(file)
                .resize(width, height)
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_broken_image)
                .into(imageView);
    }

    @Override
    public void setPhoto(ImageView imageView, String url, int width, int height) {
        Picasso.get()
                .load(url)
                .resize(width, height)
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_broken_image)
                .into(imageView);
    }
}