package online.z0lk1n.android.instagram_lite.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.fragment.PhotoFragment;
import online.z0lk1n.android.instagram_lite.util.Preferences;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(new Preferences(this).getTheme());

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new PhotoFragment(), PhotoFragment.NAME)
                    .commit();
        }
    }
}