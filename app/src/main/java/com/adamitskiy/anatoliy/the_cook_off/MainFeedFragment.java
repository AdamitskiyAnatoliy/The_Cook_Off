package com.adamitskiy.anatoliy.the_cook_off;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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

        mainListView = (ListView) getActivity().findViewById(R.id.main_feed_list);
        MainListAdapter mainListAdapter = new MainListAdapter(getActivity());
        mainListView.setAdapter(mainListAdapter);

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
