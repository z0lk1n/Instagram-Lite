package online.z0lk1n.android.photocollector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;
import online.z0lk1n.android.photocollector.data.database.PhotoEntity;
import online.z0lk1n.android.photocollector.data.model.Photos;
import online.z0lk1n.android.photocollector.di.TestComponent;
import online.z0lk1n.android.photocollector.di.modules.TestPhotosModule;
import online.z0lk1n.android.photocollector.presentation.presenters.bottomtab.NetworkPresenter;
import online.z0lk1n.android.photocollector.presentation.ui.bottomtab.NetworkView;
import online.z0lk1n.android.photocollector.util.ApiConst;

public class NetworkPresenterUnitTest {

    private NetworkPresenter presenter;
    private TestScheduler testScheduler;

    @Mock NetworkView view;

    @BeforeClass
    public static void setupClass() {

    }

    @AfterClass
    public static void tearDown() {

    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        presenter = Mockito.spy(new NetworkPresenter());
    }

    @After
    public void after() {

    }

    @Test
    public void loadPhotosTestSuccess() {
        List<PhotoEntity> photoEntities = new ArrayList<>();
        photoEntities.add(new PhotoEntity("test", true, false));

        TestComponent component = DaggerTestComponent.builder()
                .testPhotosModule(new TestPhotosModule() {
                    @Override
                    public Photos providePhotos() {
                        Photos photos = super.providePhotos();
                        Mockito.when(photos.getPhotos(ApiConst.ACCESS_KEY)).thenReturn(Single.just(photoEntities));
                        return photos;
                    }
                }).build();

        component.inject(presenter);
        presenter.attachView(view);
        Mockito.verify(presenter).loadPhotos();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Mockito.verify(view).hideLoading();
    }

    @Test
    public void loadPhotosTestFailure() {
        Throwable throwable = new RuntimeException("No photo list");

        TestComponent component = DaggerTestComponent.builder()
                .testPhotosModule(new TestPhotosModule() {
                    @Override
                    public Photos providePhotos() {
                        Photos photos = super.providePhotos();
                        Mockito.when(photos.getPhotos(ApiConst.ACCESS_KEY)).thenReturn(Single.error(throwable));
                        return photos;
                    }
                }).build();

        component.inject(presenter);
        presenter.attachView(view);
        Mockito.verify(presenter).loadPhotos();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Mockito.verify(view).hideLoading();
        Mockito.verify(view).showNotifyingMessage(throwable.getMessage());
    }
}
