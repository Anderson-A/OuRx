package com.example.ourx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static final int REQUEST_CODE = 1;
    boolean onPast = false;
    ArrayList<MedicineCard> pastMeds = new ArrayList<>();
    ArrayList<MedicineCard> upcomingMeds = new ArrayList<>();
    Date currentTime;
    private FragmentTransaction transaction;
    private Fragment cabinetFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        cabinetFrag = new CabinetFragment();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Default when opening app will be Upcoming medicine */
        TextView upcomingText = findViewById(R.id.upcoming);
        upcomingText.setPaintFlags(upcomingText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        currentTime = Calendar.getInstance().getTime();


        /* TODO - Populate Past and Upcoming using database
         * Just creating random samples right now */
        MedicineCard tylenol = new MedicineCard("Tylenol", "12:00");
        MedicineCard advil = new MedicineCard("Advil", "13:00");
        MedicineCard Vyvanse = new MedicineCard("Vyvanse", "14:00");

        pastMeds.add(tylenol);
        upcomingMeds.add(Vyvanse);
        upcomingMeds.add(advil);

        /* ------------------------------------------------------------------------ */

        if (onPast) {
            this.displayPastCards();
        } else {
            this.displayUpcomingCards();
        }

        /* display past medications array */
        final Button pastButton = findViewById(R.id.past);
        pastButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPast = true;
                TextView pastText = findViewById(R.id.past);
                TextView upcomingText = findViewById(R.id.upcoming);
                pastText.setPaintFlags(pastText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                upcomingText.setPaintFlags(0);
                displayPastCards();
            }
        });

        /* display upcoming medications array */
        Button upcomingButton = findViewById(R.id.upcoming);
        upcomingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPast = false;
                TextView upcomingText = findViewById(R.id.upcoming);
                TextView pastText = findViewById(R.id.past);
                pastText.setPaintFlags(0);
                upcomingText.setPaintFlags(upcomingText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                displayUpcomingCards();
            }
        });

    }

    /* Custom-built adapters to display list views of past/upcoming medicine cards */
    private void displayPastCards() {
        final MedCardAdapter adapter = new MedCardAdapter(this, pastMeds, true);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private  void displayUpcomingCards() {
        final MedCardAdapter adapter = new MedCardAdapter(this, upcomingMeds, false);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, AddMedication.class);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }

        /*
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        currentTime = Calendar.getInstance().getTime();
        Calendar test;

        // This will find the time closest to the current time and put it in the Upcoming Medication array
        if (resultCode == Activity.RESULT_OK) {
            String medName = data.getStringExtra("medication_name");
            String soonestTime = "";
            for (String time : data.getStringArrayExtra("all_times")) {
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

                if (currentTime.before(test.getTime())){
                    soonestTime = time;
                    break;
                }
            }
            if (soonestTime.length() > 0) {
                MedicineCard newlyAddedMedication = new MedicineCard(medName, soonestTime);
                upcomingMeds.add(newlyAddedMedication);

            }

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cabinet) {
            transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, cabinetFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_find) {

        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_map) {

        } else  if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
