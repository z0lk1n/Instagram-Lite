package online.z0lk1n.android.instagram_lite.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.fragment.PhotoGalleryFragment;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private Navigator navigator;
    private PhotoGalleryFragment photoGalleryFragment;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, photoGalleryFragment, PhotoGalleryFragment.NAME)
                    .commit();
        }
    }

    private void init() {
        setContentView(R.layout.activity_main);
        photoGalleryFragment = new PhotoGalleryFragment();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigator = new Navigator();
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        fab = findViewById(R.id.fab_add_picture);
        fab.setOnClickListener(v -> photoGalleryFragment.capturePhoto());
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            navigator.openSettingsActivity(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                navigator.showPhotoGalleryFragment(this);
                break;
            case R.id.settings:
                navigator.openSettingsActivity(this);
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showFloatingActionButton() {
        fab.show();
    };

    public void hideFloatingActionButton() {
        fab.hide();
    };
}

//TODO 03.09.18 change adapter
//TODO 03.09.18 fix crash app
//TODO 03.09.18 add MVP