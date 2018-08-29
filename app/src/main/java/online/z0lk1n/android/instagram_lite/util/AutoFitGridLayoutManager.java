package online.z0lk1n.android.instagram_lite.util;

import android.content.Context;
import android.util.DisplayMetrics;

public final class AutoFitGridLayoutManager {
    public static int calculateNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }
}