package online.z0lk1n.android.instagram_lite;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.jetbrains.annotations.Contract;

import online.z0lk1n.android.instagram_lite.di.AppComponent;
import online.z0lk1n.android.instagram_lite.di.DaggerAppComponent;
import online.z0lk1n.android.instagram_lite.di.modules.AppModule;
import timber.log.Timber;

public class App extends Application {

    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        Timber.plant(new Timber.DebugTree());
        Stetho.initializeWithDefaults(this);
    }

    @Contract(pure = true)
    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
