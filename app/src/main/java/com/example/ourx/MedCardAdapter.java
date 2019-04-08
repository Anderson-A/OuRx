package com.example.ourx;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/* Source: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView */
public class MedCardAdapter extends ArrayAdapter<MedicineCard> {

    private String timeString;
    private boolean needToCheckHour;

    public MedCardAdapter(Context context, ArrayList<MedicineCard> users, boolean isPast) {
        super(context, 0, users);
        if (isPast) {
            timeString = "Taken today at ";
        } else {
            timeString = "Take today at ";
        }

        needToCheckHour = isPast;
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
        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMin = rightNow.get(Calendar.MINUTE);
        int medHour = parseHour(medicineCard.getTimeToTake());
        int medMinute = parseMinute(medicineCard.getTimeToTake());


        /* In past mode, aka need to check the hour */
        if (needToCheckHour && !medicineCard.isTaken()) {
            if (currentHour > medHour) {
                medName.setTextColor(Color.parseColor("#f44253"));

            } else if (currentHour == medHour) {
                if (currentMin > medMinute) {
                    medName.setTextColor(Color.parseColor("#f44253"));
                }
            }
        }

        // Return the completed view to render on screen
        return convertView;
    }

    private int parseHour(String time) {
        int colonIndex = time.indexOf(':');
        if (colonIndex < 0 || colonIndex > 2) {
            return -1;
        }

        else return Integer.parseInt(time.substring(0, colonIndex));
    }

    private int parseMinute(String time) {
        int colonIndex = time.indexOf(':');
        if (colonIndex < 0 || colonIndex > 2) {
            return -1;
        }
        else return Integer.parseInt(time.substring(colonIndex + 1));
    }
}