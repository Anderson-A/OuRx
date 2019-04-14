package com.example.ourx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CabinetCardAdapter extends ArrayAdapter<CabinetCard> {

    private String infoString = "Dosage: ";

    public CabinetCardAdapter(Context context, ArrayList<CabinetCard> cards) {
        super(context, 0, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        CabinetCard cabinetCard = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cabinet_card, parent, false);
        }
        // Lookup view for medicine name, dosage, and instructions
        TextView cabinetName = (TextView) convertView.findViewById(R.id.cabinet_name);
        TextView cabinet_info = (TextView) convertView.findViewById(R.id.cabinet_info);
        cabinetName.setText(cabinetCard.getCabinetName());

        String cardDosage = cabinetCard.getDosage();
        String cardInstructions = cabinetCard.getInstructions();

        String finalString;
        if (cardInstructions.equals("")) {
            finalString = infoString + cardDosage + " pill";
        } else {
            finalString = infoString + cardDosage + " pill\n" + cardInstructions;
        }
        cabinet_info.setText(finalString);

        return convertView;
    }
}
