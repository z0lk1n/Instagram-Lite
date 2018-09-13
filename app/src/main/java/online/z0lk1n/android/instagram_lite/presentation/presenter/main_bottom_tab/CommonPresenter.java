package online.z0lk1n.android.instagram_lite.presentation.presenter.main_bottom_tab;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import online.z0lk1n.android.instagram_lite.model.PhotoItem;
import online.z0lk1n.android.instagram_lite.presentation.view.main_bottom_tab.CommonView;
import online.z0lk1n.android.instagram_lite.util.Const;
import online.z0lk1n.android.instagram_lite.util.Preferences;
import online.z0lk1n.android.instagram_lite.util.managers.AndroidResourceManager;
import online.z0lk1n.android.instagram_lite.util.managers.ResourceManager;

@InjectViewState
public final class CommonPresenter extends MvpPresenter<CommonView> {

    private static final String TAG = "CommonPresenter";

    private final Preferences preferences;
    private final ResourceManager resourceManager;
    private List<PhotoItem> photoItemList;

    public CommonPresenter(List<PhotoItem> photoItemList,
                           Preferences preferences,
                           AndroidResourceManager resourceManager) {
        this.photoItemList = photoItemList;
        this.preferences = preferences;
        this.resourceManager = resourceManager;
    }

    public void onPhotoClick(int position) {
        getViewState().showFullPhoto(photoItemList.get(position).getPhotoPath());
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
        if (new File(photoItemList.get(position).getPhotoPath()).delete()) {
            photoItemList.remove(position);
            updateView(position,
                    Const.NOTIFY_ITEM_REMOVE,
                    resourceManager.getPhotoDeleted());
        }
    }

    public void failCapturePhoto(String filePath) {
        if (new File(filePath).delete()) {
            getViewState().showNotifyingMessage(resourceManager.getFailCapturePhoto());
        }
    }

    public void capturePhoto() {
        getViewState().startCamera(createPhotoFileName(), resourceManager.getFileNameSuffix());
    }

    @NonNull
    private String createPhotoFileName() {
        String timeStamp = new SimpleDateFormat(resourceManager.getDateFormat(), Locale.US).format(new Date());
        return resourceManager.getFileNamePrefix() + timeStamp;
    }

    public void addPhoto(String filePath) {
        photoItemList.add(new PhotoItem(filePath, false));
        updateView(photoItemList.size() - 1,
                Const.NOTIFY_ITEM_INSERT,
                resourceManager.getPhotoUploaded());
    }

    private void updateView(int position, int action, String message) {
        getViewState().notifyItem(position, action);
        getViewState().showNotifyingMessage(message);
    }
}
