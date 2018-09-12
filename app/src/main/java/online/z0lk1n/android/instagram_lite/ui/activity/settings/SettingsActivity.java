package online.z0lk1n.android.instagram_lite.ui.activity.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.ui.activity.BaseActivity;
import online.z0lk1n.android.instagram_lite.util.Navigator;

public final class SettingsActivity extends BaseActivity {

    public static final String NAME = "a845ed06-721b-4da3-af5a-7f867b5a80b3";
    private static final String TAG = "SettingsActivity";

    @BindView(R.id.toolbar_settings) Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if (savedInstanceState == null) {
            new Navigator().showSettingsFragment(this);
        }
    }

    private void init() {
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}