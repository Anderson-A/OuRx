package com.example.ourx;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/* templated from https://codelabs.developers.google.com/codelabs/android-room-with-a-view/ */
@Entity
public class MedicineEntity {

    @PrimaryKey
    public int mid;

    @ColumnInfo(name = "med_name")
    public String MED_NAME;

    @ColumnInfo(name = "med_dosage")
    public String MED_DOSAGE;

    @ColumnInfo(name = "med_unit")
    public String MED_UNIT;

    @ColumnInfo(name = "med_food")
    public String MED_FOOD;

    @ColumnInfo(name = "med_water")
    public String MED_WATER;

    @ColumnInfo(name = "med_time_one")
    public String MED_TIME_ONE;

    @ColumnInfo(name = "med_time_two")
    public String MED_TIME_TWO;

    @ColumnInfo(name = "med_time_three")
    public String MED_TIME_THREE;

    @ColumnInfo(name = "med_time_four")
    public String MED_TIME_FOUR;

    @ColumnInfo(name = "med_time_five")
    public String MED_TIME_FIVE;

    @ColumnInfo(name = "med_sun")
    public String MED_SUN;

    @ColumnInfo(name = "med_mon")
    public String MED_MON;

    @ColumnInfo(name = "med_tues")
    public String MED_TUES;

    @ColumnInfo(name = "med_wed")
    public String MED_WED;

    @ColumnInfo(name = "med_thurs")
    public String MED_THURS;

    @ColumnInfo(name = "med_fri")
    public String MED_FRI;

    @ColumnInfo(name = "med_sat")
    public String MED_SAT;

    @ColumnInfo(name = "med_instruct")
    public String MED_INSTRUCT;

    @ColumnInfo(name = "med_taken")
    public String MED_TAKEN;

    /* NOTE: passed parameter names MUST be the same as variable names */
    public MedicineEntity(int mid, String MED_NAME, String MED_DOSAGE, String MED_UNIT, String MED_FOOD,
                          String MED_WATER, String MED_TIME_ONE, String MED_TIME_TWO, String MED_TIME_THREE,
                          String MED_TIME_FOUR, String MED_TIME_FIVE, String MED_SUN, String MED_MON,
                          String MED_TUES, String MED_WED, String MED_THURS, String MED_FRI,
                          String MED_SAT, String MED_INSTRUCT, String MED_TAKEN) {
        this.mid = mid;
        this.MED_NAME = MED_NAME;
        this.MED_DOSAGE = MED_DOSAGE;
        this.MED_UNIT = MED_UNIT;
        this.MED_FOOD = MED_FOOD;
        this.MED_WATER = MED_WATER;
        this.MED_TIME_ONE = MED_TIME_ONE;
        this.MED_TIME_TWO = MED_TIME_TWO;
        this.MED_TIME_THREE = MED_TIME_THREE;
        this.MED_TIME_FOUR = MED_TIME_FOUR;
        this.MED_TIME_FIVE = MED_TIME_FIVE;
        this.MED_SUN = MED_SUN;
        this.MED_MON = MED_MON;
        this.MED_TUES = MED_TUES;
        this.MED_WED = MED_WED;
        this.MED_THURS = MED_THURS;
        this.MED_FRI = MED_FRI;
        this.MED_SAT = MED_SAT;
        this.MED_INSTRUCT = MED_INSTRUCT;
        this.MED_TAKEN = MED_TAKEN;
    }
}
