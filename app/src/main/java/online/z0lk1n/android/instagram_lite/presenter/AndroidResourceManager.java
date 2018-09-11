package online.z0lk1n.android.instagram_lite.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import online.z0lk1n.android.instagram_lite.R;

public final class AndroidResourceManager implements ResourceManager {
    private Context context;

    public AndroidResourceManager(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public String getDateFormat() {
        return context.getResources().getString(R.string.date_format);
    }

    @NonNull
    @Override
    public String getFileNamePrefix() {
        return context.getResources().getString(R.string.file_name_prefix);
    }

    @NonNull
    @Override
    public String getFIleNameSuffix() {
        return context.getResources().getString(R.string.file_name_suffix);
    }

    @NonNull
    @Override
    public String getPhotoUploaded() {
        return context.getResources().getString(R.string.photo_uploaded);
    }

    @NonNull
    @Override
    public String getPhotoDeleted() {
        return context.getResources().getString(R.string.photo_deleted);
    }
}
