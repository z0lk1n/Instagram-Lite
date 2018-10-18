package online.z0lk1n.android.instagram_lite.presentation.presenters.bottomtab;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import online.z0lk1n.android.instagram_lite.data.database.PhotoEntity;
import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepository;
import online.z0lk1n.android.instagram_lite.presentation.ui.Screens;
import online.z0lk1n.android.instagram_lite.presentation.ui.bottomtab.CommonView;
import online.z0lk1n.android.instagram_lite.util.Const;
import online.z0lk1n.android.instagram_lite.util.FileManager;
import online.z0lk1n.android.instagram_lite.util.ResourceManager;
import online.z0lk1n.android.instagram_lite.util.SchedulersProvider;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public final class CommonPresenter extends MvpPresenter<CommonView> {

    private static final String MSG_ERROR_PHOTO_LIST = "Failed to get photo list";

    private List<PhotoEntity> currentList;

    @Inject PhotoRepository repository;
    @Inject ResourceManager resources;
    @Inject SchedulersProvider schedulers;
    @Inject FileManager fileManager;
    @Inject Router router;

    public CommonPresenter() {
        this.currentList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        updatePhotoList();
    }

    public void showFullPhoto(String photoPath) {
        router.navigateTo(new Screens.FullscreenPhotoScreen(photoPath));
    }

    public void onPhotoLongClick(String photoPath) {
        getViewState().showDeletePhotoDialog(photoPath);
    }

    @SuppressLint("CheckResult")
    public void onFavoritesClick(boolean isChecked, String photoPath) {
        Completable.fromAction(() -> {
            PhotoEntity photo = findByPhotoPath(photoPath);
            photo.setFavorite(isChecked);
            repository.changeFavorites(photo);
        }).subscribeOn(schedulers.io());
    }

    @SuppressLint("CheckResult")
    public void deletePhoto(String photoPath) {
        int position = currentList.indexOf(findByPhotoPath(photoPath));

        if (fileManager.deleteFile(photoPath)) {
            Completable.fromAction(() -> repository.removePhoto(currentList.get(position)))
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(() -> updateView(position, Const.NOTIFY_ITEM_REMOVE, resources.getPhotoDeleted()));
            updatePhotoList();
        }
    }

    public void capturePhoto() {
        getViewState().startCamera();
    }

    @SuppressLint("CheckResult")
    public void addPhoto(String photoPath) {
        Completable.fromAction(() -> repository.addPhoto(new PhotoEntity(photoPath, false, false)))
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(() -> updateView(currentList.size(), Const.NOTIFY_ITEM_INSERT, resources.getPhotoUploaded()));
        updatePhotoList();
    }

    private void updateView(int position, int action, String message) {
        getViewState().notifyItem(position, action);
        getViewState().showNotifyingMessage(message);
    }

    @SuppressLint("CheckResult")
    private void updatePhotoList() {
        repository.getPhotoList()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(photoEntities -> {
                    currentList = photoEntities;
                    getViewState().updatePhotoList(photoEntities);
                }, throwable -> {
                    Timber.d(throwable, MSG_ERROR_PHOTO_LIST);
                    getViewState().showNotifyingMessage(MSG_ERROR_PHOTO_LIST);
                });
    }

    public void closeDialog() {
        getViewState().closeDialog();
    }

    @Nullable
    private PhotoEntity findByPhotoPath(String photoPath) {
        for (PhotoEntity photo : currentList) {
            if (photo.getPhotoPath().equals(photoPath)) {
                return photo;
            }
        }
        //FIXME 19.10.18 fix null
        return null;
    }
}
