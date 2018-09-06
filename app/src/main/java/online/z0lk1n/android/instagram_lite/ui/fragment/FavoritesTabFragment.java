package online.z0lk1n.android.instagram_lite.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.model.PhotoItem;
import online.z0lk1n.android.instagram_lite.ui.activity.MainActivity;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;
import online.z0lk1n.android.instagram_lite.util.Preferences;
import online.z0lk1n.android.instagram_lite.util.RecyclerViewAdapter;

public class FavoritesTabFragment extends Fragment
        implements RecyclerViewAdapter.OnItemClickListener {

    public static final String NAME = "187f27ee-e044-4772-a683-858eaa67a0f4";
    private static final String TAG = "FavoritesTabFragment";

    private RecyclerViewAdapter adapter;
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
        ((MainActivity) getActivity()).hideFloatingActionButton();

        Preferences preferences = new Preferences(getActivity());

        List<PhotoItem> photoItemList = new ArrayList<>();
        for (String s : preferences.getFavorites()) {
            photoItemList.add(new PhotoItem(s, true));
        }

        adapter = new RecyclerViewAdapter(photoItemList, dimens);
        adapter.setOnItemClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_favorites);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.setOnItemClickListener(null);
    }

    @Override
    public void onPhotoClick(View view, int position) {

    }

    @Override
    public void onPhotoLongClick(View view, int position) {

    }

    @Override
    public void onFavoritesClick(int position) {

    }
}
