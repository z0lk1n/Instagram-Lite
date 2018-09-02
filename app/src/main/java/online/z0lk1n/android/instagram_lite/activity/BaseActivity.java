package online.z0lk1n.android.instagram_lite.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.util.Preferences;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    public void setTheme(int resid) {
        super.setTheme(getCurrentTheme());
    }

    protected int getCurrentTheme() {
        switch (new Preferences(BaseActivity.this).getTheme()) {
            case LIGHT:
                return R.style.ThemeStandard_Light;
            case DARK:
                return R.style.ThemeStandard_Dark;
            case STANDARD:
            default:
                return R.style.ThemeStandard;
        }
    }
}
