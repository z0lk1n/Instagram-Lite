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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import online.z0lk1n.android.instagram_lite.App;
import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.presentation.ui.Screens;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public final class MainTabFragment extends MvpAppCompatFragment {

    @BindView(R.id.bottom_navigation_view) BottomNavigationView bottomNavigationView;

    @Inject NavigatorHolder navigatorHolder;
    @Inject Router router;

    private Navigator navigator;

    public static MainTabFragment getNewInstance(Bundle bundle) {
        MainTabFragment currentFragment = new MainTabFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tab, container, false);

        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this, view);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> showFragment(item.getItemId()));

        return view;
    }

    private boolean showFragment(int itemId) {
        switch (itemId) {
            case R.id.action_common:
                router.navigateTo(new Screens.CommonScreen());
                return true;
            case R.id.action_network:
                router.navigateTo(new Screens.NetworkScreen());
                return true;
            case R.id.action_database:
                router.navigateTo(new Screens.DatabaseScreen());
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(getNavigator());
        showFragment(bottomNavigationView.getSelectedItemId());
    }

    @Override
    public void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    private Navigator getNavigator() {
        if (navigator == null) {
            navigator = new SupportAppNavigator(getActivity(), getChildFragmentManager(), R.id.main_tab_container);
        }
        return navigator;
    }
}