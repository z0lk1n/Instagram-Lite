package online.z0lk1n.android.instagram_lite.presentation.ui.toptab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.data.repositories.PhotoRepositoryImpl;
import online.z0lk1n.android.instagram_lite.util.Navigator;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;
import online.z0lk1n.android.instagram_lite.util.PhotoManagerImpl;
import online.z0lk1n.android.instagram_lite.util.RecyclerViewAdapter;

public final class FavoritesTabFragment extends MvpAppCompatFragment
        implements RecyclerViewAdapter.OnItemClickListener {

    public static final String NAME = "187f27ee-e044-4772-a683-858eaa67a0f4";

    private RecyclerViewAdapter adapter;
    private PhotoRepositoryImpl photoRepository;
    private PhotoManager photoManager;
    private int numberOfColumns;
    private int dimens;

    @BindView(R.id.recycler_view_favorites) RecyclerView recyclerView;

    public static FavoritesTabFragment newInstance(Bundle bundle) {
        FavoritesTabFragment currentFragment = new FavoritesTabFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        photoManager = new PhotoManagerImpl(context);
        numberOfColumns = photoManager.calculateNumberOfColumns();
        dimens = photoManager.calculateWidthOfPhoto();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_tab, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);

        adapter = new RecyclerViewAdapter(photoManager, dimens);
        adapter.setOnItemClickListener(this);

        photoRepository = PhotoRepositoryImpl.getInstance();
        adapter.addItems(photoRepository.getPhotoList());

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.setOnItemClickListener(null);
    }

    @Override
    public void onPhotoClick(int position) {
        new Navigator().openFullscreenPhotoActivity(
                getContext(),
                photoRepository.getPhotoPath(position));
    }

    @Override
    public void onPhotoLongClick(int position) {
        showDeletePhotoDialog(position);
    }

    @Override
    public void onFavoritesClick(boolean isChecked, int position) {
        photoRepository.changeFavorites(position, isChecked);
//        adapter.notifyItemRemoved(position);
    }

    private void showDeletePhotoDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.ask_delete_photo)
                .setPositiveButton(R.string.ok_button, (dialog, which) -> deletePhoto(position))
                .setNegativeButton(R.string.cancel_button, (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void deletePhoto(int position) {
        if (new File(photoRepository.getPhotoPath(position)).delete()) {
            photoRepository.removePhoto(position);
            adapter.notifyItemRemoved(position);
            Snackbar.make(recyclerView, R.string.photo_deleted, Snackbar.LENGTH_SHORT).show();
        }
    }
}
