package com.example.ourx;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/* templated from https://codelabs.developers.google.com/codelabs/android-room-with-a-view/
 * Step 5: Create the DAO */
@Dao
public interface MedicineDao {
    @Query("SELECT * FROM medicineentity")
    LiveData<List<MedicineEntity>> getAll();
    // Wrapped with LiveData so we can observe when the data changes and update things accordingly

    @Query("SELECT * FROM medicineentity WHERE med_name = :name")
    MedicineEntity getMedByName(String name);

    //@Query("UPDATE medicineentity SET med_taken=:taken WHERE mid = :id")
    //void update(int id, String taken);

    @Update
    void update(MedicineEntity... meds);

    @Insert
    void insert(MedicineEntity medicineEntity);

    @Delete
    void delete(MedicineEntity medicineEntity);

    @Query("DELETE FROM medicineentity")
    void deleteAll();

}
