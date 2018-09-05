package online.z0lk1n.android.instagram_lite.util;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.ui.activity.SettingsActivity;
import online.z0lk1n.android.instagram_lite.ui.fragment.PhotoFragment;
import online.z0lk1n.android.instagram_lite.ui.fragment.PhotoGalleryFragment;
import online.z0lk1n.android.instagram_lite.ui.fragment.SettingsFragment;

public final class Navigator {
    private static final String TAG = "Navigator";

    public void showPhotoGalleryFragment(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        PhotoGalleryFragment photoGalleryFragment = (PhotoGalleryFragment) fragmentManager
                .findFragmentByTag(PhotoGalleryFragment.NAME);

        if (photoGalleryFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .show(photoGalleryFragment)
                    .commit();
        }

        fragmentManager.popBackStack();
    }

    public void showPhotoFragment(AppCompatActivity activity, String path) {
        new Preferences(activity).setPhoto(path);
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_tab, new PhotoFragment(), PhotoFragment.NAME)
                .addToBackStack(null)
                .commit();
    }

    public void openSettingsActivity(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, SettingsActivity.class));
    }

    public void showSettingsFragment(AppCompatActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.settings_container, new SettingsFragment(), SettingsActivity.NAME)
                .commit();
    }
}
