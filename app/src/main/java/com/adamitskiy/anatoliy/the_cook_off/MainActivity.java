package com.adamitskiy.anatoliy.the_cook_off;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button feedButton, leaderboardsButton, achievementsButton, mainFeedNavButton,
    leaderboardsNavButton, achievementsNavButton, myProfileNavButton, settingsNavButton,
    logOutNavButton;
    private View feedIndicatorView, leaderboardsIndicatorView, achievementsIndicatorView;
    TextView navPoints, navUsername;
    FragmentManager fragmentManager;
    Network network = new Network(this);
    LinearLayout darkNavBackground;
    RelativeLayout navigationMenu;
    Boolean navOpen = false;
    ImageView userAvatarNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (network.checkNetwork()) {
//            Parse.initialize(this, "NmlHibFZqo8D6anM56zLid80ZnHOG4R9LDUEVoNZ",
//                    "Z83VxBJolBG1rvWdZpUbNytqGZNAG3kADGrUlTHm");
        } else {
            simpleSnackBar("Please Reconnect Network");
        }


        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.custom_actionbar_main,
                null);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);
        actionBar.setElevation(0);

        userAvatarNav = (ImageView) findViewById(R.id.user_avatar_nav);
        navUsername = (TextView) findViewById(R.id.username_nav);
        navPoints = (TextView) findViewById(R.id.user_score_nav);
        mainFeedNavButton = (Button) findViewById(R.id.main_feed_button_nav);
        leaderboardsNavButton = (Button) findViewById(R.id.leaderboards_button_nav);
        achievementsNavButton = (Button) findViewById(R.id.achievements_button_nav);
        myProfileNavButton = (Button) findViewById(R.id.profile_button_nav);
        settingsNavButton = (Button) findViewById(R.id.settings_button_nav);
        logOutNavButton = (Button) findViewById(R.id.logOutNavButton);
        navigationMenu = (RelativeLayout) findViewById(R.id.navigationMenu);
        darkNavBackground = (LinearLayout) findViewById(R.id.dark_background_nav);
        feedButton = (Button) findViewById(R.id.feed_button);
        leaderboardsButton = (Button) findViewById(R.id.leaderboards_button);
        achievementsButton = (Button) findViewById(R.id.achievements_button);
        feedIndicatorView = findViewById(R.id.feed_button_line);
        leaderboardsIndicatorView = findViewById(R.id.leaderboards_button_line);
        achievementsIndicatorView = findViewById(R.id.achievements_button_line);
        feedButton.setOnClickListener(this);
        leaderboardsButton.setOnClickListener(this);
        achievementsButton.setOnClickListener(this);
        logOutNavButton.setOnClickListener(this);
        settingsNavButton.setOnClickListener(this);
        mainFeedNavButton.setOnClickListener(this);
        leaderboardsNavButton.setOnClickListener(this);
        achievementsNavButton.setOnClickListener(this);
        myProfileNavButton.setOnClickListener(this);

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFeedFragment mainFrag = new MainFeedFragment();
        fragmentTransaction.add(R.id.fragment_layout, mainFrag, "HELLO");
        fragmentTransaction.commit();

        actionBarLayout.findViewById(R.id.side_nav_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!navOpen) {
                    navigationMenu.setVisibility(View.VISIBLE);
                    darkNavBackground.setVisibility(View.VISIBLE);
                    navOpen = true;
                } else {
                    navigationMenu.setVisibility(View.INVISIBLE);
                    darkNavBackground.setVisibility(View.INVISIBLE);
                    navOpen = false;
                }
            }
        });


        // Notification upon first log
        //
        //
        //
