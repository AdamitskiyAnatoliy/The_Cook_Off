package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.EventListener;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class LogInFragment extends Fragment {

    private static final String TAG = LogInFragment.class.getSimpleName();
    EditText username, password;
    Network network;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_login, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        network = new Network(getActivity());
        username = (EditText) getView().findViewById(R.id.usernameTextField);
        password = (EditText) getView().findViewById(R.id.passwordTextField);

        getView().findViewById(R.id.logInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (network.checkNetwork()) {
                    if (username.getText().toString().equals("") && password.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Please Fill Out All Fields", Toast.LENGTH_LONG).show();
                    } else if (password.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_LONG).show();
                    } else if (username.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Please Enter Username", Toast.LENGTH_LONG).show();
                    } else {
                        ParseUser.logInInBackground(username.getText().toString().trim(),
                                password.getText().toString().trim(), new LogInCallback() {
                                    public void done(ParseUser user, ParseException e) {
                                        if (user != null) {

                                            TheCookOff.updateParseInstallation(user);

                                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                            SharedPreferences.Editor editor = prefs.edit();
                                            editor.putBoolean("loggedIn", true);
                                            editor.commit();
                                            getActivity().finish();
                                        } else {
                                            Toast.makeText(getActivity(), "Invalid Login, Please Try Again", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Reconnect Network", Toast.LENGTH_LONG).show();
                }
            }
        });

        getView().findViewById(R.id.twitterLoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseTwitterUtils.logIn(getActivity(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Twitter!");
                        } else {
                            Log.d("MyApp", "User logged in through Twitter!");
                            TheCookOff.updateParseInstallation(user);
                            getActivity().finish();
                        }
                    }
                });
            }
        });

        getView().findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (network.checkNetwork()) {
                    Intent intent = new Intent(getActivity(), SignUpActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(getActivity(), "Please Reconnect Network", Toast.LENGTH_LONG).show();
                }

            }
        });

        getView().findViewById(R.id.forgotPasswordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter Email Address");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Send Reset Email", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = input.getText().toString();

                        ParseUser.requestPasswordResetInBackground(text.trim(),
                                new RequestPasswordResetCallback() {
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            // An email was successfully sent with reset instructions.
                                            Toast.makeText(getActivity(), "Email Sent Successfully", Toast.LENGTH_LONG).show();
                                        } else {
                                            // Something went wrong. Look at the ParseException to see what's up.
                                            Toast.makeText(getActivity(), "Invalid Input. Please Try Again", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        ScrollView scrollView = (ScrollView) getView().findViewById(R.id.logInScroll);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

        if (resultCode == SignUpActivity.RESULT_OK) {
            String user = data.getStringExtra("username");
            String pass = data.getStringExtra("password");
            ParseUser.logInInBackground(user, pass, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {

                }
            });
            getActivity().finish();
        } else if (resultCode == 10) {
            getActivity().finish();
        } else if (resultCode == 100) {
            getActivity().finish();
        }
    }

    public void showSnackBar (String message, Snackbar.SnackbarDuration duration) {

        SnackbarManager.show(
                Snackbar.with(getActivity())
                        .text(message)
                        .color(Color.DKGRAY)
                        .textColor(Color.rgb(255, 153, 51))
                        .duration(duration)
                        .eventListener(new EventListener() {
                            @Override
                            public void onShow(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will show. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onShowByReplace(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will show by replace. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onShown(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar shown. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onDismiss(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will dismiss. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onDismissByReplace(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will dismiss by replace. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onDismissed(Snackbar snackbar) {

                            }
                        }));

    }
}
