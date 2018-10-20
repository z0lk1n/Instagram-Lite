package online.z0lk1n.android.photocollector.presentation.ui.bottomtab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import org.jetbrains.annotations.NotNull;

import online.z0lk1n.android.photocollector.App;
import online.z0lk1n.android.photocollector.R;
import online.z0lk1n.android.photocollector.presentation.presenters.bottomtab.DatabasePresenter;

public final class DatabaseFragment extends MvpAppCompatFragment implements DatabaseView {

    @InjectPresenter DatabasePresenter presenter;

    @NonNull
    @ProvidePresenter
    DatabasePresenter provideDatabasePresenter() {
        DatabasePresenter presenter = new DatabasePresenter();
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static DatabaseFragment getNewInstance(Bundle bundle) {
        DatabaseFragment currentFragment = new DatabaseFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        App.getInstance().getAppComponent().inject(this);
    }
}
