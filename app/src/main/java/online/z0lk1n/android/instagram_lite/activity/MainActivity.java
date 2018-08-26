package online.z0lk1n.android.instagram_lite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.fragment.PhotoFragment;
import online.z0lk1n.android.instagram_lite.fragment.PhotoTilesFragment;
import online.z0lk1n.android.instagram_lite.util.Preferences;

public class MainActivity extends AppCompatActivity implements Navigator,
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = new Preferences(MainActivity.this);
        setTheme(preferences.getTheme());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new PhotoTilesFragment(), PhotoTilesFragment.NAME)
                    .commit();
        }
    }

    @Override
    public void openSettingsActivity() {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    @Override
    public void openPhotoTilesFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        PhotoTilesFragment photoTilesFragment = (PhotoTilesFragment) fragmentManager
                .findFragmentByTag(PhotoTilesFragment.NAME);

        if (photoTilesFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .show(photoTilesFragment)
                    .commit();
        }

        fragmentManager.popBackStack();
    }

    @Override
    public void openPhotoFragment(String path) {
        preferences.setPhoto(path);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new PhotoFragment(), PhotoFragment.NAME)
                .addToBackStack(null)
                .commit();
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
        if(item.getItemId() == R.id.action_settings)    {
            openSettingsActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                openPhotoTilesFragment();
                break;
            case R.id.settings:
                openSettingsActivity();
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}