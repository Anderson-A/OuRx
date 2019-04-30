package com.example.ourx;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass. TODO: make it back to Fragment instead of listFragment?
 */
public class ScheduleFragment extends Fragment {

    boolean onPast = false;
    ArrayList<MedicineCard> pastMeds = new ArrayList<>();
    ArrayList<MedicineCard> upcomingMeds = new ArrayList<>();
    ArrayList<MedicineCard> takenMeds = new ArrayList<>();

    MedicineEntity cardToMigrate;
    MedicineViewModel medicineViewModel;

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

        /* The viewModel to hold all data (separates data from activity instances */
        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        pastMeds.clear();
        upcomingMeds.clear();

        /* underlines the correct button and displays the correct card array */
        if (onPast) {
            TextView pastText = getView().findViewById(R.id.past);
            pastText.setPaintFlags(pastText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            this.displayPastCards();
        } else {
            TextView upcomingText = getView().findViewById(R.id.upcoming);
            upcomingText.setPaintFlags(upcomingText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            this.displayUpcomingCards();
        }

        /* display past medications array on click */
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

        /* display upcoming medications array on click */
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


        /* Listen for changes in the medications database and display them
        medicineViewModel.getAllMeds().observe(this, new Observer<List<MedicineEntity>>() {
            @Override
            public void onChanged(@Nullable final List<MedicineEntity> meds) {
                /* No longer used now that CabinetCardAdapter uses MedicineEntity directly TODO: delete line
                medications = entityToCabCard(meds);
                final CabinetCardAdapter adapter = new CabinetCardAdapter(getActivity(), meds);
                setListAdapter(adapter);
            }
        });
        */

        // Get the ListView the Cabinet_fragment is using and register it to have a context menu
        //if() {
        final ListView listView = (ListView) getView().findViewById(R.id.list_view);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent infoIntent = new Intent(getActivity(), MedicationInfo.class);
                TextView cabName = v.findViewById(R.id.med_name);
                infoIntent.putExtra("name", cabName.getText().toString());
                startActivity(infoIntent);
            }
        });


        /* Listen for changes in the medications database and display them */
        medicineViewModel.getAllMeds().observe(this, new Observer<List<MedicineEntity>>() {
            @Override
            public void onChanged(@Nullable final List<MedicineEntity> meds) {
                ArrayList<MedicineCard> medCards = entityToMedCard(meds);
                pastMeds = parsePast(medCards);
                upcomingMeds = parseUpcoming(medCards);
                if (onPast) {
                    displayPastCards();
                } else {
                    displayUpcomingCards();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.schedule_context_menu, menu);
        menu.setHeaderTitle("Select Action");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.migrate) {
            // Get the medication we want to delete
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
            //ListView listView = getView().findViewById(R.id.list_view)
            cardToMigrate = (MedicineEntity) medicineViewModel.getMedicineByName(upcomingMeds.get(index).getName());

            //update field
            cardToMigrate.setMED_TAKEN("true");


            //update the dao
            medicineViewModel.update(cardToMigrate );


            Snackbar migrateSnack = Snackbar.make(getActivity().findViewById(R.id.mainCoordinatorLayout), "" + cardToMigrate.MED_NAME + " moved " + cardToMigrate.MED_TAKEN, Snackbar.LENGTH_LONG);
            migrateSnack.show();

        } else if (item.getItemId() == R.id.skip) {

            // Get the medication we want to delete
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
            //ListView listView = getView().findViewById(R.id.list_view)
            cardToMigrate = (MedicineEntity) medicineViewModel.getMedicineByName(upcomingMeds.get(index).getName());
            Snackbar migrateSnack = Snackbar.make(getActivity().findViewById(R.id.mainCoordinatorLayout), "" + cardToMigrate.MED_NAME + " skipped " + cardToMigrate.MED_TAKEN, Snackbar.LENGTH_LONG);
            //deleteSnack.setAction("Undo", new CabinetFragment.undoListener());
            migrateSnack.show();

        } else {
            return false;
        }

        return true;
    }

    /* Parses medicine cards by determining if they are scheduled in the past */
    private ArrayList<MedicineCard> parsePast(ArrayList<MedicineCard> medicineCards) {
        ArrayList<MedicineCard> pastMedications = new ArrayList<>();
        Date rightNow = Calendar.getInstance().getTime();
        for (MedicineCard medicineCard : medicineCards) {
            Date medicationTime = parseTime(medicineCard.getTimeToTake());
            if (medicationTime.before(rightNow) && medicineCard.isTaken()) {
            //if(medicineCard.isTaken()) {    //TODO: switch back?
                pastMedications.add(medicineCard);
            }
        }

        return pastMedications;
    }

    /* Parses medicine cards by determining if they are scheduled in the future */
    private ArrayList<MedicineCard> parseUpcoming(ArrayList<MedicineCard> medicineCards) {
        ArrayList<MedicineCard> futureMedications = new ArrayList<>();
        for (MedicineCard medicineCard : medicineCards) {
            Date rightNow = Calendar.getInstance().getTime();
            Date medicationTime = parseTime(medicineCard.getTimeToTake());
            if (!medicationTime.before(rightNow) || !medicineCard.isTaken()) {
            //  if(!medicineCard.isTaken()) {
                futureMedications.add(medicineCard);
            }
        }
        return futureMedications;
    }

    private ArrayList<MedicineCard> parseTaken(ArrayList<MedicineCard> medicineCards) {
        ArrayList<MedicineCard> takenMedications = new ArrayList<>();
        for(MedicineCard medicineCard : medicineCards) {
            if(medicineCard.isTaken()) {
                takenMedications.add(medicineCard);
            }
        }

        return takenMedications;
    }


    /* thanks @jake */
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

    /* Turns medicine entities into med cards to display in schedule */
    private ArrayList<MedicineCard> entityToMedCard(List<MedicineEntity> meds) {
        ArrayList<MedicineCard> medCards = new ArrayList<>();
        Log.d("SIZE OF MEDS", Integer.toString(meds.size()));
        for (MedicineEntity med : meds) {
            Calendar time = Calendar.getInstance();
            if (time.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && med.MED_SUN != null
                    || time.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && med.MED_MON != null
                    || time.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && med.MED_TUES != null
                    || time.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && med.MED_WED != null
                    || time.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && med.MED_THURS != null
                    || time.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && med.MED_FRI != null
                    || time.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && med.MED_SAT != null) {
                boolean taken = false;
                if (med.MED_TAKEN.equals("true")) {
                    taken = true;
                }

                /* TODO: find solution to distinguish what time something has been taken
                 * Idea 1: have 5 columns (taken 1, taken 2, etc)
                 * Idea 2: original idea of a new entry for each time. Updating problem. */
                medCards.add(new MedicineCard(med.MED_NAME, med.MED_TIME_ONE, taken));
                /* Displays each new time instance as a new card */
                if (med.MED_TIME_TWO != null) {
                    medCards.add(new MedicineCard(med.MED_NAME, med.MED_TIME_TWO, taken));
                }
                if (med.MED_TIME_THREE != null) {
                    medCards.add(new MedicineCard(med.MED_NAME, med.MED_TIME_THREE, taken));
                }
                if (med.MED_TIME_FOUR != null) {
                    medCards.add(new MedicineCard(med.MED_NAME, med.MED_TIME_FOUR, taken));
                }
                if (med.MED_TIME_FIVE != null) {
                    medCards.add(new MedicineCard(med.MED_NAME, med.MED_TIME_FIVE, taken));
                }
            }
        }
        return medCards;
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
