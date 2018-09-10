package online.z0lk1n.android.instagram_lite.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.media.ExifInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.util.Const;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;

public class FullscreenPhotoActivity extends BaseActivity {

    public static final String NAME = "b9e6ee8c-3f43-457d-ad77-4d99891ef7bc";
    private static final String TAG = "FullscreenPhotoActivity";

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler hideHandler = new Handler();
    private ImageView imageView;

    private final Runnable hidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            imageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = () -> {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    };

    private boolean mVisible;
    private final Runnable mHideRunnable = () -> hide();

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);

        Toolbar toolbar = findViewById(R.id.toolbar_fullscreen);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mVisible = true;
        imageView = findViewById(R.id.fullscreen_photo);

        int width = PhotoManager.calculateWidthOfPhoto(this, 1);
        int height = PhotoManager.calculateHeightOfPhoto(this);

        File file = new File(getIntent().getStringExtra(Const.KEY_FULLSCREEN_PHOTO));

        switch (PhotoManager.getOrientationPhoto(file.getPath())) {
            case ExifInterface.ORIENTATION_NORMAL:
                Picasso.get().load(file).resize(width, 0).into(imageView);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                Picasso.get().load(file).resize(0, height).into(imageView);
                break;
            default:
                Picasso.get().load(file).resize(width, width).into(imageView);
                break;
        }

        imageView.setOnClickListener(view -> toggle());
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        hideHandler.removeCallbacks(mShowPart2Runnable);
        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        imageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        hideHandler.removeCallbacks(hidePart2Runnable);
        hideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        hideHandler.removeCallbacks(mHideRunnable);
        hideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
