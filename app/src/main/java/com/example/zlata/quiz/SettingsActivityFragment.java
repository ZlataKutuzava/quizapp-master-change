package com.example.zlata.quiz;

import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.os.Bundle;


public class SettingsActivityFragment extends PreferenceFragment {

    public SettingsActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
