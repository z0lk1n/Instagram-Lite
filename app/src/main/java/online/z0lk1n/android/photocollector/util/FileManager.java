package online.z0lk1n.android.photocollector.util;

import android.net.Uri;

import java.io.IOException;
import java.util.List;

import online.z0lk1n.android.photocollector.data.model.PhotoItem;

public interface FileManager {

    List<PhotoItem> updatePhotoListFromDir(List<PhotoItem> photoItemList);

    Uri createUriForIntent() throws IOException;

    String getPhotoPath(String fileName);

    boolean deleteFile(String photoPath);
}
