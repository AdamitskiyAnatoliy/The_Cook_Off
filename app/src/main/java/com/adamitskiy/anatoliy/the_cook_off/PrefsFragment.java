package com.adamitskiy.anatoliy.the_cook_off;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Anatoliy on 8/24/15.
 */

public class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
