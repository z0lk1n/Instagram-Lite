package online.z0lk1n.android.photocollector.presentation.ui.fullscreenphoto;

import android.os.Bundle;
import android.os.Handler;
import android.support.media.ExifInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import online.z0lk1n.android.photocollector.App;
import online.z0lk1n.android.photocollector.R;
import online.z0lk1n.android.photocollector.presentation.presenters.fullscreenphoto.FullscreenPhotoPresenter;
import online.z0lk1n.android.photocollector.util.Const;
import online.z0lk1n.android.photocollector.util.PhotoManager;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public final class FullscreenPhotoActivity extends MvpAppCompatActivity implements FullscreenPhotoView {

    private static final int UI_ANIMATION_DELAY = 300;

    private final Handler hideHandler = new Handler();
    private final Runnable hideRunnable = this::hide;

    @BindView(R.id.toolbar_fullscreen) Toolbar toolbar;
    @BindView(R.id.fullscreen_photo) ImageView imageView;

    @Inject PhotoManager photoManager;
    @Inject NavigatorHolder navigatorHolder;

    private Navigator navigator = new SupportAppNavigator(this, -1);

    @InjectPresenter FullscreenPhotoPresenter presenter;

    @ProvidePresenter
    public FullscreenPhotoPresenter provideFullscreenPhotoPresenter() {
        FullscreenPhotoPresenter presenter = new FullscreenPhotoPresenter();
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    private final Runnable hidePart2Runnable = () ->
            imageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    private final Runnable showPart2Runnable = () -> {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        int width = photoManager.calculateWidthOfPhoto();
        int height = photoManager.calculateHeightOfPhoto();

        File file = new File(getIntent().getStringExtra(Const.KEY_FULLSCREEN_PHOTO));

        switch (photoManager.getOrientationPhoto(file.getAbsolutePath())) {
            case ExifInterface.ORIENTATION_NORMAL:
                photoManager.setPhoto(imageView, file, width, 0);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                photoManager.setPhoto(imageView, file, 0, height);
                break;
            default:
                photoManager.setPhoto(imageView, file, width, width);
                break;
        }

        imageView.setOnClickListener(view -> presenter.pushToggle());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hideHandler.removeCallbacks(hideRunnable);
        hideHandler.postDelayed(hideRunnable, UI_ANIMATION_DELAY);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void hide() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }

        hideHandler.removeCallbacks(showPart2Runnable);
        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @Override
    public void show() {
        imageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        hideHandler.removeCallbacks(hidePart2Runnable);
        hideHandler.postDelayed(showPart2Runnable, UI_ANIMATION_DELAY);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }
}
