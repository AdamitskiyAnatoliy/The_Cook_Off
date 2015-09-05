package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Anatoliy on 8/24/15.
 */

public class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        CheckBoxPreference notifications = (CheckBoxPreference)getPreferenceManager().findPreference("notification_preference");
        notifications.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.toString().equals("true")) {

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("notificationOn", false);
                    editor.commit();

                    Toast.makeText(getActivity(), "Notifications Off",
                            Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("notificationOn", true);
                    editor.commit();

                    Toast.makeText(getActivity(), "Notifications On",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        Preference emailButton = (Preference)getPreferenceManager().findPreference("email_preference");
        emailButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(getActivity(), ChangeCredentialsActivity.class);
                intent.putExtra("Type", "email");
                startActivity(intent);

                return true;
            }
        });

        Preference usernameButton = (Preference)getPreferenceManager().findPreference("username_preference");
        usernameButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(getActivity(), ChangeCredentialsActivity.class);
                intent.putExtra("Type", "username");
                startActivity(intent);

                return true;
            }
        });

        Preference passwordButton = (Preference)getPreferenceManager().findPreference("password_preference");
        passwordButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(getActivity(), ChangeCredentialsActivity.class);
                intent.putExtra("Type", "password");
                startActivity(intent);

                return true;
            }
        });
    }

}
