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
import java.util.Date;

/* Template from: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView */
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

        needToCheckHour = !isPast;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        // Get the data item for this position
        MedicineCard medicineCard = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_schedule, parent, false);
        }

        // Lookup view for medicine name & time to take
        TextView medName = (TextView) convertView.findViewById(R.id.med_name);
        TextView medTime = (TextView) convertView.findViewById(R.id.med_time);
        medName.setText(medicineCard.getName());

        String finalString = timeString + medicineCard.getTimeToTake();
        medTime.setText(finalString);

        Date rightNow = Calendar.getInstance().getTime();
        Date medicationTime = parseTime(medicineCard.getTimeToTake());

        /* In upcoming mode, aka need to check the hour */
        /* TODO: For some reason this doesn't show up red when the hour is the same (i.e. its 10:30 and you were supposed to take meds at 10*/
        if (needToCheckHour && !medicineCard.isTaken()) {
            if (medicationTime.before(rightNow)) {
                medName.setTextColor(Color.parseColor("#f44253"));
            }
        }

        // Return the completed view to render on screen
        return convertView;
    }

    private Date parseTime(String time) {
        Calendar test;
        String[] hourAndTime = time.split("\\s+");
        int amOrPm;
        if (hourAndTime[1].equals("am")) {
            amOrPm = 0;
        } else {
            amOrPm = 1;
        }
        test = Calendar.getInstance();
        test.set(Calendar.HOUR, Integer.parseInt(hourAndTime[0]));
        test.set(Calendar.AM_PM, amOrPm);

        return test.getTime();
    }
}