package online.z0lk1n.android.instagram_lite.presentation.presenters.toptab;

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
import online.z0lk1n.android.instagram_lite.presentation.ui.toptab.FavoritesTabView;
import online.z0lk1n.android.instagram_lite.util.FileManager;
import online.z0lk1n.android.instagram_lite.util.ResourceManager;
import online.z0lk1n.android.instagram_lite.util.SchedulersProvider;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public final class FavoritesTabPresenter extends MvpPresenter<FavoritesTabView> {

    private static final String MSG_ERROR_PHOTO_LIST = "Failed to get photo list";

    private List<PhotoEntity> currentList;

    @Inject PhotoRepository repository;
    @Inject Router router;
    @Inject SchedulersProvider schedulers;
    @Inject ResourceManager resources;
    @Inject FileManager fileManager;

    public FavoritesTabPresenter() {
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
        PhotoEntity photo = findByPhotoPath(photoPath);
        Completable
                .fromAction(() -> {
                    photo.setFavorite(isChecked);
                    repository.changeFavorites(photo);
                })
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(() -> getViewState().notifyItemRemoved(currentList.indexOf(photo)));
        //TODO 19.10.18 maybe updatePhotoList();
    }

    @SuppressLint("CheckResult")
    public void deletePhoto(String photoPath) {
        int position = currentList.indexOf(findByPhotoPath(photoPath));

        if (fileManager.deleteFile(photoPath)) {
            Completable.fromAction(() -> repository.removePhoto(currentList.get(position)))
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(() -> updateView(position, resources.getPhotoDeleted()));
            updatePhotoList();
        }
    }

    private void updateView(int position, String message) {
        getViewState().notifyItemRemoved(position);
        getViewState().showNotifyingMessage(message);
    }

    @SuppressLint("CheckResult")
    private void updatePhotoList() {
        repository.getPhotoList()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(photoEntities -> {
                    currentList = findFavorites(photoEntities);
                    getViewState().updatePhotoList(currentList);
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

    private List<PhotoEntity> findFavorites(@Nullable List<PhotoEntity> photoEntities) {
        currentList.clear();
        for (PhotoEntity photo : photoEntities) {
            if (photo.isFavorite()) {
                currentList.add(photo);
            }
        }
        return currentList;
    }
}
