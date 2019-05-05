package com.example.ourx;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


/* templated from https://codelabs.developers.google.com/codelabs/android-room-with-a-view/
 * Step 9: Create the ViewModel */
public class MedicineViewModel extends AndroidViewModel {
    private LiveData<List<MedicineEntity>> allMedications;
    private MedicineDao medicineDao;
    private MedDatabase db;

    public MedicineViewModel(Application application) {
        super(application);
        db = MedDatabase.getDatabase(application);
        medicineDao = db.medicineDao();
        allMedications = medicineDao.getAll();
    }

    MedicineEntity getMedicineByName(String name) {
        return medicineDao.getMedByName(name);
    }

    /* Returns all medications in the database. Used to track any changes */
    LiveData<List<MedicineEntity>> getAllMeds() {
        return allMedications;
    }

    MedicineDao getMedicineDao() {
        return medicineDao;
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

    void update(MedicineEntity medicineEntity) {
        new updateAsyncTask(medicineDao).execute(medicineEntity);
    }

    private static class updateAsyncTask extends AsyncTask<MedicineEntity, Void, Void> {
        private MedicineDao mAsyncTaskDao;

        updateAsyncTask(MedicineDao dao) {mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final MedicineEntity...params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }

    }

    void delete(MedicineEntity medicineEntity) {
        new deleteAsyncTask(medicineDao).execute(medicineEntity);
    }

    private static class deleteAsyncTask extends AsyncTask<MedicineEntity, Void, Void> {

        private MedicineDao mAsyncTaskDao;

        deleteAsyncTask(MedicineDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final MedicineEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}
