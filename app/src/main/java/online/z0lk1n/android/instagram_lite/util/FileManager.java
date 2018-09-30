package online.z0lk1n.android.instagram_lite.util;

import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.util.List;

import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

public interface FileManager {

    List<PhotoItem> updatePhotoListFromDir(List<PhotoItem> photoItemList);

    File createFile() throws IOException;

    Uri getUriFromFile(File file);
}
