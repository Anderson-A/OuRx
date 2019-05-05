package com.example.ourx;

import android.os.Bundle;
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

import com.dpro.widgets.WeekdaysPicker;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MedicationInfo extends AppCompatActivity {
    CheckBox take_with_food, take_with_water;
    EditText dosage, special_instructions;
    Spinner unit, frequency;
    Button add_time, change_medication;
    ListView times;
    ArrayList<String> allTimes;
    ArrayAdapter<String> adapter;
    TextView medication_name, unit_text;
    WeekdaysPicker weekdaysWidget;
    MedicineEntity dataMedicine;


    static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.medication_info);

     //   Log.d("list of meds", medicineList.get(0).getMED_NAME());
     //   Log.d("list of meds", "HELLO LOOK AT ME");

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
        unit_text = findViewById(R.id.unit_text);

        //Initialize Spinners
        unit = findViewById(R.id.dosage_unit);
        frequency = findViewById(R.id.frequency_hour);

        //Initialize Buttons
        add_time = findViewById(R.id.add_time);
        change_medication = findViewById(R.id.change_medication);

        //Initialize List
        times = findViewById(R.id.times);

        //Initialize the array of times
        allTimes = new ArrayList<>();

        //Initialize adapter for the Time List
        adapter = new ArrayAdapter<>(this, R.layout.all_times_list, allTimes);
        times.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        String queryName = extras.getString("name");

        final MedicineViewModel medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
        dataMedicine = medicineViewModel.getMedicineByName(queryName);

        //Setting the values based on database
        // Log.d("list of meds", dataMedicine.MED_FOOD);
        if (dataMedicine.getMED_NAME() != null) { medication_name.setText(dataMedicine.getMED_NAME()); }
        if (dataMedicine.getMED_DOSAGE() != null) { dosage.setText(dataMedicine.getMED_DOSAGE()); }
        if (dataMedicine.getMED_FOOD() != null) { take_with_food.setChecked(true); }
        if (dataMedicine.getMED_WATER() != null) { take_with_water.setChecked(true); }

        if (dataMedicine.getMED_UNIT() != null) { unit_text.setText(dataMedicine.getMED_UNIT()); }

        if (dataMedicine.getMED_TIME_ONE() != null) { allTimes.add(dataMedicine.getMED_TIME_ONE()); }
        if (dataMedicine.getMED_TIME_TWO() != null) { allTimes.add(dataMedicine.getMED_TIME_TWO()); }
        if (dataMedicine.getMED_TIME_THREE() != null) { allTimes.add(dataMedicine.getMED_TIME_THREE()); }
        if (dataMedicine.getMED_TIME_FOUR() != null) { allTimes.add(dataMedicine.getMED_TIME_FOUR()); }
        if (dataMedicine.getMED_TIME_FIVE() != null) { allTimes.add(dataMedicine.getMED_TIME_FIVE()); }

        List<Integer> medDays = new ArrayList<>();
        if (dataMedicine.getMED_SUN() != null) { medDays.add(Calendar.SUNDAY); }
        if (dataMedicine.getMED_MON() != null) { medDays.add(Calendar.MONDAY); }
        if (dataMedicine.getMED_TUES() != null) { medDays.add(Calendar.TUESDAY); }
        if (dataMedicine.getMED_WED() != null) { medDays.add(Calendar.WEDNESDAY); }
        if (dataMedicine.getMED_THURS() != null) { medDays.add(Calendar.THURSDAY); }
        if (dataMedicine.getMED_FRI() != null) { medDays.add(Calendar.FRIDAY); }
        if (dataMedicine.getMED_SAT() != null) { medDays.add(Calendar.SATURDAY); }
        weekdaysWidget.setSelectedDays(medDays);

        if (dataMedicine.getMED_INSTRUCT() != null) { special_instructions.setText(dataMedicine.getMED_INSTRUCT()); }

        lockLayout();
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
                unlockLayout();
                return true;

            /* https://stackoverflow.com/questions/28438030/how-to-make-back-icon-to-behave-same-as-physical-back-button-in-android */
            default:
                onBackPressed();
                return true;
        }
    }

    public void unlockLayout() {
        Snackbar snackbar = Snackbar.make(change_medication, "You may now edit this medication.", Snackbar.LENGTH_SHORT);
        snackbar.show();
        take_with_food.setClickable(true);
        take_with_water.setClickable(true);
        weekdaysWidget.setEditable(true);

        dosage.setLongClickable(true);
        dosage.setFocusableInTouchMode(true);
        special_instructions.setLongClickable(true);
        special_instructions.setFocusableInTouchMode(true);

        findViewById(R.id.undo_time).setVisibility(View.VISIBLE);
        findViewById(R.id.add_time).setVisibility(View.VISIBLE);

        unit.setVisibility(View.VISIBLE);
        frequency.setVisibility(View.VISIBLE);
        change_medication.setVisibility(View.VISIBLE);

        unit_text.setVisibility(View.INVISIBLE);
    }

    public void lockLayout() {
        take_with_food.setClickable(false);
        take_with_water.setClickable(false);
        weekdaysWidget.setEditable(false);

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
        change_medication.setVisibility(View.INVISIBLE);

        unit_text.setText(unit.getSelectedItem().toString());
        unit_text.setVisibility(View.VISIBLE);
    }

    public void addTimes(View v) {
        String item = frequency.getSelectedItem().toString();
        if (allTimes.contains(item)) {
            Snackbar snackbar = Snackbar.make(change_medication, "You have already added " + item + ".", Snackbar.LENGTH_SHORT);
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
            Snackbar snackbar = Snackbar.make(change_medication, "You have not added " + item + ".", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    public boolean onAddMedication(View v) {

        int editingID = dataMedicine.getMid();

        final MedicineViewModel medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        boolean requiredFieldsFilledIn = true;
        if (dosage.getText().toString().equals("")) {
            dosage.requestFocus();
            Snackbar.make(change_medication, "Please enter a dosage.", Snackbar.LENGTH_SHORT).show();
            requiredFieldsFilledIn = false;
        }

        else if (allTimes.size() == 0) {
            Snackbar.make(change_medication, "Please enter at least one time.", Snackbar.LENGTH_SHORT).show();
            requiredFieldsFilledIn = false;
        }

        else if (allTimes.size() > 5) {
            Snackbar.make(change_medication, "No More than 5 times.", Snackbar.LENGTH_SHORT).show();
            requiredFieldsFilledIn = false;
        }

        else if (weekdaysWidget.noDaySelected()) {
            Snackbar.make(change_medication, "Please select at least one day.", Snackbar.LENGTH_SHORT).show();
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
        MedicineEntity medicineEntity = new MedicineEntity(editingID, medication_name.getText().toString(),
                dosage.getText().toString(), unit.getSelectedItem().toString(), takeWithFoodEntry,
                takeWithWaterEntry, medTimeOne, medTimeTwo, medTimeThree, medTimeFour,
                medTimeFive, sunday, monday, tuesday, wednesday, thursday, friday,
                saturday, instr, "false", "false", "false", "false",
                "false", "false", "false", "false",
                "false", "false", "false");

        /* Inserts the entity into the database */
        medicineViewModel.update(medicineEntity);

        Snackbar snackbar = Snackbar.make(change_medication, "Changes saved.", Snackbar.LENGTH_SHORT);
        snackbar.show();
        lockLayout();
        return true;
    }

}
