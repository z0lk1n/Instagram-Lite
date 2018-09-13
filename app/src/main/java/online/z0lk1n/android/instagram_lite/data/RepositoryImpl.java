package online.z0lk1n.android.instagram_lite.data;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

public class RepositoryImpl implements Repository {

    private static final String TAG = "RepositoryImpl";

    private static volatile RepositoryImpl instance = new RepositoryImpl();
    private List<PhotoItem> photoItemList;

    private RepositoryImpl() {
        this.photoItemList = new ArrayList<>();
    }

    @Contract(pure = true)
    public static synchronized RepositoryImpl getInstance() {
        if (instance == null) {
            instance = new RepositoryImpl();
        }
        return instance;
    }
}
