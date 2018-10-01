package online.z0lk1n.android.instagram_lite.util;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

public class FileManagerImpl implements FileManager {

    private final Context context;
    private final ResourceManager resources;
    private final File storageDir;

    public FileManagerImpl(Context context) {
        this.context = context;
        this.resources = new ResourceManagerImpl(context);
        this.storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public List<PhotoItem> updatePhotoListFromDir(List<PhotoItem> photoItemList) {
        for (File file : storageDir.listFiles()) {
            photoItemList.add(new PhotoItem(file.getPath(), false));
        }
        return photoItemList;
    }

    @Override
    public File createFile() throws IOException {
        return File.createTempFile(
                createPhotoFileName(),
                resources.getFileNameSuffix(),
                storageDir);
    }

    @Override
    public Uri getUriFromFile(File file) {
        return FileProvider.getUriForFile(
                context,
                resources.getPackageName(),
                file);
    }

    @NonNull
    private String createPhotoFileName() {
        String timeStamp = new SimpleDateFormat(resources.getDateFormat(), Locale.US).format(new Date());
        return resources.getFileNamePrefix() + timeStamp;
    }

    @Override
    public void deleteFile(String source)  {
        context.getContentResolver().delete(Uri.parse(source), null, null);
    }
}
