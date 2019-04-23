package com.example.ourx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CabinetCardAdapter extends ArrayAdapter<CabinetCard> {

    public CabinetCardAdapter(Context context, ArrayList<CabinetCard> cards) {
        super(context, 0, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        CabinetCard card = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cabinet_card, parent, false);
        }
        // Lookup view for medicine name, dosage, and instructions
        TextView cabinetName = (TextView) convertView.findViewById(R.id.cabinet_name);
        TextView cabinetInfo = (TextView) convertView.findViewById(R.id.cabinet_info);
        cabinetName.setText(card.getCabinetName());

        String cardDosage = card.getDosage();
        String cardInstructions = card.getInstructions();
        String cardUnit = card.getUnit();

        String infoString = "Dosage: ";
        /*if (cardInstructions.equals("")) {
            infoString = infoString + cardDosage + " pill";
        } else {
            infoString = infoString + cardDosage + " pill\n" + cardInstructions;
        }*/

        if (cardUnit.equals("mL") || cardUnit.equals("mg")) {
            if (cardInstructions == null) {
                infoString = infoString + cardDosage + " " + cardUnit;
            } else {
                infoString = infoString + cardDosage + " " + cardUnit + "\n" + cardInstructions;
            }
        } else {
            if (Integer.parseInt(cardDosage) > 1) {
                if (cardInstructions == null) {
                    infoString = infoString + cardDosage + " " + cardUnit + "s";
                } else {
                    infoString = infoString + cardDosage + " " + cardUnit + "s\n" + cardInstructions;
                }
            } else {
                if (cardInstructions == null) {
                    infoString = infoString + cardDosage + " " + cardUnit;
                } else {
                    infoString = infoString + cardDosage + " " + cardUnit + "\n" + cardInstructions;
                }
            }
        }

        cabinetInfo.setText(infoString);

        return convertView;
    }
}
