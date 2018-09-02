package online.z0lk1n.android.instagram_lite.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static final String TAG = "Preferences";
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public Preferences(Context context) {
        sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public Theme getTheme() {
        return parseTheme(sharedPref.getString(Const.KEY_PREF_THEME, Const.KEY_PREF_STANDARD_THEME));
    }

    public void setTheme(Theme theme) {
        editor.putString(Const.KEY_PREF_THEME, mapThemeToString(theme)).apply();
    }

    public String getPhoto() {
        return sharedPref.getString(Const.KEY_PREF_PHOTO, null);
    }

    public void setPhoto(String path) {
        editor.putString(Const.KEY_PREF_PHOTO, path).apply();
    }

    private Theme parseTheme(String string) {
        switch (string) {
            case Const.KEY_PREF_LIGHT_THEME:
                return Theme.LIGHT;
            case Const.KEY_PREF_DARK_THEME:
                return Theme.DARK;
            case Const.KEY_PREF_STANDARD_THEME:
            default:
                return Theme.STANDARD;
        }
    }

    private String mapThemeToString(Theme theme) {
        switch (theme) {
            case LIGHT:
                return Const.KEY_PREF_LIGHT_THEME;
            case DARK:
                return Const.KEY_PREF_DARK_THEME;
            case STANDARD:
            default:
                return Const.KEY_PREF_STANDARD_THEME;
        }
    }
}
