package online.z0lk1n.android.instagram_lite.presentation.presenter.common;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import online.z0lk1n.android.instagram_lite.model.PhotoItem;
import online.z0lk1n.android.instagram_lite.presentation.view.common.CommonView;
import online.z0lk1n.android.instagram_lite.util.Const;
import online.z0lk1n.android.instagram_lite.util.Preferences;
import online.z0lk1n.android.instagram_lite.util.managers.AndroidResourceManager;
import online.z0lk1n.android.instagram_lite.util.managers.ResourceManager;

@InjectViewState
public final class CommonPresenter extends MvpPresenter<CommonView> {

    private static final String TAG = "CommonPresenter";

    private final File storageDir;
    private final Preferences preferences;
    private final ResourceManager resourceManager;
    private List<PhotoItem> photoItemList;

    private String currentFilePath;

    public CommonPresenter(List<PhotoItem> photoItemList,
                           File storageDir,
                           Preferences preferences,
                           AndroidResourceManager resourceManager) {
        this.photoItemList = photoItemList;
        this.storageDir = storageDir;
        this.preferences = preferences;
        this.resourceManager = resourceManager;
    }

    public void onPhotoClick(int position) {
        getViewState().showFullPhoto(position);
    }

    public void onPhotoLongClick(int position) {
        getViewState().showDeletePhotoDialog(position);
    }

    public void onFavoritesClick(boolean isChecked, int position) {
        addOrRemoveFavorites(isChecked, position);
    }

    private void addOrRemoveFavorites(boolean isChecked, int position) {
        Set<String> favorites = preferences.getFavorites();
        photoItemList.get(position).setFavorites(isChecked);
        if (isChecked) {
            favorites.add(photoItemList.get(position).getPhotoPath());
        } else {
            favorites.remove(photoItemList.get(position).getPhotoPath());
        }
        preferences.setFavorites(favorites);
    }

    public void deletePhoto(int position) {
        if (position == Const.OUT_OF_ARRAY_POSITION) {
            new File(currentFilePath).delete();
        } else {
            if (new File(photoItemList.get(position).getPhotoPath()).delete()) {
                photoItemList.remove(position);
                getViewState().notifyItem(position, Const.NOTIFY_ITEM_REMOVE);
                getViewState().showNotifyingMessage(resourceManager.getPhotoDeleted());
            }
        }
    }

    public void capturePhoto() {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile != null) {
            getViewState().startCamera(photoFile);
        }
    }

    @NotNull
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat(resourceManager.getDateFormat(), Locale.US).format(new Date());
        String photoFileName = resourceManager.getFileNamePrefix() + timeStamp;
        File photo = File.createTempFile(photoFileName, resourceManager.getFIleNameSuffix(), storageDir);
        currentFilePath = photo.getAbsolutePath();
        return photo;
    }

    public void addPhoto() {
        photoItemList.add(new PhotoItem(currentFilePath, false));
        getViewState().notifyItem(photoItemList.size() - 1, Const.NOTIFY_ITEM_INSERT);
        getViewState().showNotifyingMessage(resourceManager.getPhotoUploaded());
    }
}
