package online.z0lk1n.android.instagram_lite.presentation.mvp.mainbottomtab;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepository;
import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepositoryImpl;
import online.z0lk1n.android.instagram_lite.util.Const;
import online.z0lk1n.android.instagram_lite.util.managers.AndroidResourceManager;
import online.z0lk1n.android.instagram_lite.util.managers.ResourceManager;

@InjectViewState
public final class CommonPresenter extends MvpPresenter<CommonView> {

    private static final String TAG = "CommonPresenter";

    private final ResourceManager resourceManager;
    private final PhotoRepository photoRepository;

    public CommonPresenter(AndroidResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.photoRepository = PhotoRepositoryImpl.getInstance();
        updatePhotoList();
    }

    public void onPhotoClick(int position) {
        getViewState().showFullPhoto(photoRepository.getPhotoPath(position));
    }

    public void onPhotoLongClick(int position) {
        getViewState().showDeletePhotoDialog(position);
    }

    public void onFavoritesClick(boolean isChecked, int position) {
        photoRepository.changeFavorites(position, isChecked);
    }

    public void deletePhoto(int position) {
        if (new File(photoRepository.getPhotoPath(position)).delete()) {
            photoRepository.removePhoto(position);
            updateView(position,
                    Const.NOTIFY_ITEM_REMOVE,
                    resourceManager.getPhotoDeleted());
            updatePhotoList();
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

    public void addPhoto(String filePath) {
        photoRepository.addPhoto(filePath, false);
        updateView(photoRepository.getLastPhotoPosition(),
                Const.NOTIFY_ITEM_INSERT,
                resourceManager.getPhotoUploaded());
    }

    @NonNull
    private String createPhotoFileName() {
        String timeStamp = new SimpleDateFormat(resourceManager.getDateFormat(), Locale.US).format(new Date());
        return resourceManager.getFileNamePrefix() + timeStamp;
    }

    private void updateView(int position, int action, String message) {
        getViewState().notifyItem(position, action);
        getViewState().showNotifyingMessage(message);
    }

    private void updatePhotoList() {
        getViewState().fillPhotoList(photoRepository.getPhotoList());
    }
}
