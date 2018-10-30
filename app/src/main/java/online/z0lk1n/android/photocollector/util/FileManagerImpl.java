package online.z0lk1n.android.photocollector.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class FileManagerImpl implements FileManager {

    private final Context context;
    private final ResourceManager resources;
    private final File storageDir;
    private final String storageDirPath;

    public FileManagerImpl(Context context) {
        this.context = context;
        this.resources = new ResourceManagerImpl(context);
        this.storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        this.storageDirPath = getStorageDirPath();
    }

    @NonNull
    private String getStorageDirPath() {
        return storageDir.getAbsolutePath() + "/";
    }

    @Override
    public Uri createUriForIntent() {
        return FileProvider.getUriForFile(context, resources.getPackageName(), createFile());
    }

    @NonNull
    private File createFile() {
        return new File(storageDirPath + createPhotoFileName());
    }

    @NonNull
    private String createPhotoFileName() {
        String timeStamp = new SimpleDateFormat(resources.getDateFormat(), Locale.US).format(new Date());
        return resources.getFileNamePrefix() + timeStamp + resources.getFileNameSuffix();
    }

    @Override
    public String getPhotoPath(String fileName) {
        return storageDirPath + fileName;
    }

    @Override
    public boolean deleteFile(String photoPath) {
        return new File(photoPath).delete();
    }
}
