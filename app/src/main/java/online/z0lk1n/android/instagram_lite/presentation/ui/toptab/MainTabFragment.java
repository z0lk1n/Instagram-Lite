package online.z0lk1n.android.instagram_lite.presentation.ui.toptab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.util.Navigator;

public final class MainTabFragment extends MvpAppCompatFragment {

    public static final String NAME = "6a4545d5-b082-40b6-afc7-87e365395a57";

    private Navigator navigator;

    @BindView(R.id.bottom_navigation_view) BottomNavigationView bottomNavigationView;

    public static MainTabFragment newInstance(Bundle bundle) {
        MainTabFragment currentFragment = new MainTabFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tab, container, false);
        ButterKnife.bind(this, view);
        navigator = new Navigator();
        showFragment(bottomNavigationView.getSelectedItemId());
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> showFragment(item.getItemId()));
        return view;
    }

    private boolean showFragment(int itemId) {
        switch (itemId) {
            case R.id.action_common:
                navigator.showCommonFragment(this);
                return true;
            case R.id.action_network:
                navigator.showNetworkFragment(this);
                return true;
            case R.id.action_database:
                navigator.showDatabaseFragment(this);
                return true;
            default:
                return false;
        }
    }
}