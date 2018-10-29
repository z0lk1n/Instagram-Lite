package online.z0lk1n.android.photocollector.util;

import android.net.Uri;

import java.io.IOException;

public interface FileManager {

    Uri createUriForIntent() throws IOException;

    String getPhotoPath(String fileName);

    boolean deleteFile(String photoPath);
}
