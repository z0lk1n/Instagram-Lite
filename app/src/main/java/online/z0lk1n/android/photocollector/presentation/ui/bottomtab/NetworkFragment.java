package online.z0lk1n.android.photocollector.presentation.ui.bottomtab;

import android.content.Context;
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
import android.widget.ProgressBar;

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
import online.z0lk1n.android.photocollector.presentation.presenters.bottomtab.NetworkPresenter;
import online.z0lk1n.android.photocollector.util.PhotoManager;
import online.z0lk1n.android.photocollector.util.RecyclerViewAdapter;

public final class NetworkFragment extends MvpAppCompatFragment
        implements RecyclerViewAdapter.OnItemClickListener, NetworkView {

    private RecyclerViewAdapter adapter;

    @BindView(R.id.pb_network) ProgressBar progressBar;
    @BindView(R.id.rv_network) RecyclerView recyclerView;

    @InjectPresenter NetworkPresenter presenter;

    @Inject PhotoManager photoManager;

    @NonNull
    @ProvidePresenter
    NetworkPresenter provideNetworkPresenter() {
        NetworkPresenter presenter = new NetworkPresenter();
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static NetworkFragment getNewInstance(Bundle bundle) {
        NetworkFragment currentFragment = new NetworkFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        App.getInstance().getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_network, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
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
    public void onDetach() {
        super.onDetach();
        adapter.setOnItemClickListener(null);
    }

    @Override
    public void updatePhotoList(List<PhotoEntity> photoEntities) {
        adapter.addItems(photoEntities);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showNotifyingMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
