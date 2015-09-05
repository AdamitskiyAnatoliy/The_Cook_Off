package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Anatoliy on 9/3/15.
 */
public class PointSystem {

    Context mContext;

    public PointSystem(Context con) {
        mContext = con;
    }

    public void setPoints(int _points) {
        int points = 0;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        points += _points;

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Points", points);
        editor.commit();
    }

    public int getPoints() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getInt("Points", 0);
    }

}
