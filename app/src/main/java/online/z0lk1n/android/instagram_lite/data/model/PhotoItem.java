package online.z0lk1n.android.instagram_lite.data.model;

import org.jetbrains.annotations.Contract;

public final class PhotoItem {

    private final String name;
    private boolean isFavorites;

    public PhotoItem(String name) {
        this.name = name;
        this.isFavorites = false;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }

    @Contract(pure = true)
    public boolean isFavorites() {
        return isFavorites;
    }

    public void setFavorites(boolean favorites) {
        isFavorites = favorites;
    }
}
