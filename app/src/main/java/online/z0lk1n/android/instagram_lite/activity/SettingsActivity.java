package online.z0lk1n.android.instagram_lite.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.util.Preferences;

public class SettingsActivity extends AppCompatActivity {
    public static final String NAME = "a845ed06-721b-4da3-af5a-7f867b5a80b3";
    private static final String TAG = "SettingsActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(new Preferences(this).getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
        if (savedInstanceState == null) {
            new Navigator().showSettingsFragment(this);
        }
    }
}