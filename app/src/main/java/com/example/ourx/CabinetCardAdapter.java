package com.example.ourx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CabinetCardAdapter extends ArrayAdapter<Medication> {

    public CabinetCardAdapter(Context context, ArrayList<Medication> cards) {
        super(context, 0, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Medication medication = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cabinet_card, parent, false);
        }
        // Lookup view for medicine name, dosage, and instructions
        TextView cabinetName = (TextView) convertView.findViewById(R.id.cabinet_name);
        TextView cabinetInfo = (TextView) convertView.findViewById(R.id.cabinet_info);
        cabinetName.setText(medication.getName());

        String cardDosage = medication.getDosage();
        String cardInstructions = medication.getInstructions();
        String cardUnit = medication.getUnit();

        String infoString = "Dosage: ";
        if (cardInstructions.equals("")) {
            infoString = infoString + cardDosage + " " + cardUnit;
        } else {
            infoString = infoString + cardDosage + " " + cardUnit + "\n" + cardInstructions;
        }
        cabinetInfo.setText(infoString);

        return convertView;
    }
}
