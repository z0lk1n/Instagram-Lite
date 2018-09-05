package online.z0lk1n.android.instagram_lite.presenter;

import android.support.annotation.StringRes;
import android.view.View;

public interface PhotoGalleryPresenter {
    interface PhotoGalleryView {
        void showDeletePhotoDialog(View view, final int position);

        void addOrRemoveFavorites(int position);

        void showFullPhoto(View view, final int position);

        void showNotifyingMessage(@StringRes int resourceId);
    }

    void onPhotoClick(View view, int position);

    void onPhotoLongClick(View view, int position);

    void onFavoritesClick(int position);
}