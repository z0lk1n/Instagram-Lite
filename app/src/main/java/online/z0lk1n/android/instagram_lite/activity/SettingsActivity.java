package online.z0lk1n.android.instagram_lite.activity;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.util.Preferences;

public class SettingsActivity extends PreferenceActivity {

    public static final String NAME = "a845ed06-721b-4da3-af5a-7f867b5a80b3";
    public static final String KEY_PREF_DEFAULT_THEME = "0565f9f8-3eb5-4702-a34f-58a1a2628237";
    public static final String KEY_PREF_LIGHT_THEME = "7073d1a2-b8f5-4087-afd2-07cf6fd7983b";
    public static final String KEY_PREF_DARK_THEME = "e15f52b6-70e8-4c89-9f61-c109fbfa767d";

    private Preferences preferences;
    private Preference prefDefaultTheme;
    private Preference prefLightTheme;
    private Preference prefDarkTheme;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        initialize();
        initializeListener();
        setTheme(preferences.getTheme());
    }

    private void initialize() {
        preferences = new Preferences(this);
        prefDefaultTheme = findPreference(KEY_PREF_DEFAULT_THEME);
        prefLightTheme = findPreference(KEY_PREF_LIGHT_THEME);
        prefDarkTheme = findPreference(KEY_PREF_DARK_THEME);
    }

    private void initializeListener() {
        prefDefaultTheme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                preferences.setTheme(R.style.AppTheme);
                recreate();
                return true;
            }
        });

        prefLightTheme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                preferences.setTheme(R.style.LightTheme);
                recreate();
                return true;
            }
        });

        prefDarkTheme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                preferences.setTheme(R.style.DarkTheme);
                recreate();
                return true;
            }
        });
    }
}
