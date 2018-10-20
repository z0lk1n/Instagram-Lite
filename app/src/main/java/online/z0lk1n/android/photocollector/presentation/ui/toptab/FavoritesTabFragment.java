package online.z0lk1n.android.photocollector.presentation.ui.toptab;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import online.z0lk1n.android.photocollector.App;
import online.z0lk1n.android.photocollector.R;
import online.z0lk1n.android.photocollector.data.database.PhotoEntity;
import online.z0lk1n.android.photocollector.presentation.presenters.toptab.FavoritesTabPresenter;
import online.z0lk1n.android.photocollector.util.PhotoManager;
import online.z0lk1n.android.photocollector.util.RecyclerViewAdapter;

public final class FavoritesTabFragment extends MvpAppCompatFragment
        implements RecyclerViewAdapter.OnItemClickListener, FavoritesTabView {

    private RecyclerViewAdapter adapter;
    private AlertDialog alertDialog;

    @Inject PhotoManager photoManager;

    @BindView(R.id.recycler_view_favorites) RecyclerView recyclerView;

    @InjectPresenter FavoritesTabPresenter presenter;

    @ProvidePresenter
    FavoritesTabPresenter provideFavoritesTabPresenter() {
        FavoritesTabPresenter presenter = new FavoritesTabPresenter();
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static FavoritesTabFragment getNewInstance(Bundle bundle) {
        FavoritesTabFragment currentFragment = new FavoritesTabFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_tab, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        App.getInstance().getAppComponent().inject(this);

        ButterKnife.bind(this, view);

        adapter = new RecyclerViewAdapter(photoManager, photoManager.calculateWidthOfPhoto());
        adapter.setOnItemClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), photoManager.calculateNumberOfColumns());

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
    public void onPhotoClick(String photoPath) {
        presenter.showFullPhoto(photoPath);
    }

    @Override
    public void onPhotoLongClick(String photoPath) {
        presenter.onPhotoLongClick(photoPath);
    }

    @Override
    public void onFavoritesClick(boolean isChecked, String photoPath) {
        presenter.onFavoritesClick(isChecked, photoPath);
    }

    @Override
    public void showNotifyingMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void notifyItemRemoved(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void showDeletePhotoDialog(final String photoPath) {
        if (getContext() == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.ask_delete_photo)
                .setPositiveButton(R.string.ok_button, (dialog, which) -> presenter.deletePhoto(photoPath))
                .setNegativeButton(R.string.cancel_button, (dialog, which) -> presenter.closeDialog());
        alertDialog = builder.show();
    }

    @Override
    public void updatePhotoList(List<PhotoEntity> photoItems) {
        adapter.addItems(photoItems);
    }

    @Override
    public void closeDialog() {
        if (alertDialog == null) {
            return;
        }
        alertDialog.dismiss();
        alertDialog = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            presenter.visibleToUser();
        }
    }
}
