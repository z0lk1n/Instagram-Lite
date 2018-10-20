package online.z0lk1n.android.photocollector.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Preferences {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Preferences(@NotNull Context context) {
        sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public Theme getTheme() {
        return parseTheme(sharedPref.getString(Const.KEY_PREF_THEME, Const.KEY_PREF_STANDARD_THEME));
    }

    public void setTheme(Theme theme) {
        editor.putString(Const.KEY_PREF_THEME, mapThemeToString(theme)).apply();
    }

    @Contract(pure = true)
    private Theme parseTheme(@NotNull String string) {
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

    @Contract(pure = true)
    private String mapThemeToString(@NotNull Theme theme) {
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
