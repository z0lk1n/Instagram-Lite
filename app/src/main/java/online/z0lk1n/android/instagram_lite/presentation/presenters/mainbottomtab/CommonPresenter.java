package online.z0lk1n.android.instagram_lite.presentation.presenters.mainbottomtab;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepository;
import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepositoryImpl;
import online.z0lk1n.android.instagram_lite.presentation.ui.Screens;
import online.z0lk1n.android.instagram_lite.presentation.ui.mainbottomtab.CommonView;
import online.z0lk1n.android.instagram_lite.util.Const;
import online.z0lk1n.android.instagram_lite.util.FileManager;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;
import online.z0lk1n.android.instagram_lite.util.ResourceManager;
import ru.terrakok.cicerone.Router;

@InjectViewState
public final class CommonPresenter extends MvpPresenter<CommonView> {

    private final PhotoRepository repository;

    @Inject ResourceManager resources;
    @Inject FileManager fileManager;
    @Inject PhotoManager photoManager;
    @Inject Router router;

    public CommonPresenter() {
        this.repository = PhotoRepositoryImpl.getInstance();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        updatePhotoList();
    }

    public void showFullPhoto(int position) {
        router.navigateTo(new Screens.FullscreenPhotoScreen(fileManager.getPhotoPath(repository.getPhotoPath(position))));
    }

    public void onPhotoLongClick(int position) {
        getViewState().showDeletePhotoDialog(position);
    }

    public void onFavoritesClick(boolean isChecked, int position) {
        repository.changeFavorites(position, isChecked);
    }

    public void deletePhoto(int position) {
        if (fileManager.deleteFile(repository.getPhotoPath(position))) {
            repository.removePhoto(position);
            updateView(position,
                    Const.NOTIFY_ITEM_REMOVE,
                    resources.getPhotoDeleted());
            updatePhotoList();
        }
    }

    public void capturePhoto() {
        getViewState().startCamera();
    }

    public void addPhoto(String fileName) {
        repository.addPhoto(fileManager.getPhotoPath(fileName));
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
