package online.z0lk1n.android.instagram_lite.util;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.ui.activity.SettingsActivity;
import online.z0lk1n.android.instagram_lite.ui.fragment.CommonFragment;
import online.z0lk1n.android.instagram_lite.ui.fragment.DatabaseFragment;
import online.z0lk1n.android.instagram_lite.ui.fragment.NetworkFragment;
import online.z0lk1n.android.instagram_lite.ui.fragment.PhotoFragment;
import online.z0lk1n.android.instagram_lite.ui.fragment.SettingsFragment;

public final class Navigator {

    private static final String TAG = "Navigator";

    public void openSettingsActivity(@NotNull AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, SettingsActivity.class));
    }

    public void showSettingsFragment(@NotNull AppCompatActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.settings_container, new SettingsFragment(), SettingsActivity.NAME)
                .commit();
    }

    public void showCommonFragment(@NotNull Fragment fragment, int start) {
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        if (fragmentManager.findFragmentByTag(CommonFragment.NAME) == null) {
            switch (start) {
                case Const.FIRST_START_FRAGMENT:
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.container_main_tab, CommonFragment.newInstance(null), CommonFragment.NAME)
                            .commit();
                    break;
                case Const.NEXT_START_FRAGMENT:
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.container_main_tab, CommonFragment.newInstance(null), CommonFragment.NAME)
                            .commit();
                    break;
                default:
                    break;
            }
        }
    }

    public void showNetworkFragment(@NotNull Fragment fragment, int start) {
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        if (fragmentManager.findFragmentByTag(NetworkFragment.NAME) == null) {
            switch (start) {
                case Const.FIRST_START_FRAGMENT:
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.container_main_tab, NetworkFragment.newInstance(null), NetworkFragment.NAME)
                            .commit();
                    break;
                case Const.NEXT_START_FRAGMENT:
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.container_main_tab, NetworkFragment.newInstance(null), NetworkFragment.NAME)
                            .commit();
                    break;
                default:
                    break;
            }
        }
    }

    public void showDatabaseFragment(@NotNull Fragment fragment, int start) {
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        if (fragmentManager.findFragmentByTag(DatabaseFragment.NAME) == null) {
            switch (start) {
                case Const.FIRST_START_FRAGMENT:
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.container_main_tab, DatabaseFragment.newInstance(null), DatabaseFragment.NAME)
                            .commit();
                    break;
                case Const.NEXT_START_FRAGMENT:
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.container_main_tab, DatabaseFragment.newInstance(null), DatabaseFragment.NAME)
                            .commit();
                    break;
                default:
                    break;
            }
        }
    }

    public void showPhotoFragment(AppCompatActivity activity, String path) {
        new Preferences(activity).setPhoto(path);
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_tab, new PhotoFragment(), PhotoFragment.NAME)
                .addToBackStack(null)
                .commit();
    }
}
