package com.example.ourx;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/* templated from https://codelabs.developers.google.com/codelabs/android-room-with-a-view/
 * Step 4: Create the entity */
@Entity
public class MedicineEntity {

    @PrimaryKey(autoGenerate = true)
    private int mid;

    @ColumnInfo(name = "med_name")
    private String MED_NAME;

    @ColumnInfo(name = "med_dosage")
    private String MED_DOSAGE;

    @ColumnInfo(name = "med_unit")
    private String MED_UNIT;

    @ColumnInfo(name = "med_food")
    private String MED_FOOD;

    @ColumnInfo(name = "med_water")
    private String MED_WATER;

    @ColumnInfo(name = "med_time_one")
    private String MED_TIME_ONE;

    @ColumnInfo(name = "med_time_two")
    private String MED_TIME_TWO;

    @ColumnInfo(name = "med_time_three")
    private String MED_TIME_THREE;

    @ColumnInfo(name = "med_time_four")
    private String MED_TIME_FOUR;

    @ColumnInfo(name = "med_time_five")
    private String MED_TIME_FIVE;

    @ColumnInfo(name = "med_sun")
    private String MED_SUN;

    @ColumnInfo(name = "med_mon")
    private String MED_MON;

    @ColumnInfo(name = "med_tues")
    private String MED_TUES;

    @ColumnInfo(name = "med_wed")
    private String MED_WED;

    @ColumnInfo(name = "med_thurs")
    private String MED_THURS;

    @ColumnInfo(name = "med_fri")
    private String MED_FRI;

    @ColumnInfo(name = "med_sat")
    private String MED_SAT;

    @ColumnInfo(name = "med_instruct")
    private String MED_INSTRUCT;

    @ColumnInfo(name = "med_taken")
    public String MED_TAKEN;

    @ColumnInfo(name = "time_one_taken")
    public String TIME_ONE_TAKEN;

    @ColumnInfo(name = "time_two_taken")
    public String TIME_TWO_TAKEN;

    @ColumnInfo(name = "time_three_taken")
    public String TIME_THREE_TAKEN;

    @ColumnInfo(name = "time_four_taken")
    public String TIME_FOUR_TAKEN;

    @ColumnInfo(name = "time_five_taken")
    public String TIME_FIVE_TAKEN;

    @ColumnInfo(name = "time_one_skipped")
    public String TIME_ONE_SKIPPED;

    @ColumnInfo(name = "time_two_skipped")
    public String TIME_TWO_SKIPPED;

    @ColumnInfo(name = "time_three_skipped")
    public String TIME_THREE_SKIPPED;

    @ColumnInfo(name = "time_four_skipped")
    public String TIME_FOUR_SKIPPED;

    @ColumnInfo(name = "time_five_skipped")
    public String TIME_FIVE_SKIPPED;

    /* NOTE: passed parameter names MUST be the same as variable names */
    public MedicineEntity(int mid, String MED_NAME, String MED_DOSAGE, String MED_UNIT, String MED_FOOD,
                          String MED_WATER, String MED_TIME_ONE, String MED_TIME_TWO, String MED_TIME_THREE,
                          String MED_TIME_FOUR, String MED_TIME_FIVE, String MED_SUN, String MED_MON,
                          String MED_TUES, String MED_WED, String MED_THURS, String MED_FRI,
                          String MED_SAT, String MED_INSTRUCT, String MED_TAKEN, String TIME_ONE_TAKEN,
                          String TIME_TWO_TAKEN, String TIME_THREE_TAKEN, String TIME_FOUR_TAKEN,
                          String TIME_FIVE_TAKEN, String TIME_ONE_SKIPPED, String TIME_TWO_SKIPPED,
                          String TIME_THREE_SKIPPED, String TIME_FOUR_SKIPPED, String TIME_FIVE_SKIPPED) {
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
        this.TIME_ONE_TAKEN = TIME_ONE_TAKEN;
        this.TIME_TWO_TAKEN = TIME_TWO_TAKEN;
        this.TIME_THREE_TAKEN = TIME_THREE_TAKEN;
        this.TIME_FOUR_TAKEN = TIME_FOUR_TAKEN;
        this.TIME_FIVE_TAKEN = TIME_FIVE_TAKEN;
        this.TIME_ONE_SKIPPED = TIME_ONE_SKIPPED;
        this.TIME_TWO_SKIPPED = TIME_TWO_SKIPPED;
        this.TIME_THREE_SKIPPED = TIME_THREE_SKIPPED;
        this.TIME_FOUR_SKIPPED = TIME_FOUR_SKIPPED;
        this.TIME_FIVE_SKIPPED = TIME_FIVE_SKIPPED;
    }

    public int getMid() { return this.mid; }

    public String getMED_NAME() {
        return this.MED_NAME;
    }

    public String getMED_DOSAGE() {
        return this.MED_DOSAGE;
    }

    public String getMED_UNIT() {
        return this.MED_UNIT;
    }

    public String getMED_FOOD() {
        return this.MED_FOOD;
    }

    public String getMED_WATER() {
        return this.MED_WATER;
    }

    public String getMED_TIME_ONE() { return this.MED_TIME_ONE; }

    public String getMED_TIME_TWO() {
        return this.MED_TIME_TWO;
    }

    public String getMED_TIME_THREE() {
        return this.MED_TIME_THREE;
    }

    public String getMED_TIME_FOUR() { return this.MED_TIME_FOUR; }

    public String getMED_TIME_FIVE() {
        return this.MED_TIME_FIVE;
    }

    public String getMED_SUN() {
        return this.MED_SUN;
    }

    public String getMED_MON() {
        return this.MED_MON;
    }

    public String getMED_TUES() {
        return this.MED_TUES;
    }

    public String getMED_WED() {
        return this.MED_WED;
    }

    public String getMED_THURS() {
        return this.MED_THURS;
    }

    public String getMED_FRI() {
        return this.MED_FRI;
    }

    public String getMED_SAT() {
        return this.MED_SAT;
    }

    public String getMED_INSTRUCT() {
        return this.MED_INSTRUCT;
    }

    public String getMED_TAKEN() {
        return this.MED_TAKEN;
    }

    /*TODO delete this?*/
    public void setMED_TAKEN(String t) {
        MED_TAKEN = t;
    }

    public void setMED_ONE(String taken, String skipped) { TIME_ONE_TAKEN = taken; TIME_ONE_SKIPPED = skipped;}

    public void setMED_TWO(String taken, String skipped) { TIME_TWO_TAKEN = taken; TIME_TWO_SKIPPED = skipped;}

    public void setMED_THREE(String taken, String skipped) { TIME_THREE_TAKEN = taken; TIME_THREE_SKIPPED = skipped;}

    public void setMED_FOUR(String taken, String skipped) { TIME_FOUR_TAKEN = taken; TIME_FOUR_SKIPPED = skipped;}

    public void setMED_FIVE(String taken, String skipped) { TIME_FIVE_TAKEN = taken; TIME_FIVE_SKIPPED = skipped;}




}
