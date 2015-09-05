package com.adamitskiy.anatoliy.the_cook_off;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.ParseUser;

public class MyProfileActivity extends AppCompatActivity {

    String requestType = "";
    String stringUser;
    ParseUser user;
    ListView profileList;
    TextView userScoreProfile, profileUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Gson gson = new Gson();

        requestType = getIntent().getStringExtra("requestType");
        stringUser = getIntent().getStringExtra("User");
        user = gson.fromJson(stringUser, ParseUser.class);

        if (requestType.equals("nav")) {

            PointSystem pointSystem = new PointSystem(this);
            ParseUser parseUser = ParseUser.getCurrentUser();
            profileUsername = (TextView) findViewById(R.id.username_profile);
            profileUsername.setText(parseUser.getUsername());
            userScoreProfile = (TextView) findViewById(R.id.user_score_profile);
            userScoreProfile.setText("Score: " + Integer.toString(pointSystem.getPoints()));

        } else {

            profileUsername = (TextView) findViewById(R.id.username_profile);
            profileUsername.setText(user.getUsername());
            userScoreProfile = (TextView) findViewById(R.id.user_score_profile);
            userScoreProfile.setText("Score: " + user.getString("Points"));

        }

        profileList = (ListView) findViewById(R.id.listView_profile);
        ProfileListAdapter profileListAdapter = new ProfileListAdapter(this);
        profileList.setAdapter(profileListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
