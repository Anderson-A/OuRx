package com.example.ourx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CabinetCardAdapter extends ArrayAdapter<MedicineEntity> {

    public CabinetCardAdapter(Context context, List<MedicineEntity> cards) {
        super(context, 0, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        MedicineEntity card = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cabinet_card, parent, false);
        }
        // Lookup view for medicine name, dosage, and instructions
        TextView cabinetName = (TextView) convertView.findViewById(R.id.cabinet_name);
        TextView cabinetInfo = (TextView) convertView.findViewById(R.id.cabinet_info);
        cabinetName.setText(card.getMED_NAME());

        String cardDosage = card.getMED_DOSAGE();
        String cardInstructions = card.getMED_INSTRUCT();
        String cardUnit = card.getMED_UNIT();

        String infoString = "Dosage: ";

        // Check to see if dosage needs to be plural
        if (cardUnit.equals("mL") || cardUnit.equals("mg")) {
            infoString = infoString + cardDosage + " " + cardUnit;
        } else {
            if (Integer.parseInt(cardDosage) > 1) {
                infoString = infoString + cardDosage + " " + cardUnit + "s";
            } else {
                infoString = infoString + cardDosage + " " + cardUnit;
            }
        }

        // Get all days of the week the user wants to be reminded on
        String daysOfWeek = weekdays(card);
        if (!daysOfWeek.isEmpty()) {
            infoString += "\n" + weekdays(card);
        }

        // Get times user wants to be reminded on
        infoString += "\n" + timesString(card);

        // Get if they are taking with food, water, or none
        if (card.getMED_FOOD() != null) {
            if (card.getMED_WATER() != null) {
                infoString = infoString + "\nTake with food and water";
            } else {
                infoString = infoString + "\nTake with food";
            }
        } else if (card.getMED_WATER() != null) {
            infoString = infoString + "\nTake with water";
        }

        // append special instructions
        if (cardInstructions != null) {
            infoString = infoString + "\n" + cardInstructions;
        }

        cabinetInfo.setText(infoString);

        return convertView;
    }

    public String weekdays(MedicineEntity me) {
        ArrayList<String> days = new ArrayList<>();
        if (me.getMED_SUN() != null) { days.add("Sun"); }
        if (me.getMED_MON() != null) { days.add("Mon"); }
        if (me.getMED_TUES() != null) { days.add("Tue"); }
        if (me.getMED_WED() != null) { days.add("Wed"); }
        if (me.getMED_THURS() != null) { days.add("Thu"); }
        if (me.getMED_FRI() != null) { days.add("Fri"); }
        if (me.getMED_SAT() != null) { days.add("Sat"); }

        String daysOfWeek = "";
        for (String day : days) {
            daysOfWeek += day + ", ";
        }

        if (!daysOfWeek.isEmpty()) {
            daysOfWeek =  daysOfWeek.substring(0, daysOfWeek.length() - 2);
        }

        return daysOfWeek;
    }

    public String timesString(MedicineEntity me) {
        String allTimes = me.getMED_TIME_ONE() + ", ";

        if (me.getMED_TIME_TWO() != null) { allTimes += me.getMED_TIME_TWO() + ", "; }
        if (me.getMED_TIME_THREE() != null) { allTimes += me.getMED_TIME_THREE() + ", "; }
        if (me.getMED_TIME_FOUR() != null) { allTimes += me.getMED_TIME_FOUR() + ", "; }
        if (me.getMED_TIME_FIVE() != null) { allTimes += me.getMED_TIME_FIVE() + ", "; }

        return allTimes.substring(0, allTimes.length() - 2);
    }
}
