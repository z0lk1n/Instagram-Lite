package online.z0lk1n.android.photocollector.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import online.z0lk1n.android.photocollector.App;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public App provideApp() {
        return app;
    }

    @Singleton
    @Provides
    public Context provideContext()  {
        return app.getApplicationContext();
    }
}
