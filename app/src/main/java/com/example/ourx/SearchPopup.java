package com.example.ourx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class SearchPopup extends Activity {
    ArrayAdapter<String> adapter;
    String[] titles;
    AutoCompleteTextView auto_complete;

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
        titles = new String[]{"Test", "wow", "WQEFWE"};
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
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        if (auto_complete.getText().toString().length() > 0) {
            intent.putExtra("medication_name", auto_complete.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}

