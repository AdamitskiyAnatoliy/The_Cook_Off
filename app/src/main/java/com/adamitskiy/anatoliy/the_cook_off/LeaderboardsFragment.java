package com.adamitskiy.anatoliy.the_cook_off;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Anatoliy on 8/23/15.
 */
public class LeaderboardsFragment extends Fragment {

    ListView leaderboardList;
    List<ParseUser> users;

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
        return inflater.inflate(R.layout.leaderboards_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        leaderboardList = (ListView) getActivity().findViewById(R.id.leaderboards_list);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    users = objects;

                    for(int i = users.size()-1; i >= 0; i--) {
                        for(int j = 0; j < i; j++) {
                            if(Integer.parseInt(users.get(j).getString("Points")) < Integer.parseInt(users.get(j+1).getString("Points"))) {
                                ParseUser temp = users.get(j);
                                users.set(j, users.get(j + 1));
                                users.set(j + 1, temp);
                            }
                        }
                    }

                    LeaderboardsListAdapter leaderboardsListAdapter = new LeaderboardsListAdapter(getActivity(), objects);
                    leaderboardList.setAdapter(leaderboardsListAdapter);

                } else {


                }
            }
        });

        leaderboardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final ParseUser user = users.get(position);

                final Intent intent = new Intent(getActivity(), MyProfileActivity.class);

                if (user.getString("Type").equals("Normal")) {
                    ParseFile userImage = user.getParseFile("image");
                    userImage.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {

                                //intent.putExtra("avatarByteArray", data);

                                try {
                                    InternalStorage.writeObject(getActivity(), "leaderboardImage", data);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }

                                intent.putExtra("type", "Normal");
                                intent.putExtra("requestType", "leaderboard");
                                intent.putExtra("name", user.getString("username"));
                                intent.putExtra("score", user.getString("Points"));
                                intent.putExtra("userId", user.getObjectId());
                                startActivity(intent);
                            }
                        }
                    });
                } else if (user.getString("Type").equals("Twitter")) {
                    intent.putExtra("type", "Twitter");
                    intent.putExtra("avatarUrl", user.getString("socialAvatarUrl"));
                    intent.putExtra("requestType", "leaderboard");
                    intent.putExtra("name", user.getString("username"));
                    intent.putExtra("score", user.getString("Points"));
                    intent.putExtra("userId", user.getObjectId());
                    startActivity(intent);
                } else if (user.getString("Type").equals("Facebook")) {

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
