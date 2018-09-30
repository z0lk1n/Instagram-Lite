package online.z0lk1n.android.instagram_lite.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.presentation.ui.fullscreenphoto.FullscreenPhotoActivity;
import online.z0lk1n.android.instagram_lite.presentation.ui.mainbottomtab.CommonFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.mainbottomtab.DatabaseFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.mainbottomtab.NetworkFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.settings.SettingsActivity;
import online.z0lk1n.android.instagram_lite.presentation.ui.settings.SettingsFragment;

public final class Navigator {

    public void openSettingsActivity(@NotNull AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, SettingsActivity.class));
    }

    public void showSettingsFragment(@NotNull AppCompatActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.settings_container, new SettingsFragment(), SettingsActivity.NAME)
                .commit();
    }

    public void showCommonFragment(@NotNull Fragment fragment) {
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        if (fragmentManager.findFragmentByTag(CommonFragment.NAME) == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main_tab, CommonFragment.newInstance(null), CommonFragment.NAME)
                    .commit();
        }
    }

    public void showNetworkFragment(@NotNull Fragment fragment) {
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        if (fragmentManager.findFragmentByTag(NetworkFragment.NAME) == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main_tab, NetworkFragment.newInstance(null), NetworkFragment.NAME)
                    .commit();
        }
    }

    public void showDatabaseFragment(@NotNull Fragment fragment) {
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        if (fragmentManager.findFragmentByTag(DatabaseFragment.NAME) == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main_tab, DatabaseFragment.newInstance(null), DatabaseFragment.NAME)
                    .commit();
        }
    }

    public void openFullscreenPhotoActivity(@NotNull Context context, String path) {
        Intent intent = new Intent(context, FullscreenPhotoActivity.class);
        intent.putExtra(Const.KEY_FULLSCREEN_PHOTO, path);
        context.startActivity(intent);
    }
}
