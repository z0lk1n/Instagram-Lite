package online.z0lk1n.android.photocollector.presentation.presenters.bottomtab;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import online.z0lk1n.android.photocollector.data.database.PhotoEntity;
import online.z0lk1n.android.photocollector.data.model.Photo;
import online.z0lk1n.android.photocollector.presentation.ui.Screens;
import online.z0lk1n.android.photocollector.presentation.ui.bottomtab.NetworkView;
import online.z0lk1n.android.photocollector.util.ApiConst;
import online.z0lk1n.android.photocollector.util.SchedulersProvider;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public final class NetworkPresenter extends MvpPresenter<NetworkView> {

    private static final String MSG_ERROR_PHOTO_LIST = "Failed to get photo list";

    private List<PhotoEntity> currentList;

    @Inject Router router;
    @Inject SchedulersProvider schedulers;
    @Inject Photo photo;

    public NetworkPresenter() {
        this.currentList = new ArrayList<>();
    }

    public void showFullPhoto(String photoPath) {
        router.navigateTo(new Screens.FullscreenPhotoScreen(photoPath));
    }

    public void onPhotoLongClick(String photoPath) {

    }

    public void onFavoritesClick(boolean isChecked, String photoPath) {

    }

    public void onResume() {
        updatePhotoList();
    }

    @SuppressLint("CheckResult")
    private void updatePhotoList() {
        photo.getPhotoList(ApiConst.ACCESS_KEY)
//                .map(photoItemNets -> {
//                    List<PhotoEntity> photoEntities = new ArrayList<>();
//                    for (PhotoItemNet item : photoItemNets) {
//                        photoEntities.add(new PhotoEntity(item.getPhotoUrl(), false, false));
//                    }
//                    photoEntities.add(new PhotoEntity("dfsdfsdfsdf", false, false));
//                    return photoEntities;
//                })
                .observeOn(schedulers.ui())
                .subscribe(photoItemNets -> {
                    Timber.d(String.valueOf(photoItemNets.size()));
//                    currentList = photoEntities;
//                    getViewState().updatePhotoList(photoEntities);
                }, throwable -> {
//                    Timber.d(throwable, MSG_ERROR_PHOTO_LIST);
                    getViewState().showNotifyingMessage(MSG_ERROR_PHOTO_LIST);
                });
    }
}
