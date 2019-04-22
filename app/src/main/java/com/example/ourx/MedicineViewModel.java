package com.example.ourx;

import android.app.Application;
import android.os.AsyncTask;
import java.util.List;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/* templated from https://codelabs.developers.google.com/codelabs/android-room-with-a-view/ */
public class MedicineViewModel extends AndroidViewModel {
    private LiveData<List<MedicineEntity>> allMedications;
    private MedicineDao medicineDao;

    public MedicineViewModel(Application application) {
        super(application);
        MedDatabase db = MedDatabase.getDatabase(application);
        medicineDao = db.medicineDao();
        allMedications = medicineDao.getAll();
    }

    /* Returns all medications in the database. Used to track any changes */
    LiveData<List<MedicineEntity>> getAllMeds() {
        return allMedications;
    }

    /* Must insert using a new thread (not one the UI is using) to avoid crashing the app */
    void insert(MedicineEntity medicineEntity) {
        new insertAsyncTask(medicineDao).execute(medicineEntity);
    }

    private static class insertAsyncTask extends AsyncTask<MedicineEntity, Void, Void> {

        private MedicineDao mAsyncTaskDao;

        insertAsyncTask(MedicineDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MedicineEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
