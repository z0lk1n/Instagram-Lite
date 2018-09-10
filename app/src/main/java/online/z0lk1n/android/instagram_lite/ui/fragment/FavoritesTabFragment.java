package online.z0lk1n.android.instagram_lite.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.model.PhotoItem;
import online.z0lk1n.android.instagram_lite.util.Navigator;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;
import online.z0lk1n.android.instagram_lite.util.Preferences;
import online.z0lk1n.android.instagram_lite.util.RecyclerViewAdapter;

public final class FavoritesTabFragment extends Fragment
        implements RecyclerViewAdapter.OnItemClickListener {

    public static final String NAME = "187f27ee-e044-4772-a683-858eaa67a0f4";
    private static final String TAG = "FavoritesTabFragment";

    private Preferences preferences;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<PhotoItem> photoItemList;
    private int numberOfColumns;
    private int dimens;

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
        preferences = new Preferences(context);
        numberOfColumns = PhotoManager.calculateNumberOfColumns(context);
        dimens = PhotoManager.calculateWidthOfPhoto(context, numberOfColumns);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_tab, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        photoItemList = new ArrayList<>();
        for (String s : preferences.getFavorites()) {
            photoItemList.add(new PhotoItem(s, true));
        }

        adapter = new RecyclerViewAdapter(photoItemList, dimens, preferences);
        adapter.setOnItemClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);

        recyclerView = view.findViewById(R.id.recycler_view_favorites);
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
                (AppCompatActivity) getContext(),
                photoItemList.get(position).getPhotoPath());
    }

    @Override
    public void onPhotoLongClick(int position) {
        showDeletePhotoDialog(position);
    }

    @Override
    public void onFavoritesClick(int position) {
        Set<String> favorites = preferences.getFavorites();
        favorites.remove(photoItemList.get(position).getPhotoPath());
        preferences.setFavorites(favorites);
        photoItemList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void showDeletePhotoDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.ask_delete_photo)
                .setPositiveButton(R.string.ok_button, (dialog, which) -> deletePhoto(position))
                .setNegativeButton(R.string.cancel_button, (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void deletePhoto(int position) {
        if (new File(photoItemList.get(position).getPhotoPath()).delete()) {
            Set<String> favorites = preferences.getFavorites();
            favorites.remove(photoItemList.get(position).getPhotoPath());
            preferences.setFavorites(favorites);

            photoItemList.remove(position);
            adapter.notifyItemRemoved(position);
            Snackbar.make(recyclerView, R.string.photo_deleted, Snackbar.LENGTH_SHORT).show();
        }
    }
}
