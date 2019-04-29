package com.example.ourx;

import android.app.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.lifecycle.ViewModelProviders;

public class SearchPopup extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    String[] titles;
    AutoCompleteTextView auto_complete;
    MedicineEntity dataMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.row_layout);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        // Initialize Search Bar
        titles = getResources().getStringArray(R.array.medications);
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, titles);
        auto_complete = findViewById(R.id.search_results);
        auto_complete.setAdapter(adapter);
        auto_complete.setThreshold(1);

        auto_complete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(auto_complete.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }


        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        final MedicineViewModel medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
        String medName = auto_complete.getText().toString();
        if (medName.length() > 0) {
            dataMedicine = medicineViewModel.getMedicineByName(medName);
            if (dataMedicine == null) {
                intent.putExtra("medication_name", medName);
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.button), "You have already added " + medName + ".", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }
    }
}

