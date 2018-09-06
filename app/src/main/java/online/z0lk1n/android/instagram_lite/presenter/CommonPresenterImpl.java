package online.z0lk1n.android.instagram_lite.presenter;


import android.view.View;

public final class CommonPresenterImpl implements CommonPresenter {

    private static final String TAG = "CommonPresenterImpl";

    private final CommonView commonView;

    public CommonPresenterImpl(CommonView commonView) {
        this.commonView = commonView;
    }

    @Override
    public void onPhotoClick(View view, int position) {
        commonView.showFullPhoto(view, position);
    }

    @Override
    public void onPhotoLongClick(View view, int position) {
        commonView.showDeletePhotoDialog(view, position);
    }

    @Override
    public void onFavoritesClick(int position) {
        commonView.addOrRemoveFavorites(position);
    }
}
