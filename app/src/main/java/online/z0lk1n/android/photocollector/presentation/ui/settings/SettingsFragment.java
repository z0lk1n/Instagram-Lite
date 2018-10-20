package online.z0lk1n.android.photocollector.presentation.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import online.z0lk1n.android.photocollector.R;
import online.z0lk1n.android.photocollector.presentation.ui.main.MainActivity;
import online.z0lk1n.android.photocollector.util.Const;
import online.z0lk1n.android.photocollector.util.Preferences;
import online.z0lk1n.android.photocollector.util.Theme;

public final class SettingsFragment extends PreferenceFragmentCompat {

    private Preferences preferences;
    private Preference prefDefaultTheme;
    private Preference prefLightTheme;
    private Preference prefDarkTheme;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        preferences = new Preferences(context);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.preferences, s);
        init();
        initListener();
    }

    private void init() {
        prefDefaultTheme = findPreference(Const.KEY_PREF_STANDARD_THEME);
        prefLightTheme = findPreference(Const.KEY_PREF_LIGHT_THEME);
        prefDarkTheme = findPreference(Const.KEY_PREF_DARK_THEME);
    }

    private void initListener() {
        prefDefaultTheme.setOnPreferenceClickListener(preference -> {
            changeTheme(Theme.STANDARD);
            return true;
        });
        prefLightTheme.setOnPreferenceClickListener(preference -> {
            changeTheme(Theme.LIGHT);
            return true;
        });
        prefDarkTheme.setOnPreferenceClickListener(preference -> {
            changeTheme(Theme.DARK);
            return true;
        });
    }

    public void changeTheme(Theme theme) {
        if (preferences.getTheme() == theme || getActivity() == null) {
            return;
        }
        preferences.setTheme(theme);
        TaskStackBuilder.create(getActivity())
                .addNextIntent(new Intent(getActivity(), MainActivity.class))
                .addNextIntent(getActivity().getIntent())
                .startActivities();
    }
}