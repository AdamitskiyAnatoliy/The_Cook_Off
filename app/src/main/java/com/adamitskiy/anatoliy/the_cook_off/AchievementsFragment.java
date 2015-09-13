package com.adamitskiy.anatoliy.the_cook_off;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
public class AchievementsFragment extends Fragment {

    ListView achievementsList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.achievements_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Achievement");
        query.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
                if (e == null) {
                    // your logic here

                    ArrayList<ParseObject> objects = new ArrayList<>();

                    for (int i = 0; i < markers.size(); i++) {

                        objects.add(markers.get(i));

                    }

                    achievementsList = (ListView) getActivity().findViewById(R.id.achievements_list);
                    AchievementsListAdapter achievementsListAdapter = new AchievementsListAdapter(getActivity(), objects);
                    achievementsList.setAdapter(achievementsListAdapter);

                } else {
                    // handle Parse Exception here
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
