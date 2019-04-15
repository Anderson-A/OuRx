package com.example.ourx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AddMedication extends AppCompatActivity {
CheckBox take_with_food, take_with_water, sun, mon, tues, wed, thurs, fri, sat;
EditText dosage, special_instructions;
Spinner unit, frequency;
Button add_time, add_medication;
ListView times;
ArrayList<String> allTimes;
ArrayAdapter<String> adapter;
TextView medication_name;

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
        // times.set
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
        /* For demo purposes only, eventually this will just be added to database */
        Intent intent = new Intent();
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

        if (!requiredFieldsFilledIn) {
            return false;
        }
        intent.putExtra("medication_name", medication_name.getText());
        intent.putExtra("all_times", allTimes.toArray(new String[allTimes.size()]));
        intent.putExtra("dosage", dosage.getText().toString());

        intent.putExtra("units", unit.getSelectedItem().toString());

        intent.putExtra("take_with_food", take_with_food.isChecked());
        intent.putExtra("take_with_water", take_with_water.isChecked());

        intent.putExtra("sun", sun.isChecked());
        intent.putExtra("mon", mon.isChecked());
        intent.putExtra("tues", tues.isChecked());
        intent.putExtra("wed", wed.isChecked());
        intent.putExtra("thurs", thurs.isChecked());
        intent.putExtra("fri", fri.isChecked());
        intent.putExtra("sat", sat.isChecked());

        if (special_instructions.getText().toString().equals("")) {
            intent.putExtra("special_instructions", "");
        } else {
            intent.putExtra("special_instructions", special_instructions.getText().toString());
        }
        setResult(Activity.RESULT_OK, intent);
        finish();
        return true;
    }

}
