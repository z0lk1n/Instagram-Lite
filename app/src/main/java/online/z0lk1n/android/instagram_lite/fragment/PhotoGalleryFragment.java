package online.z0lk1n.android.instagram_lite.fragment;

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
import online.z0lk1n.android.instagram_lite.activity.MainActivity;
import online.z0lk1n.android.instagram_lite.activity.Navigator;
import online.z0lk1n.android.instagram_lite.model.PhotoItem;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;
import online.z0lk1n.android.instagram_lite.util.RecyclerViewAdapter;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class PhotoGalleryFragment extends Fragment
        implements RecyclerViewAdapter.OnItemClickListener {
    public static final String NAME = "cb2d00bb-ca6b-45e6-a501-80f70efa65b9";
    private static final String TAG = "PhotoGalleryFragment";

    private final int CAMERA_REQUEST = 1;
    private File storageDir;
    private RecyclerViewAdapter adapter;
    private int numberOfColumns;
    private List<PhotoItem> photoItemList;
    private String currentFilePath;
    private int dimens;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        numberOfColumns = PhotoManager.calculateNumberOfColumns(context);
        dimens = PhotoManager.calculateWidthOfPhoto(context, numberOfColumns);
        storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ((MainActivity) getActivity()).showFloatingActionButton();

        photoItemList = new ArrayList<>();
        getFilesList();
        adapter = new RecyclerViewAdapter(photoItemList, dimens);
        adapter.setOnItemClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            photoItemList.add(new PhotoItem(currentFilePath, false));
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
            Snackbar.make(getView(), R.string.photo_uploaded, Snackbar.LENGTH_SHORT).show();
        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_CANCELED) {
            new File(currentFilePath).delete();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void getFilesList() {
        if (photoItemList.size() != storageDir.listFiles().length) {
            for (File file : storageDir.listFiles()) {
                photoItemList.add(new PhotoItem(file.getPath(), false));
            }
        }
    }

    @Override
    public void onPhotoClick(View view, int position) {
        new Navigator().showPhotoFragment(
                (AppCompatActivity) view.getContext(),
                photoItemList.get(position).getPhotoPath());
    }

    @Override
    public void onPhotoLongClick(View view, int position) {
        showDeletePhotoDialog(view, position);
    }

    @Override
    public void onFavoritesClick(View view, int position) {
        addOrRemoveFavorites(position);
    }

    private void showDeletePhotoDialog(View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle("Delete Photo?")
                .setPositiveButton("OK", (dialog, which) -> {
                    new File(photoItemList.get(position).getPhotoPath()).delete();
                    photoItemList.remove(position);
                    adapter.notifyItemRemoved(position);
                })
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void addOrRemoveFavorites(int position) {
        if (photoItemList.get(position).isFavorites()) {
            photoItemList.get(position).setFavorites(false);
        } else {
            photoItemList.get(position).setFavorites(true);
        }
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.setOnItemClickListener(null);
    }
}
