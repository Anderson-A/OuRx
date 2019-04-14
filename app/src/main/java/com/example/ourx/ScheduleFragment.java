package com.example.ourx;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    boolean onPast = false;
    ArrayList<MedicineCard> pastMeds = new ArrayList<>();
    ArrayList<MedicineCard> upcomingMeds = new ArrayList<>();

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Schedule Fragment", "onStart");
        // Apply any required UI change now that the Fragment is visible.

        pastMeds.clear();
        upcomingMeds.clear();

        TextView upcomingText = getView().findViewById(R.id.upcoming);
        upcomingText.setPaintFlags(upcomingText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        /* TODO - Populate Past and Upcoming using database
         * Just creating random samples right now */
        MedicineCard tylenol = new MedicineCard("Tylenol", "12:00");
        MedicineCard advil = new MedicineCard("Advil", "13:00");
        MedicineCard Vyvanse = new MedicineCard("Vyvanse", "14:00");

        pastMeds.add(tylenol);
        upcomingMeds.add(Vyvanse);
        upcomingMeds.add(advil);

        if (onPast) {
            this.displayPastCards();
        } else {
            this.displayUpcomingCards();
        }

        /* display past medications array */
        final Button pastButton = getView().findViewById(R.id.past);
        pastButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPast = true;
                TextView pastText = getView().findViewById(R.id.past);
                TextView upcomingText = getView().findViewById(R.id.upcoming);
                pastText.setPaintFlags(pastText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                upcomingText.setPaintFlags(0);
                displayPastCards();
            }
        });

        /* display upcoming medications array */
        Button upcomingButton = getView().findViewById(R.id.upcoming);
        upcomingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPast = false;
                TextView upcomingText = getView().findViewById(R.id.upcoming);
                TextView pastText = getView().findViewById(R.id.past);
                pastText.setPaintFlags(0);
                upcomingText.setPaintFlags(upcomingText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                displayUpcomingCards();
            }
        });
    }

    /* Custom-built adapters to display list views of past/upcoming medicine cards */
    private void displayPastCards() {
        final MedCardAdapter adapter = new MedCardAdapter(getActivity(), pastMeds, true);
        ListView listView = (ListView) getView().findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private  void displayUpcomingCards() {
        final MedCardAdapter adapter = new MedCardAdapter(getActivity(), upcomingMeds, false);
        ListView listView = (ListView) getView().findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    // Called at the start of the active lifetime.
    @Override
    public void onResume(){
        super.onResume();
        Log.d ("Schedule Fragment", "onResume");
        // Resume any paused UI updates, threads, or processes required
        // by the Fragment but suspended when it became inactive.

        ((MainActivity) getActivity()).setActionBarTitle("Schedule");
    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
        Log.d ("Schedule Fragment", "onPause");
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
        Log.d ("Schedule Fragment", "onSaveInstanceState");
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate, onCreateView, and
        // onCreateView if the parent Activity is killed and restarted.
        super.onSaveInstanceState(savedInstanceState);
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
        Log.d ("Schedule Fragment", "onStop");
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Fragment isn't visible.
        super.onStop();
    }

    // Called when the Fragment's View has been detached.
    @Override
    public void onDestroyView() {
        Log.d ("Schedule Fragment", "onDestroyView");
        // Clean up resources related to the View.
        super.onDestroyView();
    }

    // Called at the end of the full lifetime.
    @Override
    public void onDestroy(){
        Log.d ("Schedule Fragment", "onDestroy");
        // Clean up any resources including ending threads,
        // closing database connections etc.
        super.onDestroy();
    }

    // Called when the Fragment has been detached from its parent Activity.
    @Override
    public void onDetach() {
        Log.d ("Schedule Fragment", "onDetach");
        super.onDetach();
    }

}
