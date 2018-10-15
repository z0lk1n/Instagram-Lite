package online.z0lk1n.android.instagram_lite.presentation.presenters.fullscreenphoto;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import online.z0lk1n.android.instagram_lite.presentation.ui.fullscreenphoto.FullscreenPhotoView;
import ru.terrakok.cicerone.Router;

@InjectViewState
public final class FullscreenPhotoPresenter extends MvpPresenter<FullscreenPhotoView> {

    private boolean isVisible;

    @Inject Router router;

    public FullscreenPhotoPresenter() {
        this.isVisible = true;
    }

    public void onBackPressed() {
        router.exit();
    }

    public void pushToggle() {
        if (isVisible) {
            getViewState().hide();
            isVisible = false;
        } else {
            getViewState().show();
            isVisible = true;
        }
    }
}
