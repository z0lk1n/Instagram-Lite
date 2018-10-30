package online.z0lk1n.android.photocollector.presentation.presenters.bottomtab;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import online.z0lk1n.android.photocollector.data.database.PhotoEntity;
import online.z0lk1n.android.photocollector.data.model.Photos;
import online.z0lk1n.android.photocollector.data.repositories.PhotoRepository;
import online.z0lk1n.android.photocollector.presentation.ui.Screens;
import online.z0lk1n.android.photocollector.presentation.ui.bottomtab.NetworkView;
import online.z0lk1n.android.photocollector.util.ApiConst;
import online.z0lk1n.android.photocollector.util.SchedulersProvider;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public final class NetworkPresenter extends MvpPresenter<NetworkView> {

    private static final String MSG_ERROR_PHOTO_LIST = "Failed to get photos list";

    private List<PhotoEntity> currentList;

    @Inject Router router;
    @Inject PhotoRepository repository;
    @Inject SchedulersProvider schedulers;
    @Inject Photos photos;

    public NetworkPresenter() {
        this.currentList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        photos.getPhotos(ApiConst.ACCESS_KEY)
                .map(photoEntities -> {
                    repository.addAllPhotos(photoEntities);
                    return photoEntities;
                })
                .observeOn(schedulers.ui())
                .subscribe();
    }

    public void showFullPhoto(String photoPath) {
        router.navigateTo(new Screens.FullscreenPhotoScreen(photoPath));
    }

    public void onPhotoLongClick(String photoPath) {

    }

    @SuppressLint("CheckResult")
    public void onFavoritesClick(boolean isChecked, String photoPath) {
        Completable
                .fromAction(() -> {
                    PhotoEntity photo = findByPhotoPath(photoPath);
                    photo.setFavorite(isChecked);
                    repository.changeFavorites(photo);
                })
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(() -> {
                });
        updatePhotoList();
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

    public void onResume() {
        updatePhotoList();
    }

    @SuppressLint("CheckResult")
    private void updatePhotoList() {
        repository.getNetworkPhotoList()
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
}
