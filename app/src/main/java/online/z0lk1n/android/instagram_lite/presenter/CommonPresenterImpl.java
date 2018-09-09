package online.z0lk1n.android.instagram_lite.presenter;


import android.view.View;

import java.util.List;

import online.z0lk1n.android.instagram_lite.model.PhotoItem;

public final class CommonPresenterImpl implements CommonPresenter {

    private static final String TAG = "CommonPresenterImpl";

    private final CommonView commonView;
    private final List<PhotoItem> photoItemList;

    public CommonPresenterImpl(CommonView commonView, List<PhotoItem> photoItemList) {
        this.commonView = commonView;
        this.photoItemList = photoItemList;
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
