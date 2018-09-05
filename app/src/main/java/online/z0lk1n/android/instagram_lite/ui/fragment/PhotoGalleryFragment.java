package online.z0lk1n.android.instagram_lite.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.model.PhotoItem;
import online.z0lk1n.android.instagram_lite.presenter.PhotoGalleryPresenter;
import online.z0lk1n.android.instagram_lite.presenter.PhotoGalleryPresenterImpl;
import online.z0lk1n.android.instagram_lite.ui.activity.MainActivity;
import online.z0lk1n.android.instagram_lite.util.Navigator;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;
import online.z0lk1n.android.instagram_lite.util.RecyclerViewAdapter;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public final class PhotoGalleryFragment extends Fragment
        implements RecyclerViewAdapter.OnItemClickListener, PhotoGalleryPresenter.PhotoGalleryView {
    public static final String NAME = "cb2d00bb-ca6b-45e6-a501-80f70efa65b9";
    private static final String TAG = "PhotoGalleryFragment";

    private final int PHOTO_CAMERA_REQUEST = 1;
    private final int OUT_OF_ARRAY_POSITION = -1;
    private PhotoGalleryPresenterImpl presenter;
    private File storageDir;
    private RecyclerViewAdapter adapter;
    private int numberOfColumns;
    private List<PhotoItem> photoItemList;
    private String currentFilePath;
    private int dimens;
    private RecyclerView recyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        numberOfColumns = PhotoManager.calculateNumberOfColumns(context);
        dimens = PhotoManager.calculateWidthOfPhoto(context, numberOfColumns);
        storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showFloatingActionButton();
        }
        presenter = new PhotoGalleryPresenterImpl(this);
        photoItemList = new ArrayList<>();
        getFilesList();
        adapter = new RecyclerViewAdapter(photoItemList, dimens);
        adapter.setOnItemClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_CAMERA_REQUEST) {
            switch (resultCode) {
                case RESULT_OK:
                    addPhoto();
                    break;
                case RESULT_CANCELED:
                    deletePhoto(OUT_OF_ARRAY_POSITION);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onPhotoClick(View view, int position) {
        presenter.onPhotoClick(view, position);
    }

    @Override
    public void onPhotoLongClick(View view, int position) {
        presenter.onPhotoLongClick(view, position);
    }

    @Override
    public void onFavoritesClick(int position) {
        presenter.onFavoritesClick(position);
    }

    @Override
    public void showDeletePhotoDialog(View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle(R.string.ask_delete_photo)
                .setPositiveButton(R.string.ok_button, (dialog, which) -> deletePhoto(position))
                .setNegativeButton(R.string.cancel_button, (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void addOrRemoveFavorites(int position) {
        if (photoItemList.get(position).isFavorites()) {
            photoItemList.get(position).setFavorites(false);
        } else {
            photoItemList.get(position).setFavorites(true);
        }
        adapter.notifyItemChanged(position);
    }

    @Override
    public void showFullPhoto(View view, int position) {
        new Navigator().showPhotoFragment(
                (AppCompatActivity) view.getContext(),
                photoItemList.get(position).getPhotoPath());
    }

    @Override
    public void showNotifyingMessage(int resourceId) {
        Snackbar.make(recyclerView, resourceId, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.setOnItemClickListener(null);
    }

    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getActivity() != null) {
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    currentFilePath = photoFile.getPath();
                    Uri photoURI = FileProvider.getUriForFile(getActivity(),
                            getResources().getString(R.string.package_name),
                            photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, PHOTO_CAMERA_REQUEST);
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat(getString(R.string.date_format), Locale.US).format(new Date());
        String imageFileName = getString(R.string.file_name_prefix) + timeStamp;
        return File.createTempFile(imageFileName, getString(R.string.file_name_suffix), storageDir);
    }

    private void getFilesList() {
        if (photoItemList.size() != storageDir.listFiles().length) {
            for (File file : storageDir.listFiles()) {
                photoItemList.add(new PhotoItem(file.getPath(), false));
            }
        }
    }

    private void deletePhoto(int position) {
        if (position == OUT_OF_ARRAY_POSITION) {
            new File(currentFilePath).delete();
        } else {
            if (new File(photoItemList.get(position).getPhotoPath()).delete()) {
                photoItemList.remove(position);
                adapter.notifyItemRemoved(position);
                Snackbar.make(recyclerView, R.string.photo_deleted, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void addPhoto() {
        photoItemList.add(new PhotoItem(currentFilePath, false));
        adapter.notifyItemInserted(photoItemList.size() - 1);
        Snackbar.make(recyclerView, R.string.photo_uploaded, Snackbar.LENGTH_SHORT).show();
    }
}