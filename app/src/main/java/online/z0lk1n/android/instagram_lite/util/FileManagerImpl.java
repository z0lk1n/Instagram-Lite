package online.z0lk1n.android.instagram_lite.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

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

    @Override
    public List<PhotoItem> updatePhotoListFromDir(List<PhotoItem> photoItemList) {
        for (File file : storageDir.listFiles()) {
            photoItemList.add(new PhotoItem(file.getAbsolutePath()));
        }
        return photoItemList;
    }

    @Override
    public Uri createUriForIntent() {
        return FileProvider.getUriForFile(
                context,
                resources.getPackageName(),
                createFile());
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

    @NonNull
    private String getStorageDirPath() {
        return storageDir.getAbsolutePath() + "/";
    }
}
