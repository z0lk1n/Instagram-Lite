package online.z0lk1n.android.instagram_lite.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.util.Const;
import online.z0lk1n.android.instagram_lite.util.Preferences;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String NAME = "1b7cc406-5e05-431f-9cbe-cc1401f03152";
    private static final String TAG = "SettingsFragment";

    private Preferences preferences;
    private Preference prefDefaultTheme;
    private Preference prefLightTheme;
    private Preference prefDarkTheme;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        preferences = new Preferences(context);
        context.setTheme(preferences.getTheme());
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.preferences, s);
        initialize();
        initializeListener();
    }

    private void initialize() {
        setHasOptionsMenu(true);
        prefDefaultTheme = findPreference(Const.KEY_PREF_DEFAULT_THEME);
        prefLightTheme = findPreference(Const.KEY_PREF_LIGHT_THEME);
        prefDarkTheme = findPreference(Const.KEY_PREF_DARK_THEME);
    }

    private void initializeListener() {
        prefDefaultTheme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                preferences.setTheme(R.style.AppTheme);
                getActivity().recreate();
                return true;
            }
        });

        prefLightTheme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                preferences.setTheme(R.style.LightTheme);
                getActivity().recreate();
                return true;
            }
        });

        prefDarkTheme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                preferences.setTheme(R.style.DarkTheme);
                getActivity().recreate();
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_settings, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
