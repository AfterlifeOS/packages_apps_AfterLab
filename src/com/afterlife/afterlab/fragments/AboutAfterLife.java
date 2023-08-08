package com.afterlife.afterlab.fragments;

import com.android.settings.R;
import android.os.Bundle;
import com.android.settings.SettingsPreferenceFragment;

public class AboutAfterLife extends SettingsPreferenceFragment {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.about_afterlife);
    }

    public int getMetricsCategory() {
        return 1150;
    }
}