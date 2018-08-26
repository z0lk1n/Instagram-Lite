package online.z0lk1n.android.instagram_lite.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class Preferences {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public Preferences(Context context) {
        sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public Set<String> getPhotoSet() {
        return sharedPref.getStringSet(Const.PREF_KEY_PHOTO_SET, null);
    }

    public void setPhotoSet(Set<String> photoSet) {
        editor.putStringSet(Const.PREF_KEY_PHOTO_SET, photoSet).apply();
    }

    public int getTheme() {
        return sharedPref.getInt(Const.PREF_KEY_THEME, Const.PREF_DEFAULT_THEME);
    }

    public void setTheme(int theme) {
        editor.putInt(Const.PREF_KEY_THEME, theme).apply();
    }

    public String getPhoto() {
        return sharedPref.getString(Const.PREF_KEY_PHOTO, null);
    }

    public void setPhoto(String path) {
        editor.putString(Const.PREF_KEY_PHOTO, path).apply();
    }
}
