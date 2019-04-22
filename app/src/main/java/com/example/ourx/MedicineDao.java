package com.example.ourx;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

/* templated from https://codelabs.developers.google.com/codelabs/android-room-with-a-view/ */
@Dao
public interface MedicineDao {
    @Query("SELECT * FROM medicineentity")
    LiveData<List<MedicineEntity>> getAll();

    @Insert
    void insert(MedicineEntity medicineEntity);

    @Delete
    void delete(MedicineEntity medicineEntity);

    @Query("DELETE FROM medicineentity")
    void deleteAll();

}
