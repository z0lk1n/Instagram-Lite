package online.z0lk1n.android.instagram_lite.presentation.presenters.toptab;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepository;
import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepositoryImpl;
import online.z0lk1n.android.instagram_lite.presentation.ui.Screens;
import online.z0lk1n.android.instagram_lite.presentation.ui.toptab.FavoritesTabView;
import online.z0lk1n.android.instagram_lite.util.FileManager;
import online.z0lk1n.android.instagram_lite.util.ResourceManager;
import ru.terrakok.cicerone.Router;

@InjectViewState
public final class FavoritesTabPresenter extends MvpPresenter<FavoritesTabView> {

    private final PhotoRepository repository;

    @Inject Router router;
    @Inject ResourceManager resources;
    @Inject FileManager fileManager;

    public FavoritesTabPresenter() {
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
        getViewState().notifyItemRemoved(position);
    }

    public void deletePhoto(int position) {
        if (fileManager.deleteFile(repository.getPhotoPath(position))) {
            repository.removePhoto(position);
            updateView(position, resources.getPhotoDeleted());
            updatePhotoList();
        }
    }

    private void updateView(int position, String message) {
        getViewState().notifyItemRemoved(position);
        getViewState().showNotifyingMessage(message);
    }

    private void updatePhotoList() {
        getViewState().updatePhotoList(fileManager.updatePhotoListFromDir(repository.getPhotoList()));
    }

    public void closeDialog() {
        getViewState().closeDialog();
    }
}
