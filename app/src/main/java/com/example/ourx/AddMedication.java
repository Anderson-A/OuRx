package com.example.ourx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddMedication extends AppCompatActivity {
CheckBox take_with_food, take_with_water, sun, mon, tues, wed, thurs, fri, sat;
EditText dosage, special_instructions;
Spinner unit, frequency;
Button add_time, add_medication;
RecyclerView times;
ArrayList<String> allTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medication);

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
    }

    public void addTimes(View v) {
        allTimes.add(frequency.getSelectedItem().toString());
        Log.d("ALLTIMES SIZE: ", Integer.toString(allTimes.size()));
        // times.set
    }

    public boolean onAddMedication(View v) {
        Intent intent = new Intent();
        boolean requiredFieldsFilledIn = true;
        if (dosage.getText().toString().equals("")) {
            dosage.requestFocus();
            // dosage.setBackground(Color.parseColor("#ff0000"));
            requiredFieldsFilledIn = false;
        }

        if (allTimes.size() == 0) {
            frequency.requestFocus();
            requiredFieldsFilledIn = false;
        }

        if (!requiredFieldsFilledIn) {
            return false;
        }
        intent.putExtra("all_times", allTimes);

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
