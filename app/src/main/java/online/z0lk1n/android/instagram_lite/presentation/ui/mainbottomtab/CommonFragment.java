package online.z0lk1n.android.instagram_lite.presentation.ui.mainbottomtab;

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
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;
import online.z0lk1n.android.instagram_lite.presentation.mvp.mainbottomtab.CommonPresenter;
import online.z0lk1n.android.instagram_lite.presentation.mvp.mainbottomtab.CommonView;
import online.z0lk1n.android.instagram_lite.util.Const;
import online.z0lk1n.android.instagram_lite.util.Navigator;
import online.z0lk1n.android.instagram_lite.util.adapters.RecyclerViewAdapter;
import online.z0lk1n.android.instagram_lite.util.managers.AndroidResourceManager;
import online.z0lk1n.android.instagram_lite.util.managers.PhotoManager;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public final class CommonFragment extends MvpAppCompatFragment
        implements RecyclerViewAdapter.OnItemClickListener, CommonView {

    public static final String NAME = "cb2d00bb-ca6b-45e6-a501-80f70efa65b9";
    private static final String TAG = "CommonFragment";

    @BindView(R.id.recycler_view_common)
    RecyclerView recyclerView;
    @BindView(R.id.fab_add_picture)
    FloatingActionButton fab;

    @InjectPresenter
    CommonPresenter presenter;

    @NotNull
    @ProvidePresenter
    CommonPresenter provideCommonPresenter() {
        return new CommonPresenter(new AndroidResourceManager(getContext()));
    }

    private RecyclerViewAdapter adapter;
    private File storageDir;
    private GridLayoutManager layoutManager;
    private int dimens;
    private String currentFilePath;

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
        ButterKnife.bind(this, view);

        adapter = new RecyclerViewAdapter(dimens);
        adapter.setOnItemClickListener(this);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> presenter.capturePhoto());
    }

    @Override
    public void startCamera(String fileName, String suffix) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getActivity() != null) {
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = File.createTempFile(fileName, suffix, storageDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    currentFilePath = photoFile.getAbsolutePath();

                    Uri photoUri = FileProvider.getUriForFile(
                            getActivity(),
                            getString(R.string.package_name),
                            photoFile);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePictureIntent, Const.PHOTO_CAMERA_REQUEST);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.PHOTO_CAMERA_REQUEST) {
            switch (resultCode) {
                case RESULT_OK:
                    presenter.addPhoto(currentFilePath);
                    break;
                case RESULT_CANCELED:
                    presenter.failCapturePhoto(currentFilePath);
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
    public void showFullPhoto(String photoPath) {
        new Navigator().openFullscreenPhotoActivity(getContext(), photoPath);
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

    @Override
    public void fillPhotoList(List<PhotoItem> photoItems) {
        if (photoItems.size() != storageDir.listFiles().length) {
            for (File file : storageDir.listFiles()) {
                photoItems.add(new PhotoItem(file.getPath(), false));
            }
            adapter.addItems(photoItems);
        }
    }
}