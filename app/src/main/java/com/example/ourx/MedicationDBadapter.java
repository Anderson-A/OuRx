package com.example.ourx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MedicationDBadapter {

    private SQLiteDatabase db;
    private MedicationDBhelper dbHelper;
    private final Context context;

    private static final String DB_NAME = "medications.db";
    private static int dbVersion = 1;

    private static final String MEDS_TABLE = "meds";
    public static final String MED_ID = "med_id"; // column 0
    public static final String MED_NAME = "med_name"; // column 1
    public static final String MED_DOSAGE = "med_dosage"; // column 2
    public static final String MED_UNIT = "med_unit"; // column 3
    public static final String MED_FOOD = "med_food"; // column 4
    public static final String MED_WATER = "med_water"; // column 5
    public static final String MED_TIMES = "med_times"; // column 6
    public static final String MED_SUN = "med_sun"; // column 7
    public static final String MED_MON = "med_mon"; // column 8
    public static final String MED_TUE = "med_tue"; // column 9
    public static final String MED_WED = "med_wed"; // column 10
    public static final String MED_THU = "med-thu"; // column 11
    public static final String MED_FRI = "med_fri"; // column 12
    public static final String MED_SAT = "med_sat"; // column 13
    public static final String MED_INSTRUCT = "med_instruct"; // column 14
    public static final String[] MED_COLS = {MED_ID, MED_NAME, MED_DOSAGE, MED_UNIT, MED_FOOD,
                                             MED_WATER, MED_TIMES, MED_SUN, MED_MON, MED_TUE,
                                             MED_WED, MED_THU, MED_FRI, MED_SAT, MED_INSTRUCT};

    public MedicationDBadapter(Context ctx) {
        context = ctx;
        dbHelper = new MedicationDBhelper(context, DB_NAME, null, dbVersion);
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() { db.close(); }

    public void clear() {
        dbHelper.onUpgrade(db, dbVersion, dbVersion++);
        dbVersion++;
    }

    // database update methods

    public long insertItem(Medication med) {
        // create a enw row of values to insert
        ContentValues cValues = new ContentValues();
        //assign values for each col
        cValues.put(MED_NAME, med.getName());
        cValues.put(MED_DOSAGE, med.getDosage());
        cValues.put(MED_UNIT, med.getUnit());
        cValues.put(MED_FOOD, med.getWithFood());
        cValues.put(MED_WATER, med.getWithWater());
        cValues.put(MED_TIMES, (med.getTimes())[0]);
        cValues.put(MED_SUN, med.getSunday());
        cValues.put(MED_MON, med.getMonday());
        cValues.put(MED_TUE, med.getTuesday());
        cValues.put(MED_WED, med.getWednesday());
        cValues.put(MED_THU, med.getThursday());
        cValues.put(MED_FRI, med.getFriday());
        cValues.put(MED_SAT, med.getSaturday());
        cValues.put(MED_INSTRUCT,med.getInstructions());
        // add to course table in database
        return db.insert(MEDS_TABLE, null, cValues);
    }

    public boolean removeItem(long mId) { return db.delete(MEDS_TABLE, "MED_ID="+mId, null) > 0; }

    public boolean updateField(long mId,int field,String wh) {
        ContentValues cValue = new ContentValues();
        cValue.put(MED_COLS[field], wh);
        return db.update(MEDS_TABLE, cValue, MED_ID+"="+mId, null) > 0;
    }

    // database query methods
    public Cursor getAllItems() {
        return db.query(MEDS_TABLE, MED_COLS, null, null, null, null, null);
    }

    public Cursor getItemCursor(long mId) throws SQLException {
        Cursor result = db.query(true, MEDS_TABLE, MED_COLS, MED_ID+"="+mId, null, null, null, null, null);
        if ((result.getCount() == 0) || !result.moveToFirst()) {
            throw new SQLException("No job items found for row: " + mId);
        }
        return result;
    }

    public Medication getMedItem(long mId) throws SQLException {
        Cursor cursor = db.query(true, MEDS_TABLE, MED_COLS, MED_ID+"="+mId, null, null, null, null, null);
        if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
            throw new SQLException("No course items found for row: " + mId);
        }
        // must use column indices to get column values
        String[] times = {cursor.getString(6)};
        return new Medication(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), times, cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10), cursor.getInt(11), cursor.getInt(12), cursor.getInt(13), cursor.getString(14));
    }

    private static class MedicationDBhelper extends SQLiteOpenHelper {

        // SQL statement to create a new database
        private static final String DB_CREATE = "CREATE TABLE " + MEDS_TABLE
                + " (" + MED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MED_NAME + " TEXT, "
                + MED_DOSAGE + " TEXT, " + MED_UNIT + " TEXT, " + MED_FOOD + " INTEGER, "
                + MED_WATER + " INTEGER, " + MED_TIMES + " TEXT, " + MED_SUN + " INTEGER, "
                + MED_MON + " INTEGER, " + MED_TUE + " INTEGER, " + MED_WED + " INTEGER, "
                + MED_THU + " INTEGER, " + MED_FRI + " INTEGER, " + MED_SAT + " INTEGER, "
                + MED_INSTRUCT + " TEXT);";

        public MedicationDBhelper(Context context, String name, SQLiteDatabase.CursorFactory fct, int version) {
            super(context, name, fct, version);
        }

        @Override
        public void onCreate(SQLiteDatabase adb) {
            adb.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase adb, int oldVersion, int newVersion) {
            Log.w("MedicationDB", "upgrading from version " + oldVersion + " to "
                    + newVersion + ", destroying old data");
            // drop old table if it exists, create new one
            // better to migrate existing data into new table
            adb.execSQL("DROP TABLE IF EXISTS " + MEDS_TABLE);
            onCreate(adb);
        }
    } // MedicationDBhelper class
}// MedicationDBadapter class
