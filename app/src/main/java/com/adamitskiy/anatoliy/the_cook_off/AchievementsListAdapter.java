package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatoliy on 8/23/15.
 */
public class AchievementsListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context mContext;
    ArrayList<ParseObject> objects;

    public AchievementsListAdapter (Context _mContext, ArrayList<ParseObject> objects) {
        mContext = _mContext;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(convertView == null) {

            //vi = inflater.inflate(R.layout.profile_list_item, null);
            vi = inflater.inflate(R.layout.achievements_list_item, null);

        }

        TextView achievement = (TextView) vi.findViewById(R.id.achievementName);
        achievement.setText(objects.get(position).getString("achievement"));

        return vi;
    }

}
