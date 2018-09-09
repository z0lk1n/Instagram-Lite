package online.z0lk1n.android.instagram_lite.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.model.PhotoItem;
import online.z0lk1n.android.instagram_lite.presenter.CommonPresenter;
import online.z0lk1n.android.instagram_lite.presenter.CommonPresenterImpl;
import online.z0lk1n.android.instagram_lite.ui.activity.MainActivity;
import online.z0lk1n.android.instagram_lite.util.Navigator;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;
import online.z0lk1n.android.instagram_lite.util.Preferences;
import online.z0lk1n.android.instagram_lite.util.RecyclerViewAdapter;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public final class CommonFragment extends Fragment
        implements RecyclerViewAdapter.OnItemClickListener, CommonPresenter.CommonView {

    public static final String NAME = "cb2d00bb-ca6b-45e6-a501-80f70efa65b9";
    private static final String TAG = "CommonFragment";

    private final int PHOTO_CAMERA_REQUEST = 1;
    private final int OUT_OF_ARRAY_POSITION = -1;

    private CommonPresenterImpl presenter;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private List<PhotoItem> photoItemList;
    private File storageDir;
    private String currentFilePath;
    private Preferences preferences;
    GridLayoutManager layoutManager;
    private int numberOfColumns;
    private int dimens;

    public static CommonFragment newInstance(Bundle bundle) {
        CommonFragment currentFragment = new CommonFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        photoItemList = new ArrayList<>();
        preferences = new Preferences(context);
        numberOfColumns = PhotoManager.calculateNumberOfColumns(context);
        dimens = PhotoManager.calculateWidthOfPhoto(context, numberOfColumns);
        storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        layoutManager = new GridLayoutManager(context, numberOfColumns);
    }

    @NonNull
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        MainActivity activity = (MainActivity) view.getContext();
        activity.showFloatingActionButton();

        getFilesList();
        adapter = new RecyclerViewAdapter(photoItemList, dimens, preferences);
        adapter.setOnItemClickListener(this);

        recyclerView = view.findViewById(R.id.recycler_view_common);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        presenter = new CommonPresenterImpl(this, photoItemList);

        FloatingActionButton fab = activity.findViewById(R.id.fab_add_picture);
        fab.setOnClickListener(v -> capturePhoto());
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
    public void showDeletePhotoDialog(@NotNull View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle(R.string.ask_delete_photo)
                .setPositiveButton(R.string.ok_button, (dialog, which) -> deletePhoto(position))
                .setNegativeButton(R.string.cancel_button, (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void addOrRemoveFavorites(int position) {
        Set<String> favorites = preferences.getFavorites();
        if (photoItemList.get(position).isFavorites()) {
            photoItemList.get(position).setFavorites(false);
            favorites.remove(photoItemList.get(position).getPhotoPath());
        } else {
            photoItemList.get(position).setFavorites(true);
            favorites.add(photoItemList.get(position).getPhotoPath());
        }
        preferences.setFavorites(favorites);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void showFullPhoto(@NotNull View view, int position) {
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

    @NotNull
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
        //TODO 05.09.18 fix crash app at change orientation in camera
        photoItemList.add(new PhotoItem(currentFilePath, false));
        adapter.notifyItemInserted(photoItemList.size() - 1);
        Snackbar.make(recyclerView, R.string.photo_uploaded, Snackbar.LENGTH_SHORT).show();
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }
}