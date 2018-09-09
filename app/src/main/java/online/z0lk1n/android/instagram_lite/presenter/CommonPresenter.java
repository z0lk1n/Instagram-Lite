package online.z0lk1n.android.instagram_lite.presenter;

import java.io.File;

public interface CommonPresenter {

    interface CommonView {
        void showDeletePhotoDialog(final int position);

        void showFullPhoto(final int position);

        void showNotifyingMessage(String message);

        void notifyItem(int position, int action);

        void startCamera(File file);
    }

    void onPhotoClick(int position);

    void onPhotoLongClick(int position);

    void onFavoritesClick(int position);

    void capturePhoto();

    void addPhoto();

    void deletePhoto(int position);
}