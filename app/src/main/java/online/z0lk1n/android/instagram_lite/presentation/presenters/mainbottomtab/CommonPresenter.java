package online.z0lk1n.android.instagram_lite.presentation.presenters.mainbottomtab;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;

import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepository;
import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepositoryImpl;
import online.z0lk1n.android.instagram_lite.presentation.ui.mainbottomtab.CommonView;
import online.z0lk1n.android.instagram_lite.util.Const;
import online.z0lk1n.android.instagram_lite.util.FileManager;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;
import online.z0lk1n.android.instagram_lite.util.ResourceManager;
import online.z0lk1n.android.instagram_lite.util.ResourceManagerImpl;
import online.z0lk1n.android.instagram_lite.util.SchedulersProvider;
import online.z0lk1n.android.instagram_lite.util.SchedulersProviderImpl;

@InjectViewState
public final class CommonPresenter extends MvpPresenter<CommonView> {

    private final ResourceManager resources;
    private final SchedulersProvider schedulers;
    private final PhotoManager photoManager;
    private final FileManager fileManager;
    private final PhotoRepository repository;

    public CommonPresenter(ResourceManagerImpl resources, PhotoManager photoManager, FileManager fileManager) {
        this.resources = resources;
        this.photoManager = photoManager;
        this.fileManager = fileManager;
        this.schedulers = new SchedulersProviderImpl();
        this.repository = PhotoRepositoryImpl.getInstance();
        updatePhotoList();
    }

    public void onPhotoClick(int position) {
        getViewState().showFullPhoto(repository.getPhotoPath(position));
    }

    public void onPhotoLongClick(int position) {
        getViewState().showDeletePhotoDialog(position);
    }

    public void onFavoritesClick(boolean isChecked, int position) {
        repository.changeFavorites(position, isChecked);
    }

    public void deletePhoto(int position) {
        if (new File(repository.getPhotoPath(position)).delete()) {
            repository.removePhoto(position);
            updateView(position,
                    Const.NOTIFY_ITEM_REMOVE,
                    resources.getPhotoDeleted());
            updatePhotoList();
        }
    }

    public void failCapturePhoto(String source) {
        fileManager.deleteFile(source);
        getViewState().showNotifyingMessage(resources.getFailCapturePhoto());
    }

    public void capturePhoto() {
        getViewState().startCamera();
    }

    public void addPhoto(String filePath) {
        repository.addPhoto(filePath, false);
        updateView(repository.getLastPhotoPosition(),
                Const.NOTIFY_ITEM_INSERT,
                resources.getPhotoUploaded());
    }

    private void updateView(int position, int action, String message) {
        getViewState().notifyItem(position, action);
        getViewState().showNotifyingMessage(message);
    }

    private void updatePhotoList() {
        getViewState().updatePhotoList(fileManager.updatePhotoListFromDir(repository.getPhotoList()));
    }
}
