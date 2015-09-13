package com.adamitskiy.anatoliy.the_cook_off;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProfileFeedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Network network;
    public static final String TAG = "PROFILEFEEDFRAGMENT.TAG";
    ListView profileListView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static ProfileFeedFragment newInstance(String param1, String param2) {
        ProfileFeedFragment fragment = new ProfileFeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_feed, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        network = new Network(getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Main_Feed_Entry");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
                if (e == null) {
                    // your logic here

                    Bundle bundle = getArguments();
                    String userId = bundle.getString("userId");

                    ArrayList<MainFeedObject> feedObjects = new ArrayList<MainFeedObject>();

                    for (ListIterator<ParseObject> iter = markers.listIterator(); iter.hasNext(); ) {
                        ParseObject a = iter.next();
                        if (!a.getString("userId").equals(userId)) {
                            iter.remove();
                        }
                    }

                    for (int i = 0; i < markers.size(); i++) {

                        String avatarUrl = null;
                        ParseFile avatarFile = null;

                        if (markers.get(i).getString("typeOfUser").equals("Normal")) {
                            avatarUrl = null;
                            avatarFile = markers.get(i).getParseFile("avatarFile");
                        } else if (markers.get(i).getString("typeOfUser").equals("Twitter")) {
                            avatarUrl = markers.get(i).getString("avatarUrl");
                            avatarFile = null;
                        } else if (markers.get(i).getString("typeOfUser").equals("Facebook")) {

                        }

                        feedObjects.add(i, new MainFeedObject(markers.get(i).getString("Message"), avatarUrl, markers.get(i).getString("typeOfUser"), avatarFile, markers.get(i).getString("userId")));

                    }

                    profileListView = (ListView) getActivity().findViewById(R.id.profile_feed_list);
                    MainListAdapter mainListAdapter = new MainListAdapter(getActivity(), feedObjects);
                    profileListView.setAdapter(mainListAdapter);

                } else {
                    // handle Parse Exception here
                }
            }
        });
    }

    public void simpleSnackBar(String text) {
        SnackbarManager.show(
                Snackbar.with(getActivity())
                        .text(text)
                        .textColor(Color.rgb(255, 153, 51))
                        .color(Color.DKGRAY)
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT));
    }

}
