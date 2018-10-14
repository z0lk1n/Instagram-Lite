package online.z0lk1n.android.instagram_lite.presentation.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import online.z0lk1n.android.instagram_lite.presentation.ui.bottomtab.CommonFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.bottomtab.DatabaseFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.bottomtab.NetworkFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.fullscreenphoto.FullscreenPhotoActivity;
import online.z0lk1n.android.instagram_lite.presentation.ui.main.MainActivity;
import online.z0lk1n.android.instagram_lite.presentation.ui.settings.SettingsActivity;
import online.z0lk1n.android.instagram_lite.presentation.ui.settings.SettingsFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.toptab.FavoritesTabFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.toptab.MainTabFragment;
import online.z0lk1n.android.instagram_lite.util.Const;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static final class NetworkScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return NetworkFragment.getNewInstance(null);
        }
    }

    public static final class DatabaseScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return DatabaseFragment.getNewInstance(null);
        }
    }

    public static final class CommonScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return CommonFragment.getNewInstance(null);
        }
    }

    public static final class FullscreenPhotoScreen extends SupportAppScreen {
        private final String path;

        public FullscreenPhotoScreen(String path) {
            this.path = path;
        }

        @NonNull
        @Override
        public Intent getActivityIntent(Context context) {
            Intent intent = new Intent(context, FullscreenPhotoActivity.class);
            intent.putExtra(Const.KEY_FULLSCREEN_PHOTO, path);
            return intent;
        }
    }

    public static final class SettingsActivityScreen extends SupportAppScreen {
        @NonNull
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, SettingsActivity.class);
        }
    }

    public static final class SettingsFragmentScreen extends SupportAppScreen {
        @NonNull
        @Override
        public Fragment getFragment() {
            return new SettingsFragment();
        }
    }

    public static final class FavoritesTabScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return FavoritesTabFragment.getNewInstance(null);
        }
    }

    public static final class MainTabScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return MainTabFragment.getNewInstance(null);
        }
    }

    public static final class MainScreen extends SupportAppScreen {
        @NonNull
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, MainActivity.class);
        }
    }
}
