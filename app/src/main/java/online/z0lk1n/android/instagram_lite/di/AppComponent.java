package online.z0lk1n.android.instagram_lite.di;

import javax.inject.Singleton;

import dagger.Component;
import online.z0lk1n.android.instagram_lite.di.modules.AppModule;
import online.z0lk1n.android.instagram_lite.di.modules.FileManagerModule;
import online.z0lk1n.android.instagram_lite.di.modules.NavigationModule;
import online.z0lk1n.android.instagram_lite.di.modules.PhotoManagerModule;
import online.z0lk1n.android.instagram_lite.di.modules.ResourcesModule;
import online.z0lk1n.android.instagram_lite.di.modules.SchedulersModule;
import online.z0lk1n.android.instagram_lite.presentation.presenters.bottomtab.CommonPresenter;
import online.z0lk1n.android.instagram_lite.presentation.presenters.bottomtab.DatabasePresenter;
import online.z0lk1n.android.instagram_lite.presentation.presenters.bottomtab.NetworkPresenter;
import online.z0lk1n.android.instagram_lite.presentation.presenters.fullscreenphoto.FullscreenPhotoPresenter;
import online.z0lk1n.android.instagram_lite.presentation.presenters.toptab.FavoritesTabPresenter;
import online.z0lk1n.android.instagram_lite.presentation.presenters.toptab.MainTabPresenter;
import online.z0lk1n.android.instagram_lite.presentation.ui.bottomtab.CommonFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.bottomtab.DatabaseFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.bottomtab.NetworkFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.fullscreenphoto.FullscreenPhotoActivity;
import online.z0lk1n.android.instagram_lite.presentation.ui.main.MainActivity;
import online.z0lk1n.android.instagram_lite.presentation.ui.settings.SettingsActivity;
import online.z0lk1n.android.instagram_lite.presentation.ui.toptab.FavoritesTabFragment;
import online.z0lk1n.android.instagram_lite.presentation.ui.toptab.MainTabFragment;

@Singleton
@Component(modules = {
        AppModule.class,
        NavigationModule.class,
        PhotoManagerModule.class,
        FileManagerModule.class,
        ResourcesModule.class,
        SchedulersModule.class
})
public interface AppComponent {

    void inject(CommonFragment fragment);

    void inject(CommonPresenter presenter);

    void inject(DatabaseFragment fragment);

    void inject(DatabasePresenter presenter);

    void inject(NetworkFragment fragment);

    void inject(NetworkPresenter presenter);

    void inject(FavoritesTabFragment fragment);

    void inject(FavoritesTabPresenter presenter);

    void inject(MainTabFragment fragment);

    void inject(MainTabPresenter presenter);

    void inject(FullscreenPhotoActivity activity);

    void inject(FullscreenPhotoPresenter presenter);

    void inject(SettingsActivity activity);

    void inject(MainActivity activity);
}
