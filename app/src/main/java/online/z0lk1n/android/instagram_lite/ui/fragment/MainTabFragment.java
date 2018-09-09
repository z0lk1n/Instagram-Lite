package online.z0lk1n.android.instagram_lite.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.util.Navigator;

public final class MainTabFragment extends Fragment {

    public static final String NAME = "6a4545d5-b082-40b6-afc7-87e365395a57";
    private static final String TAG = "MainTabFragment";

    private Navigator navigator;

    public static MainTabFragment newInstance(Bundle bundle) {
        MainTabFragment currentFragment = new MainTabFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tab, container, false);
        navigator = new Navigator();
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);
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