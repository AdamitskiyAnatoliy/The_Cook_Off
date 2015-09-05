package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ChangeCredentialsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_credentials);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if (intent.getStringExtra("Type").equals("email")) {
            setTitle("Change Email Address");

        } else if (intent.getStringExtra("Type").equals("username")) {
            setTitle("Change Username");

        } else if (intent.getStringExtra("Type").equals("password")) {
            setTitle("Change Password");

        }

        final EditText newField = (EditText) findViewById(R.id.newChangeTextField);
        final EditText confirmField = (EditText) findViewById(R.id.confirmChangeTextField);

        Button updateButton = (Button) findViewById(R.id.updateInfoButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();

                if (intent.getStringExtra("Type").equals("email")) {

                    if (!newField.getText().toString().equals(confirmField.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Fields do not match.", Toast.LENGTH_SHORT).show();
                    } else {
                        ParseUser parseUser = ParseUser.getCurrentUser();
                        parseUser.setEmail(confirmField.getText().toString());
                        parseUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (null == e) {
                                    // report about success
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Email Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    // report about error
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                    Log.i("ERROR", e.toString());
                                }
                            }
                        });

                    }
                } else if (intent.getStringExtra("Type").equals("username")) {

                    if (!newField.getText().toString().equals(confirmField.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Fields do not match.", Toast.LENGTH_SHORT).show();
                    } else {
                        ParseUser parseUser = ParseUser.getCurrentUser();
                        parseUser.setUsername(confirmField.getText().toString());
                        parseUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (null == e) {
                                    // report about success
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Username Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    // report about error
                                }
                            }
                        });

                    }
                } else if (intent.getStringExtra("Type").equals("password")) {

                    if (!newField.getText().toString().equals(confirmField.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Fields do not match.", Toast.LENGTH_SHORT).show();
                    } else {
                        ParseUser parseUser = ParseUser.getCurrentUser();
                        parseUser.setPassword(confirmField.getText().toString());
                        parseUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (null == e) {
                                    // report about success
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    // report about error
                                }
                            }
                        });

                    }
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_change_credentials, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
