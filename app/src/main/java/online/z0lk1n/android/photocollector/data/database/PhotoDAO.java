package online.z0lk1n.android.photocollector.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PhotoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<PhotoEntity> photoEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PhotoEntity photoEntity);

    @Update
    void update(PhotoEntity photoEntity);

    @Delete
    void delete(PhotoEntity photoEntity);

    @Query("DELETE FROM photoentity")
    void deleteAll();

    @Query("SELECT * FROM photoentity")
    Single<List<PhotoEntity>> getAllPhoto();

    @Query("SELECT * FROM photoentity WHERE photo_path LIKE :photoPath")
    Single<PhotoEntity> getByPhotoPath(String photoPath);
}
