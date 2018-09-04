package online.z0lk1n.android.instagram_lite.presenter;


import android.view.View;

public class PhotoGalleryPresenterImpl implements PhotoGalleryPresenter {
    private static final String TAG = "PhotoGalleryPresenterImpl";
    private final PhotoGalleryView photoGalleryView;

    public PhotoGalleryPresenterImpl(PhotoGalleryView photoGalleryView) {
        this.photoGalleryView = photoGalleryView;
    }

    @Override
    public void onPhotoClick(View view, int position) {
        photoGalleryView.showFullPhoto(view, position);
    }

    @Override
    public void onPhotoLongClick(View view, int position) {
        photoGalleryView.showDeletePhotoDialog(view, position);
    }

    @Override
    public void onFavoritesClick(int position) {
        photoGalleryView.addOrRemoveFavorites(position);
    }
}
