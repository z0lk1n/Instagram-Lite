package online.z0lk1n.android.photocollector.di;

import javax.inject.Singleton;

import dagger.Component;
import online.z0lk1n.android.photocollector.di.modules.TestPhotosModule;
import online.z0lk1n.android.photocollector.presentation.presenters.bottomtab.NetworkPresenter;

@Singleton
@Component(modules = TestPhotosModule.class)
public interface TestComponent {
    void inject(NetworkPresenter presenter);
}
