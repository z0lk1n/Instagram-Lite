package online.z0lk1n.android.instagram_lite.util;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import online.z0lk1n.android.instagram_lite.presentation.ui.Screens;

public final class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TITLES = {"Main", "Favorites"};

    public CustomFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        return createFragment(i);
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    private Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Screens.MainTabScreen().getFragment();
            case 1:
                return new Screens.FavoritesTabScreen().getFragment();
            default:
                throw new IllegalArgumentException("Could not create fragment for position :" + position);
        }
    }
}
