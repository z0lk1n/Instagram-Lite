package online.z0lk1n.android.instagram_lite.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PhotoDAO {
    @Insert
    void insertAll(PhotoEntity... photoEntities);

    @Delete
    void delete(PhotoEntity photoEntity);

    @Query("SELECT * FROM PhotoEntity")
    Single<List<PhotoEntity>> getAllPhoto();
}
