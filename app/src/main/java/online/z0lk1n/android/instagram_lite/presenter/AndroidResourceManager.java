package online.z0lk1n.android.instagram_lite.presenter;

import android.content.Context;

import online.z0lk1n.android.instagram_lite.R;

public class AndroidResourceManager implements ResourceManager {
    private Context context;

    public AndroidResourceManager(Context context) {
        this.context = context;
    }

    @Override
    public String getDateFormat() {
        return context.getResources().getString(R.string.date_format);
    }

    @Override
    public String getFileNamePrefix() {
        return context.getResources().getString(R.string.file_name_prefix);
    }

    @Override
    public String getFIleNameSuffix() {
        return context.getResources().getString(R.string.file_name_suffix);
    }

    @Override
    public String getPhotoUploaded() {
        return context.getResources().getString(R.string.photo_uploaded);
    }

    @Override
    public String getPhotoDeleted() {
        return context.getResources().getString(R.string.photo_deleted);
    }
}
