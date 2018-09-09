package online.z0lk1n.android.instagram_lite.presenter;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import online.z0lk1n.android.instagram_lite.model.PhotoItem;
import online.z0lk1n.android.instagram_lite.util.Const;
import online.z0lk1n.android.instagram_lite.util.Preferences;

public final class CommonPresenterImpl implements CommonPresenter {

    private static final String TAG = "CommonPresenterImpl";

    private final CommonView commonView;
    private final File storageDir;
    private final Preferences preferences;
    private final ResourceManager resourceManager;
    private List<PhotoItem> photoItemList;
    private String currentFilePath;

    public CommonPresenterImpl(CommonView commonView,
                               List<PhotoItem> photoItemList,
                               File storageDir,
                               Preferences preferences,
                               AndroidResourceManager resourceManager) {
        this.commonView = commonView;
        this.photoItemList = photoItemList;
        this.storageDir = storageDir;
        this.preferences = preferences;
        this.resourceManager = resourceManager;
    }

    @Override
    public void onPhotoClick(int position) {
        commonView.showFullPhoto(position);
    }

    @Override
    public void onPhotoLongClick(int position) {
        commonView.showDeletePhotoDialog(position);
    }

    @Override
    public void onFavoritesClick(int position) {
        addOrRemoveFavorites(position);
    }

    private void addOrRemoveFavorites(int position) {
        Set<String> favorites = preferences.getFavorites();
        if (photoItemList.get(position).isFavorites()) {
            photoItemList.get(position).setFavorites(false);
            favorites.remove(photoItemList.get(position).getPhotoPath());
        } else {
            photoItemList.get(position).setFavorites(true);
            favorites.add(photoItemList.get(position).getPhotoPath());
        }
        preferences.setFavorites(favorites);
        commonView.notifyItem(position, Const.NOTIFY_ITEM_CHANGE);
    }

    @Override
    public void capturePhoto() {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile != null) {
            currentFilePath = photoFile.getPath();
            commonView.startCamera(photoFile);
        }
    }

    @NotNull
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat(resourceManager.getDateFormat(), Locale.US).format(new Date());
        String imageFileName = resourceManager.getFileNamePrefix() + timeStamp;
        return File.createTempFile(imageFileName, resourceManager.getFIleNameSuffix(), storageDir);
    }

    @Override
    public void addPhoto() {
        photoItemList.add(new PhotoItem(currentFilePath, false));
        commonView.notifyItem(photoItemList.size() - 1, Const.NOTIFY_ITEM_INSERT);
        commonView.showNotifyingMessage(resourceManager.getPhotoUploaded());
    }

    @Override
    public void deletePhoto(int position) {
        if (position == Const.OUT_OF_ARRAY_POSITION) {
            new File(currentFilePath).delete();
        } else {
            if (new File(photoItemList.get(position).getPhotoPath()).delete()) {
                photoItemList.remove(position);
                commonView.notifyItem(position, Const.NOTIFY_ITEM_REMOVE);
                commonView.showNotifyingMessage(resourceManager.getPhotoDeleted());
            }
        }
    }
}
