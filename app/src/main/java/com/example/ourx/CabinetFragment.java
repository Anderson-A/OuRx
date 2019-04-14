package com.example.ourx;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CabinetFragment extends ListFragment {

    ArrayList<CabinetCard> medications = new ArrayList<>();

    public CabinetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);

        //ListView listView = getListView();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        medications.clear();

        CabinetCard tylenol = new CabinetCard("Tylenol", "1", "");
        CabinetCard advil = new CabinetCard("Advil", "2", "Take two pills");
        CabinetCard vyvanse = new CabinetCard("Vyvanse", "1", "");

        medications.add(tylenol);
        medications.add(advil);
        medications.add(vyvanse);

        CabinetCardAdapter adapter = new CabinetCardAdapter(getActivity(), medications);
        setListAdapter(adapter);
    }

}
