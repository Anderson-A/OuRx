package com.example.ourx;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load preferences from XML file
        addPreferencesFromResource(R.xml.preferences);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals("night_switch")) {
                    boolean nightModeOn = prefs.getBoolean("night_switch", false);
                    if (nightModeOn) {
                        //getActivity().setTheme(R.style.NightTheme);
                        //getActivity().recreate();
                    } else {
                        //getActivity().setTheme(R.style.AppTheme);
                        //getActivity().recreate();
                    }
                }
            }
        };
    }
}
