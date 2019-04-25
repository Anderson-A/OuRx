package com.example.ourx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MedicationInfo extends AppCompatActivity {
    CheckBox take_with_food, take_with_water, sun, mon, tues, wed, thurs, fri, sat;
    EditText dosage, special_instructions;
    Spinner unit, frequency;
    Button add_time, add_medication;
    ListView times;
    ArrayList<String> allTimes;
    ArrayAdapter<String> adapter;
    TextView medication_name;

    private static int currentMedId = 2;

    static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_info);


        //Initialize Medication Name
        medication_name = findViewById(R.id.medication_name);

        //Initialize Checkboxes
        take_with_food = findViewById(R.id.take_with_food);
        take_with_water = findViewById(R.id.take_with_water);
        sun = findViewById(R.id.checkBox_sun);
        mon = findViewById(R.id.checkBox_mon);
        tues = findViewById(R.id.checkBox_tues);
        wed = findViewById(R.id.checkBox_wed);
        thurs = findViewById(R.id.checkBox_thurs);
        fri = findViewById(R.id.checkBox_fri);
        sat = findViewById(R.id.checkBox_sat);

        //Initialize Text Boxes
        dosage = findViewById(R.id.dosage);
        special_instructions = findViewById(R.id.special_instructions);

        //Initialize Spinners
        unit = findViewById(R.id.dosage_unit);
        frequency = findViewById(R.id.frequency_hour);

        //Initialize Buttons
        add_time = findViewById(R.id.add_time);
        add_medication = findViewById(R.id.add_medication);

        //Initialize List
        times = findViewById(R.id.times);

        //Initialize the array of times
        allTimes = new ArrayList<>();

        //Initialize adapter for the Time List
        adapter = new ArrayAdapter<>(this, R.layout.all_times_list, allTimes);
        times.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_medication_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Log.d("HERE", "HERE1");
                unlockLayout();
        }
        return true;
    }

    public void unlockLayout() {
        Snackbar snackbar = Snackbar.make(add_medication, "You may now edit this medication.", Snackbar.LENGTH_SHORT);
        snackbar.show();
        take_with_food.setClickable(true);
        take_with_water.setClickable(true);
        sun.setClickable(true);
        mon.setClickable(true);
        tues.setClickable(true);
        wed.setClickable(true);
        thurs.setClickable(true);
        fri.setClickable(true);
        sat.setClickable(true);

        dosage.setLongClickable(true);
        dosage.setFocusableInTouchMode(true);
        special_instructions.setLongClickable(true);
        special_instructions.setFocusableInTouchMode(true);

        findViewById(R.id.undo_time).setVisibility(View.VISIBLE);
        findViewById(R.id.add_time).setVisibility(View.VISIBLE);

        unit.setVisibility(View.VISIBLE);
        frequency.setVisibility(View.VISIBLE);
        add_medication.setVisibility(View.VISIBLE);
    }

    public void lockLayout() {
        Snackbar snackbar = Snackbar.make(add_medication, "Changes saved.", Snackbar.LENGTH_SHORT);
        snackbar.show();
        take_with_food.setClickable(false);
        take_with_water.setClickable(false);
        sun.setClickable(false);
        mon.setClickable(false);
        tues.setClickable(false);
        wed.setClickable(false);
        thurs.setClickable(false);
        fri.setClickable(false);
        sat.setClickable(false);

        dosage.setLongClickable(false);
        dosage.setFocusableInTouchMode(false);
        dosage.clearFocus();
        special_instructions.setLongClickable(false);
        special_instructions.setFocusableInTouchMode(false);
        special_instructions.clearFocus();

        findViewById(R.id.undo_time).setVisibility(View.INVISIBLE);
        findViewById(R.id.add_time).setVisibility(View.INVISIBLE);

        unit.setVisibility(View.INVISIBLE);
        frequency.setVisibility(View.INVISIBLE);
        add_medication.setVisibility(View.INVISIBLE);
    }

    public void addTimes(View v) {
        String item = frequency.getSelectedItem().toString();
        if (allTimes.contains(item)) {
            Snackbar snackbar = Snackbar.make(add_medication, "You have already added " + item + ".", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {
            allTimes.add(item);
            adapter.notifyDataSetChanged();
        }
    }

    public void removeTimes(View v) {
        String item = frequency.getSelectedItem().toString();
        if (allTimes.contains(item)) {
            allTimes.remove(item);
            adapter.notifyDataSetChanged();
        } else {
            Snackbar snackbar = Snackbar.make(add_medication, "You have not added " + item + ".", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    public boolean onAddMedication(View v) {
        final MedicineViewModel medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        boolean requiredFieldsFilledIn = true;
        if (dosage.getText().toString().equals("")) {
            dosage.requestFocus();
            Snackbar.make(add_medication, "Please enter a dosage.", Snackbar.LENGTH_SHORT).show();
            requiredFieldsFilledIn = false;
        }

        else if (allTimes.size() == 0) {
            Snackbar.make(add_medication, "Please enter at least one time.", Snackbar.LENGTH_SHORT).show();
            requiredFieldsFilledIn = false;
        }

        else if (allTimes.size() > 5) {
            Snackbar.make(add_medication, "No More than 5 times.", Snackbar.LENGTH_SHORT).show();
            requiredFieldsFilledIn = false;
        }

        if (!requiredFieldsFilledIn) {
            return false;
        }

        String takeWithFoodEntry = null;
        String takeWithWaterEntry = null;

        String medTimeOne = allTimes.get(0);
        String medTimeTwo = null;
        String medTimeThree = null;
        String medTimeFour = null;
        String medTimeFive = null;

        String sunday = null;
        String monday = null;
        String tuesday = null;
        String wednesday = null;
        String thursday = null;
        String friday = null;
        String saturday = null;

        String instr = null;

        try { medTimeTwo = allTimes.get(1); }
        catch (IndexOutOfBoundsException e) {}

        try { medTimeThree = allTimes.get(2); }
        catch (IndexOutOfBoundsException e) {}

        try { medTimeFour = allTimes.get(3); }
        catch (IndexOutOfBoundsException e) {}

        try { medTimeFive = allTimes.get(4); }
        catch (IndexOutOfBoundsException e) {}

        if (take_with_food.isChecked()) { takeWithFoodEntry = "yes"; }
        if (take_with_water.isChecked()) { takeWithWaterEntry = "yes"; }
        if (sun.isChecked()) { sunday = "yes"; }
        if (mon.isChecked()) { monday = "yes"; }
        if (tues.isChecked()) { tuesday = "yes"; }
        if (wed.isChecked()) { wednesday = "yes"; }
        if (thurs.isChecked()) { thursday = "yes"; }
        if (fri.isChecked()) { friday = "yes"; }
        if (sat.isChecked()) { saturday = "yes"; }

        if (special_instructions.getText().toString().equals("")) {
            instr = null;
        } else {
            instr = special_instructions.getText().toString();
        }

        /* Creates a new medication entry based on entered data */
        MedicineEntity medicineEntity = new MedicineEntity(currentMedId, medication_name.getText().toString(),
                dosage.getText().toString(), unit.getSelectedItem().toString(), takeWithFoodEntry,
                takeWithWaterEntry, medTimeOne, medTimeTwo, medTimeThree, medTimeFour,
                medTimeFive, sunday, monday, tuesday, wednesday, thursday, friday,
                saturday, instr, "false");

        /* Inserts the entity into the database */
        medicineViewModel.insert(medicineEntity);
        currentMedId++; // tracks the current medID. Hardcoded to start at 2 because initial population is 1. Need to fix.

        lockLayout();
        return true;
    }

}
