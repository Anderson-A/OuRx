package com.example.ourx;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);
        return view;
    }

    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Cabinet Fragment", "onStart");
        // Apply any required UI change now that the Fragment is visible.

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

    // Called at the start of the active lifetime.
    @Override
    public void onResume(){
        super.onResume();
        Log.d ("Cabinet Fragment", "onResume");
        // Resume any paused UI updates, threads, or processes required
        // by the Fragment but suspended when it became inactive.
    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
        Log.d ("Cabinet Fragment", "onPause");
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground activity.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
        super.onPause();
    }

    // Called to save UI state changes at the
    // end of the active lifecycle.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d ("Cabinet Fragment", "onSaveInstanceState");
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate, onCreateView, and
        // onCreateView if the parent Activity is killed and restarted.
        super.onSaveInstanceState(savedInstanceState);
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
        Log.d ("Cabinet Fragment", "onStop");
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Fragment isn't visible.
        super.onStop();
    }

    // Called when the Fragment's View has been detached.
    @Override
    public void onDestroyView() {
        Log.d ("Cabinet Fragment", "onDestroyView");
        // Clean up resources related to the View.
        super.onDestroyView();
    }

    // Called at the end of the full lifetime.
    @Override
    public void onDestroy(){
        Log.d ("Cabinet Fragment", "onDestroy");
        // Clean up any resources including ending threads,
        // closing database connections etc.
        super.onDestroy();
    }

    // Called when the Fragment has been detached from its parent Activity.
    @Override
    public void onDetach() {
        Log.d ("Cabinet Fragment", "onDetach");
        super.onDetach();
    }

    /*@Override
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
    }*/

}