//        Intent intent1 = new Intent(this, MainActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
//
//        Notification n  = new Notification.Builder(this)
//                .setContentTitle("Health Champion")
//                .setContentText("You are making this look easy!!!")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pIntent)
//                .setAutoCancel(true).build();
//
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, n);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feed_button:
                feedIndicatorView.setBackgroundColor(Color.rgb(30,149,140));
                leaderboardsIndicatorView.setBackgroundColor(Color.rgb(241,194,49));
                achievementsIndicatorView.setBackgroundColor(Color.rgb(241,194,49));

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MainFeedFragment mainFrag = new MainFeedFragment();
                fragmentTransaction.replace(R.id.fragment_layout, mainFrag, "HELLO");
                fragmentTransaction.commit();

                break;
            case R.id.leaderboards_button:
                feedIndicatorView.setBackgroundColor(Color.rgb(241,194,49));
                leaderboardsIndicatorView.setBackgroundColor(Color.rgb(30,149,140));
                achievementsIndicatorView.setBackgroundColor(Color.rgb(241,194,49));

                FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                LeaderboardsFragment leaderFrag = new LeaderboardsFragment();
                fragmentTransaction1.replace(R.id.fragment_layout, leaderFrag, "HELLO1");
                fragmentTransaction1.commit();

                break;
            case R.id.achievements_button:
                feedIndicatorView.setBackgroundColor(Color.rgb(241,194,49));
                leaderboardsIndicatorView.setBackgroundColor(Color.rgb(241,194,49));
                achievementsIndicatorView.setBackgroundColor(Color.rgb(30,149,140));

                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                AchievementsFragment achieveFrag = new AchievementsFragment();
                fragmentTransaction2.replace(R.id.fragment_layout, achieveFrag, "HELLO2");
                fragmentTransaction2.commit();

                break;
            case R.id.logOutNavButton:
                if (network.checkNetwork()) {
                    //stopRepeating();

                    ParseUser.logOut();
                    ParseUser currentUser = ParseUser.getCurrentUser();

                    navigationMenu.setVisibility(View.INVISIBLE);
                    darkNavBackground.setVisibility(View.INVISIBLE);

                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                } else {
                    // Manually log user out

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("loggedIn", false);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.settings_button_nav:
                navigationMenu.setVisibility(View.INVISIBLE);
                darkNavBackground.setVisibility(View.INVISIBLE);
                navOpen = false;

                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);

                break;

            case R.id.main_feed_button_nav:
                navigationMenu.setVisibility(View.INVISIBLE);
                darkNavBackground.setVisibility(View.INVISIBLE);
                navOpen = false;

                feedIndicatorView.setBackgroundColor(Color.rgb(30,149,140));
                leaderboardsIndicatorView.setBackgroundColor(Color.rgb(241,194,49));
                achievementsIndicatorView.setBackgroundColor(Color.rgb(241,194,49));

                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                MainFeedFragment mainFrag1 = new MainFeedFragment();
                fragmentTransaction3.replace(R.id.fragment_layout, mainFrag1, "HELLO");
                fragmentTransaction3.commit();

                break;
            case R.id.leaderboards_button_nav:
                navigationMenu.setVisibility(View.INVISIBLE);
                darkNavBackground.setVisibility(View.INVISIBLE);
                navOpen = false;

                feedIndicatorView.setBackgroundColor(Color.rgb(241,194,49));
                leaderboardsIndicatorView.setBackgroundColor(Color.rgb(30,149,140));
                achievementsIndicatorView.setBackgroundColor(Color.rgb(241,194,49));

                FragmentTransaction fragmentTransaction4 = fragmentManager.beginTransaction();
                LeaderboardsFragment leaderFrag1 = new LeaderboardsFragment();
                fragmentTransaction4.replace(R.id.fragment_layout, leaderFrag1, "HELLO1");
                fragmentTransaction4.commit();

                break;
            case R.id.achievements_button_nav:
                navigationMenu.setVisibility(View.INVISIBLE);
                darkNavBackground.setVisibility(View.INVISIBLE);
                navOpen = false;

                feedIndicatorView.setBackgroundColor(Color.rgb(241,194,49));
                leaderboardsIndicatorView.setBackgroundColor(Color.rgb(241,194,49));
                achievementsIndicatorView.setBackgroundColor(Color.rgb(30,149,140));

                FragmentTransaction fragmentTransaction5 = fragmentManager.beginTransaction();
                AchievementsFragment achieveFrag1 = new AchievementsFragment();
                fragmentTransaction5.replace(R.id.fragment_layout, achieveFrag1, "HELLO2");
                fragmentTransaction5.commit();

                break;
            case R.id.profile_button_nav:
                navigationMenu.setVisibility(View.INVISIBLE);
                darkNavBackground.setVisibility(View.INVISIBLE);
                navOpen = false;

                Intent intent3 = new Intent(this, MyProfileActivity.class);
                intent3.putExtra("requestType", "nav");
                startActivity(intent3);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        PointSystem pointSystem = new PointSystem(this);

        if (network.checkNetwork()) {

            if (ParseUser.getCurrentUser() != null) {
                if (ParseUser.getCurrentUser().getString("Type").equals("Normal")) {
                    ParseFile image = ParseUser.getCurrentUser().getParseFile("image");
                    image.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                userAvatarNav.setImageBitmap(BitmapFactory.decodeByteArray(data,0,data.length) );
                            } else {
                                Log.d("Error", "Error Retrieving Avatar Navigation Image");
                            }
                        }
                    });
                } else if (ParseUser.getCurrentUser().getString("Type").equals("Twitter")) {

                    navUsername.setText(ParseTwitterUtils.getTwitter().getScreenName());
                    new NavAvatarDownloader(this).execute(ParseTwitterUtils.getTwitter().getScreenName());

                } else if (ParseUser.getCurrentUser().getString("Type").equals("Facebook")) {



                }
            }

            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                currentUser.fetchInBackground();

                String userPoints = currentUser.getString("Points");
                if (userPoints != null) {
                    pointSystem.setPoints(Integer.parseInt(userPoints));
                }

                navUsername.setText(currentUser.getUsername());
                navPoints.setText("Score: " + Integer.toString(pointSystem.getPoints()));
            }

            if (currentUser != null) {
//                stopRepeating();
//                startRepeating();
            } else {
                Intent intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
            }
        } else {
            // No Network, Pull from Local

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (prefs.getBoolean("loggedIn", false) == false) {
                Intent intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            navigationMenu.setVisibility(View.INVISIBLE);
            darkNavBackground.setVisibility(View.INVISIBLE);
            navOpen = false;

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void simpleSnackBar(String text) {
        SnackbarManager.show(
                Snackbar.with(this)
                        .text(text)
                        .textColor(Color.rgb(255, 153, 51))
                        .color(Color.DKGRAY)
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT));
    }

    class NavAvatarDownloader extends AsyncTask<String, Void, Bitmap> {

        Context mContext;

        public NavAvatarDownloader(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String url = null;

            try {

                HttpClient client = new DefaultHttpClient();
                HttpGet verifyGet = new HttpGet(
                        "https://api.twitter.com/1.1/users/show.json?screen_name=" + params[0]);
                ParseTwitterUtils.getTwitter().signRequest(verifyGet);
                HttpResponse response = client.execute(verifyGet);
                InputStream is = response.getEntity().getContent();
                JSONObject responseJson = new JSONObject(IOUtils.toString(is));
                url = responseJson.getString("profile_image_url");

                ParseUser user = ParseUser.getCurrentUser();
                user.put("socialAvatarUrl", url);
                user.saveInBackground();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                URL url1 = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);

            userAvatarNav.setImageBitmap(s);

        }
    }

}
