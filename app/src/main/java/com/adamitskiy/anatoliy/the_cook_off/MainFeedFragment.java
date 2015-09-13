package com.adamitskiy.anatoliy.the_cook_off;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatoliy on 8/23/15.
 */
public class MainFeedFragment extends Fragment {

    Network network;
    public static final String TAG = "MAINFEEDFRAGMENT.TAG";
    public FloatingActionsMenu addNewMenu;
    ListView mainListView;

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
        return inflater.inflate(R.layout.main_feed_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        network = new Network(getActivity());
        final View darkBack = getActivity().findViewById(R.id.blackBackground);
        addNewMenu = (FloatingActionsMenu)
                getActivity().findViewById(R.id.addNewMenu);

        addNewMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                darkBack.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                darkBack.setVisibility(View.INVISIBLE);
            }
        });

        darkBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMenu.collapse();
                darkBack.setVisibility(View.INVISIBLE);
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton newChallengeButton =
                (com.getbase.floatingactionbutton.FloatingActionButton) getActivity().findViewById(R.id.newChallengeButton);
        newChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (network.checkNetwork()) {
                    Intent intent = new Intent(getActivity(), ChallengeActivity.class);
                    startActivity(intent);
                    addNewMenu.collapse();
                } else {
                    addNewMenu.collapse();
                    simpleSnackBar("Please Reconnect Network");
                }
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton newFoodButton =
                (com.getbase.floatingactionbutton.FloatingActionButton) getActivity().findViewById(R.id.newFoodButton);
        newFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (network.checkNetwork()) {
                    Intent intent = new Intent(getActivity(), AddNewActivity.class);
                    startActivity(intent);
                    addNewMenu.collapse();
                } else {
                    addNewMenu.collapse();
                    simpleSnackBar("Please Reconnect Network");
                }
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton newDrinkButton =
                (com.getbase.floatingactionbutton.FloatingActionButton) getActivity().findViewById(R.id.newDrinkButton);
        newDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (network.checkNetwork()) {
                    Intent intent = new Intent(getActivity(), AddNewActivity.class);
                    startActivity(intent);
                    addNewMenu.collapse();
                } else {
                    addNewMenu.collapse();
                    simpleSnackBar("Please Reconnect Network");
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

        if (Integer.parseInt(ParseUser.getCurrentUser().getString("Points")) >= 10000) {

            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            boolean defaultValue = sharedPref.getBoolean("Achievement 1" + ParseUser.getCurrentUser().getUsername(), false);

            if (defaultValue == false) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("Achievement 1", true);
                editor.commit();

                ParseObject achievement = new ParseObject("Achievement");
                achievement.put("achieved", true);
                achievement.put("achievement", "Achieved 10,000 points.");
                achievement.put("username", ParseUser.getCurrentUser().getUsername());
                achievement.put("userId", ParseUser.getCurrentUser().getObjectId());
                achievement.saveInBackground();


                Intent intent1 = new Intent(getActivity(), MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(getActivity(), (int) System.currentTimeMillis(), intent1, 0);

                android.app.Notification n = new android.app.Notification.Builder(getActivity())
                        .setContentTitle("Achievement Unlocked")
                        .setContentText("Reached 10,000 points.")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true).build();


                NotificationManager notificationManager =
                        (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);

                notificationManager.notify(100, n);

            }
        }

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Main_Feed_Entry");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
                if (e == null) {
                    // your logic here

                    ArrayList<MainFeedObject> feedObjects = new ArrayList<MainFeedObject>();

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

                    mainListView = (ListView) getActivity().findViewById(R.id.main_feed_list);
                    MainListAdapter mainListAdapter = new MainListAdapter(getActivity(), feedObjects);
                    mainListView.setAdapter(mainListAdapter);

                } else {
                    // handle Parse Exception here
                }
            }
        });
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

    public void simpleSnackBar(String text) {
        SnackbarManager.show(
                Snackbar.with(getActivity())
                        .text(text)
                        .textColor(Color.rgb(255, 153, 51))
                        .color(Color.DKGRAY)
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT));
    }
}
