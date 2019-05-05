package com.example.ourx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dpro.widgets.WeekdaysPicker;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMedication extends AppCompatActivity {
    CheckBox take_with_food, take_with_water;
    EditText dosage, special_instructions;
    Spinner unit, frequency;
    Button add_time, add_medication;
    ListView times;
    ArrayList<String> allTimes;
    ArrayAdapter<String> adapter;
    TextView medication_name;
    WeekdaysPicker weekdaysWidget;

    static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medication);


        //Initialize Medication Name
        medication_name = findViewById(R.id.medication_name);

        //Initialize Checkboxes
        take_with_food = findViewById(R.id.take_with_food);
        take_with_water = findViewById(R.id.take_with_water);

        // Initialize Weekday Picker
        weekdaysWidget = (WeekdaysPicker) findViewById(R.id.weekdays);
        weekdaysWidget.setSelectedDays(new ArrayList<Integer>()); // Nothing selected by default

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

        Intent intent = new Intent(AddMedication.this, SearchPopup.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            medication_name.setText(data.getStringExtra("medication_name"));
        }
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

        else if (weekdaysWidget.noDaySelected()) {
            Snackbar.make(add_medication, "Please select at least one day.", Snackbar.LENGTH_SHORT).show();
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

        List<String> selectedDays = weekdaysWidget.getSelectedDaysText();
        if (selectedDays.contains("Sunday")) { sunday = "yes"; }
        if (selectedDays.contains("Monday")) { monday = "yes"; }
        if (selectedDays.contains("Tuesday")) { tuesday = "yes"; }
        if (selectedDays.contains("Wednesday")) { wednesday = "yes"; }
        if (selectedDays.contains("Thursday")) { thursday = "yes"; }
        if (selectedDays.contains("Friday")) { friday = "yes"; }
        if (selectedDays.contains("Saturday")) { saturday = "yes"; }

        if (special_instructions.getText().toString().equals("")) {
            instr = null;
        } else {
            instr = special_instructions.getText().toString();
        }

        /* Creates a new medication entry based on entered data */
        MedicineEntity medicineEntity = new MedicineEntity(0, medication_name.getText().toString(),
                dosage.getText().toString(), unit.getSelectedItem().toString(), takeWithFoodEntry,
                takeWithWaterEntry, medTimeOne, medTimeTwo, medTimeThree, medTimeFour,
                medTimeFive, sunday, monday, tuesday, wednesday, thursday, friday,
                saturday, instr, "false", "false", "false", "false",
                "false", "false", "false", "false",
                "false", "false", "false");

        /* Inserts the entity into the database */
        medicineViewModel.insert(medicineEntity);

        finish();
        return true;
    }

}
