package online.z0lk1n.android.instagram_lite.util;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public final class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "CustomFragmentPagerAdapter";

    private final TabFragmentFactory fragmentFactory;

    public CustomFragmentPagerAdapter(FragmentManager fm, TabFragmentFactory fragmentFactory) {
        super(fm);
        this.fragmentFactory = fragmentFactory;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentFactory.createFragment(i);
    }

    @Override
    public int getCount() {
        return fragmentFactory.getFragmentsCount();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentFactory.getFragmentTitle(position);
    }
}
