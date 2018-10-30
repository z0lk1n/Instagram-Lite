package online.z0lk1n.android.photocollector;

import android.app.Application;

import com.facebook.stetho.Stetho;

import online.z0lk1n.android.photocollector.di.AppComponent;
import online.z0lk1n.android.photocollector.di.DaggerAppComponent;
import online.z0lk1n.android.photocollector.di.modules.AppModule;
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

        // chrome://inspect/#devices
        Stetho.initializeWithDefaults(this);
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
