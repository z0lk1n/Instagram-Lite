package online.z0lk1n.android.instagram_lite.util;

import android.support.v4.app.Fragment;

import org.jetbrains.annotations.Contract;

import online.z0lk1n.android.instagram_lite.presentation.ui.toptab.FavoritesTabFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.toptab.MainTabFragment;

public final class TabFragmentFactory {

    private static final String TAG = "TabFragmentFactory";
    private static final String[] TITLES = {"Main", "Favorites"};

    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return MainTabFragment.newInstance(null);
            case 1:
                return FavoritesTabFragment.newInstance(null);
            default:
                throw new IllegalArgumentException("Could not create fragment for position :" + position);
        }
    }

    @Contract(pure = true)
    public int getFragmentsCount() {
        return 2;
    }

    @Contract(pure = true)
    public CharSequence getFragmentTitle(int position) {
        return TITLES[position];
    }
}
