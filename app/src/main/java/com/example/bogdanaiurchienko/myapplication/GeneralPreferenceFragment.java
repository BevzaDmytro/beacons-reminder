package com.example.bogdanaiurchienko.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.MenuItem;

/**
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GeneralPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        setHasOptionsMenu(true);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences
        // to their values. When their values change, their summaries are
        // updated to reflect the new value, per the Android Design
        // guidelines.
        SettingsActivity.bindPreferenceSummaryToValue(findPreference("user_name"));


        SwitchPreference switchPreference = (SwitchPreference) findPreference("beacon_scanning");
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
//                System.out.println(preference.getKey() + "   " + o.toString());
                String stringValue = o.toString();
                if(stringValue.equals("true")) {
                    System.out.println("TRUEEEEEEEE");
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            getActivity().startService(new Intent(getActivity(),BeaconScanner.class));
                        }
                    };
                    thread.start();
//                    getActivity().startService(new Intent(getActivity(),BeaconScanner.class));
                } else{
                    System.out.println("FALSEEEEEE");
                    getActivity().stopService(new Intent(getActivity(),BeaconScanner.class));
                }
                preference.setSummary(stringValue);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Preference findPreference(CharSequence key) {
        return super.findPreference(key);
    }
}

