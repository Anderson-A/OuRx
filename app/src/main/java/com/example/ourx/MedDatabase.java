package com.example.ourx;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* templated from https://codelabs.developers.google.com/codelabs/android-room-with-a-view/
 * Step 7: Add a Room database */
@Database(entities = {MedicineEntity.class}, version = 2)
public abstract class MedDatabase extends RoomDatabase {
    public abstract MedicineDao medicineDao();

    private static volatile MedDatabase INSTANCE;

    /* singleton */
    static MedDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MedDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MedDatabase.class, "med_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /* These functions populate the database immediately upon instantiation */
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

            @Override
            public void onOpen (@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                new PopulateDbAsync(INSTANCE).execute();
            }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MedicineDao mDao;

        PopulateDbAsync(MedDatabase db) {
            mDao = db.medicineDao();
        }

        /* TODO: Hardcoded default value. Need to incorporate persistence */
        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            MedicineEntity medicineEntity = new MedicineEntity(1,"OxyCodone", "1", "pill",
                    null, null, "11 pm", null, null, null,
                    null, "yes", null, null, null, null, null,
                    null, null, "true");
            Log.d("insert", "inserting " + medicineEntity.MED_NAME);
            mDao.insert(medicineEntity);
            return null;
        }
    }

}
