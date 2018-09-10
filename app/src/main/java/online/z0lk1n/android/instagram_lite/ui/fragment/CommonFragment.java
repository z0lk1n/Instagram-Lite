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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.model.PhotoItem;
import online.z0lk1n.android.instagram_lite.presenter.AndroidResourceManager;
import online.z0lk1n.android.instagram_lite.presenter.CommonPresenter;
import online.z0lk1n.android.instagram_lite.presenter.CommonPresenterImpl;
import online.z0lk1n.android.instagram_lite.util.Const;
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

    private CommonPresenterImpl presenter;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private List<PhotoItem> photoItemList;
    private File storageDir;
    private Preferences preferences;
    private GridLayoutManager layoutManager;
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
        storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        int numberOfColumns = PhotoManager.calculateNumberOfColumns(context);
        dimens = PhotoManager.calculateWidthOfPhoto(context, numberOfColumns);
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

    private void init(@NotNull View view) {
        getFilesList();
        adapter = new RecyclerViewAdapter(photoItemList, dimens, preferences);
        adapter.setOnItemClickListener(this);

        recyclerView = view.findViewById(R.id.recycler_view_common);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        presenter = new CommonPresenterImpl(
                this,
                photoItemList,
                storageDir,
                preferences,
                new AndroidResourceManager(view.getContext()));

        FloatingActionButton fab = view.findViewById(R.id.fab_add_picture);
        fab.setOnClickListener(v -> presenter.capturePhoto());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.PHOTO_CAMERA_REQUEST) {
            switch (resultCode) {
                case RESULT_OK:
                    presenter.addPhoto();
                    break;
                case RESULT_CANCELED:
                    presenter.deletePhoto(Const.OUT_OF_ARRAY_POSITION);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onPhotoClick(int position) {
        presenter.onPhotoClick(position);
    }

    @Override
    public void onPhotoLongClick(int position) {
        presenter.onPhotoLongClick(position);
    }

    @Override
    public void onFavoritesClick(boolean isChecked, int position) {
        presenter.onFavoritesClick(isChecked, position);
    }

    @Override
    public void showDeletePhotoDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.ask_delete_photo)
                .setPositiveButton(R.string.ok_button, (dialog, which) -> presenter.deletePhoto(position))
                .setNegativeButton(R.string.cancel_button, (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void showFullPhoto(int position) {
        new Navigator().openFullscreenPhotoActivity(
                getContext(),
                photoItemList.get(position).getPhotoPath());
    }

    @Override
    public void startCamera(File file) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getActivity() != null) {
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                Uri photoUri = FileProvider.getUriForFile(
                        getActivity(),
                        getString(R.string.package_name),
                        file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, Const.PHOTO_CAMERA_REQUEST);
            }
        }
    }

    @Override
    public void showNotifyingMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void notifyItem(int position, int action) {
        switch (action) {
            case Const.NOTIFY_ITEM_INSERT:
                adapter.notifyItemInserted(position);
                break;
            case Const.NOTIFY_ITEM_REMOVE:
                adapter.notifyItemRemoved(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.setOnItemClickListener(null);
    }

    private void getFilesList() {
        if (photoItemList.size() != storageDir.listFiles().length) {
            for (File file : storageDir.listFiles()) {
                photoItemList.add(new PhotoItem(file.getPath(), false));
            }
        }
    }
}