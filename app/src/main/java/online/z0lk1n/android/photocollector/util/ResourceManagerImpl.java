package online.z0lk1n.android.photocollector.util;

import android.content.Context;
import android.support.annotation.NonNull;

import online.z0lk1n.android.photocollector.R;

public final class ResourceManagerImpl implements ResourceManager {

    private Context context;

    public ResourceManagerImpl(Context context) {
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
    public String getFileNameSuffix() {
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

    @NonNull
    @Override
    public String getFailCapturePhoto() {
        return context.getResources().getString(R.string.fail_capture_photo);
    }

    @NonNull
    @Override
    public String getPackageName() {
        return context.getResources().getString(R.string.package_name);
    }
}
