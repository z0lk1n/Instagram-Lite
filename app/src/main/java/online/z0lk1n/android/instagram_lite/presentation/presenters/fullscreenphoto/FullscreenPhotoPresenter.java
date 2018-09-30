package online.z0lk1n.android.instagram_lite.presentation.presenters.fullscreenphoto;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import online.z0lk1n.android.instagram_lite.presentation.ui.fullscreenphoto.FullscreenPhotoView;
import online.z0lk1n.android.instagram_lite.util.SchedulersProvider;

@InjectViewState
public final class FullscreenPhotoPresenter extends MvpPresenter<FullscreenPhotoView> {

    private final SchedulersProvider schedulers;

    public FullscreenPhotoPresenter(SchedulersProvider schedulers) {
        this.schedulers = schedulers;
    }
}
