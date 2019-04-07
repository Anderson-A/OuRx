package com.example.ourx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/* Source: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView */
public class MedCardAdapter extends ArrayAdapter<MedicineCard> {

    private String timeString;

    public MedCardAdapter(Context context, ArrayList<MedicineCard> users, boolean isPast) {
        super(context, 0, users);
        if (isPast) {
            timeString = "Taken yesterday at ";
        } else {
            timeString = "Take today at ";
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        MedicineCard medicineCard = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.medicine_card, parent, false);
        }

        // Lookup view for medicine name & time to take
        TextView medName = (TextView) convertView.findViewById(R.id.med_name);
        TextView medTime = (TextView) convertView.findViewById(R.id.med_time);
        medName.setText(medicineCard.getName());

        String finalString = timeString + medicineCard.getTimeToTake();
        medTime.setText(finalString);

        /* TODO if time to take after current time outline red */

        // Return the completed view to render on screen
        return convertView;
    }
}