package online.z0lk1n.android.instagram_lite.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import online.z0lk1n.android.instagram_lite.R;

public class MainTabFragment extends Fragment {

    public static final String NAME = "6a4545d5-b082-40b6-afc7-87e365395a57";
    private static final String TAG = "MainTabFragment";

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

        FragmentManager fragmentManager = getChildFragmentManager();

        if (fragmentManager.findFragmentByTag(PhotoGalleryFragment.NAME) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_main_tab, PhotoGalleryFragment.newInstance(null), PhotoGalleryFragment.NAME);
            fragmentTransaction.commit();
        }

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_common:
                            Fragment commonFragment = fragmentManager.findFragmentByTag(PhotoGalleryFragment.NAME);
                            if (commonFragment == null) {
                                commonFragment = PhotoGalleryFragment.newInstance(null);
                            }
                            FragmentTransaction fragmentTransactionE = fragmentManager.beginTransaction();
                            fragmentTransactionE.replace(
                                    R.id.container_main_tab,
                                    commonFragment,
                                    PhotoGalleryFragment.NAME);
                            fragmentTransactionE.commit();
                            return true;
                        case R.id.action_network:
                            FragmentTransaction fragmentTransactionM = fragmentManager.beginTransaction();
                            fragmentTransactionM.replace(
                                    R.id.container_main_tab,
                                    NetworkFragment.newInstance(null),
                                    NetworkFragment.NAME);
                            fragmentTransactionM.commit();
                            return true;
                        case R.id.action_database:
                            FragmentTransaction fragmentTransactionF = fragmentManager.beginTransaction();
                            fragmentTransactionF.replace(
                                    R.id.container_main_tab,
                                    DatabaseFragment.newInstance(null),
                                    DatabaseFragment.NAME);
                            fragmentTransactionF.commit();
                            return true;
                    }
                    return false;
                });
        return view;
    }
}
